package com.ren.service.mapper;

import com.ren.service.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ren.service.entity.frontvo.CourseFrontVo;
import com.ren.service.entity.frontvo.CourseWebVo;
import com.ren.service.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublishCourseInfo(String courseId);

    // 查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
