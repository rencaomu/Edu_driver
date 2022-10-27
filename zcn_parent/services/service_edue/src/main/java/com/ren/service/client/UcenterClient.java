package com.ren.service.client;

import com.ren.commonutils.Result;
import com.ren.service.entity.EduComment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    // 根据用户id获取用户信息
    @GetMapping("/educenter/member/getInfoUc/{id}")
    public EduComment getUcenterPay(@PathVariable("id") String id);
}
