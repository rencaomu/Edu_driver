package com.ren.service.controller;

import com.ren.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户管理")
@RestController
@RequestMapping("/user")
//@CrossOrigin
public class EduLoginController {
    /**
     * 登录验证
     * @return
     */
    @ApiOperation(value = "登录验证")
    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    /**
     * 用户信息
     * @param token
     * @return
     */
    @ApiOperation(value = "用户信息")
    @GetMapping("info")
    public Result info(String token){
        return Result.ok()
                .data("roles","[\"admin\"]")
                .data("name","admin")
                .data("avatar","https://grain-online-education.oss-cn-shenzhen.aliyuncs.com/avatar/avatar.jpg");
    }

}
