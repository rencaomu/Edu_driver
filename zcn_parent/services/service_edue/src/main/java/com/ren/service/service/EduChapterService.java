package com.ren.service.service;

import com.ren.service.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.service.entity.chapter.Chapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
public interface EduChapterService extends IService<EduChapter> {

    // 根据课程id查询章节小节
    List<Chapter> getChapterVideoByCourseId(String courseId);

    // 删除章节
    boolean deleteChapter(String chapterId);

    // 根据课程id删除章节
    void removeChapterByCourseId(String courseId);

}
