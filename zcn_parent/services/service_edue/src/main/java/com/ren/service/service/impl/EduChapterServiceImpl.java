package com.ren.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.exception.RenException;
import com.ren.service.entity.EduChapter;
import com.ren.service.entity.EduVideo;
import com.ren.service.entity.chapter.Chapter;
import com.ren.service.entity.chapter.VideoVo;
import com.ren.service.mapper.EduChapterMapper;
import com.ren.service.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.service.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    // 根据课程id查询章节小节
    @Override
    public List<Chapter> getChapterVideoByCourseId(String courseId) {

        // 根据课程id查询所有的章节
        QueryWrapper<EduChapter> wrapperChapter  = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
       List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 根据课程id查询小节信息
        QueryWrapper<EduVideo> wrapperVideo  = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
       List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

       // 创建集合用于封装最终数据
        List<Chapter> finalList = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            // 每个章节
            EduChapter eduChapter = eduChapterList.get(i);

            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(eduChapter,chapter);

            // 把chapter放到list集合中
            finalList.add(chapter);

            List<VideoVo> videoList = new ArrayList<>();

            // 遍历查询的list集合进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                // 判断 小节里chapterid和章节的id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }

            chapter.setChildren(videoList);
        }
        return finalList;
    }
    ////删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id 查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count >0) {//查询出小节，不进行删除
            throw new RenException(20001,"不能删除");
        } else { //不能查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            //成功  1>0   0>0
            return result>0;
        }
    }
    // 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
