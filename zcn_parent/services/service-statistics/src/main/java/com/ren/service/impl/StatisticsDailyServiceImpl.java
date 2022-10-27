package com.ren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.client.UcenterClient;
import com.ren.commonutils.Result;
import com.ren.entity.StatisticsDaily;
import com.ren.mapper.StatisticsDailyMapper;
import com.ren.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-10-21
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public void registerCount(String day) {

        // 添加日期之前先删除相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        // 得到注册人数
       Result registerResult = ucenterClient.countRegister(day);
       Integer countRegister = (Integer) registerResult.getData().get("countRegister");

       // 把数据添加到数据库
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister); // 注册人数
        sta.setDateCalculated(day); //统计日期

        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(sta);
    }

     // 图表显示，返回部分数据，日期json数组，数量json数据
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应的数据
       QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated",type);
       wrapper.between("date_calculated",begin,end);

       List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        // 建立两个list集合存储数据
        List<String> dateCaIculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        // 遍历查询所有数据list进行封装
        for (int i = 0; i < staList.size(); i++) {
            // 得到每一个数据
            StatisticsDaily daily = staList.get(i);
            // 封装日期集合
            dateCaIculatedList.add(daily.getDateCalculated());

            // 封装对应的数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
            }
        }

        // 把封装后的list集合放入map中进行返回
        Map<String,Object> map = new HashMap<>();
        map.put("numDataList",numDataList);
        map.put("dateCaIculatedList",dateCaIculatedList);

        return map;
    }
}
