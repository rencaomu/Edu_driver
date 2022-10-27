package com.ren.controller;


import com.ren.client.UcenterClient;
import com.ren.commonutils.Result;
import com.ren.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private StatisticsDailyService staService;

    // 统计某天注册人数,生成统计数据
    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable String day) {

        staService.registerCount(day);
        return Result.ok();
    }

    // 图表显示，返回部分数据，日期json数组，数量json数据
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type,
                           @PathVariable String begin,
                           @PathVariable String end) {
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return Result.ok().data(map);
    }
}

