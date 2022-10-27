package com.ren.service.client;

import com.ren.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImplFeignClient implements VodClient{
    @Override
    public Result removeVideo(String id) {

        return Result.error().message("删除出错");
    }

    @Override
    public Result deleteBach(List<String> videoIdList) {

        return Result.error().message("批量删除出错");
    }
}
