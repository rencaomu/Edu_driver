package com.ren.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.service.client.VodClient;
import com.ren.service.entity.EduVideo;
import com.ren.service.mapper.EduVideoMapper;
import com.ren.service.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.Utilities;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    // 根据课程id删除小节
    // TODO 删除小节的时候需要删除对应的视频
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 根据课程id查询视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        List<String> videoId = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
             EduVideo eduVideo = eduVideoList.get(i);
             String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                videoId.add(videoSourceId);
            }
        }
        if (videoId.size() > 0 ){
            vodClient.deleteBach(videoId);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
