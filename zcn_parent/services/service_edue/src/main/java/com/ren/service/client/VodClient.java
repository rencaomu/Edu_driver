package com.ren.service.client;

import com.ren.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodClientImplFeignClient.class)
@Component
public interface VodClient {
    // 定义调用方法的路径
    @DeleteMapping(value = "/vod/video/removeAlyVideo/{id}")
    public Result removeVideo(@PathVariable("id") String id);

    // 定义调用删除多个视频的方法
    // 删除多个视频
    @DeleteMapping("/vod/video/delete-batch")
    public Result deleteBach(@RequestParam("videoIdList") List<String> videoIdList);
}
