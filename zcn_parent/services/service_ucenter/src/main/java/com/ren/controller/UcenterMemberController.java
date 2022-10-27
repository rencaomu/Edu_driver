package com.ren.controller;


import com.ren.commonutils.JwtUtils;
import com.ren.commonutils.Result;
import com.ren.commonutils.ordervo.UcenterMemberOrder;
import com.ren.entity.UcenterMember;
import com.ren.entity.vo.RegisterVo;
import com.ren.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-10-06
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember member) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return Result.ok().data("token",token);
    }

    // 注册接口
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo)  {
        memberService.register(registerVo);
        return Result.ok();
    }

    // 根据token获取用户信息
    @GetMapping("getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
         String userId = JwtUtils.getMemberIdByJwtToken(request);
         // 根据用户id获取用户信息
         UcenterMember byId = memberService.getById(userId);
         return Result.ok().data("userInfo",byId);
    }

    // 根据token字符串获取用户信息
    @PostMapping("getInfoUc/{id}")
    public UcenterMember getInfoUc(@PathVariable String id) {
        UcenterMember ucenterMember = memberService.getById(id);

        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(ucenterMember,member);
        return  member;
    }


    // 根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    // 查询某天注册人数
    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable String day) {

       Integer count  =  memberService.countRegisterDay(day);

        return Result.ok().data("countRegister",count);
    }
}

