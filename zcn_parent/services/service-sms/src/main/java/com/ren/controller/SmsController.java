package com.ren.controller;

import com.ren.commonutils.Result;
import com.ren.service.MsmService;
import com.ren.client.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class SmsController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // 发送手机号
    @GetMapping("send/{phone}")
    public Result senSms(@PathVariable String phone) {

        // 从redis中获取验证码
         String code = redisTemplate.opsForValue().get(phone);
         if (!StringUtils.isEmpty(code)){
             return Result.ok();
         }

         code = RandomUtil.getFourBitRandom();

        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用Service发送短信
         boolean isSend =   msmService.send(param,phone);
         if (isSend){
             // 发送成功将验证码放进redis，设置有效时间
             redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

             return Result.ok();
         }else {
             return Result.error().message("短信发送失败");
         }

    }
}
