package com.ren.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.service.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.service.entity.frontvo.CourseFrontVo;
import com.ren.service.entity.frontvo.CourseWebVo;
import com.ren.service.entity.vo.CourseInfoVo;
import com.ren.service.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
public interface EduCourseService extends IService<EduCourse> {

    // 添加课程信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id 查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    // 修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    // 根据课程id查询确认信息
    CoursePublishVo publishCourseInfo(String id);

    // 删除课程方法
    void removeCourse(String courseId);
    // 条件查询课表带分页
    Map<String, Object> getCourseList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);


    // 课程详情
    CourseWebVo getBaseCourseInfo(String courseId);

}
