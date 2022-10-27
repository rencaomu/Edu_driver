package com.ren.schedu;

import com.ren.service.StatisticsDailyService;
import com.ren.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduTask {

    @Autowired
    private StatisticsDailyService staService;

    //在每天凌晨1点执行方法,查询注册人数

    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        // 调用自定义的 DateUtil 工具类方法将当前日期 -1
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
