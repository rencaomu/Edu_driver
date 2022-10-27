package com.ren.controller;

import com.google.gson.Gson;
import com.ren.commonutils.JwtUtils;
import com.ren.entity.UcenterMember;
import com.ren.exception.RenException;
import com.ren.service.UcenterMemberService;
import com.ren.client.ConstantWxUtils;
import com.ren.client.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

//@CrossOrigin
@Controller
@RequestMapping("/api/center/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;
    // 获取扫描人的信息
    @GetMapping("callback")
    public String callback(String code,String state){

        try {
            //获取临时票据

            // 请求微信地址
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            // 拼接参数
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            // 得到返回值
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

            Gson gson  = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String oppenid = (String)mapAccessToken.get("openid");



            // 将扫码的用户添加到数据库
         UcenterMember member = memberService.getOpenIdMember(oppenid);
         if (member == null){
             // 将得到的值请求地址
             String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                     "?access_token=%s" +
                     "&openid=%s";

             // 拼接参数
             String userInfoUrl = String.format(
                     baseUserInfoUrl,
                     access_token,
                     oppenid
             );
             // 发送请求
             String userInfo = HttpClientUtils.get(userInfoUrl);

             // 获取扫码人的信息
             HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
             String nickname = (String)userInfoMap.get("nickname"); // 昵称
             String headimgurl = (String)userInfoMap.get("headimgurl"); // 头像
             member = new UcenterMember();
             member.setOpenid(oppenid);
             member.setNickname(nickname);
             member.setAvatar(headimgurl);
             memberService.save(member);
         }

         // 使用Jwt生成token 解决跨域问题
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            // 返回到首页面
            return "redirect:http://localhost:3000?token="+jwtToken;
        }catch (Exception e){
            throw new RenException(20001,"登录失败");
        }

    }


    // 生成微信登录二维码
    @GetMapping("login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
        "?appid=%s" +
        "&redirect_uri=%s" +
        "&response_type=code" +
        "&scope=snsapi_login" +
        "&state=%s" +
        "#wechat_redirect";

        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new RenException(20001, e.getMessage());
        }

        // 设置%s中
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "ren"
        );

        // 重定向请求微信地址
        return "redirect:"+url;

    }
}
