package com.ren.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.commonutils.Result;
import com.ren.service.entity.EduCourse;
import com.ren.service.entity.EduTeacher;
import com.ren.service.entity.vo.CourseInfoVo;
import com.ren.service.entity.vo.CoursePublishVo;
import com.ren.service.entity.vo.CourseQuery;
import com.ren.service.entity.vo.TeacherQuery;
import com.ren.service.service.EduCourseService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
@RestController
@RequestMapping("/service/course")
//@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    // 课程列表
    @GetMapping
    public Result getCourseList () {
        List<EduCourse> list = eduCourseService.list(null);
        return Result.ok().data("list",list);
    }

    // TODO 条件查询带分页
    // 条件查询
    @PostMapping("pageCourse/{current}/{limit}")
    public Result pageCourse(@PathVariable long current,
                                       @PathVariable long limit,
                                       @RequestBody(required = false) CourseQuery courseQuery) {


        Page<EduCourse> pageCourse = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 多条件组合查询

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        // 判断条件是否为空
        if (!StringUtils.isEmpty(title)) {

            wrapper.like("title", title);

        }

        if (!StringUtils.isEmpty(teacherId) ) {

            wrapper.eq("teacher_id", teacherId);

        }

        if (!StringUtils.isEmpty(subjectParentId)) {

            wrapper.ge("subject_parent_id", subjectParentId);

        }

        // 排序
        wrapper.orderByDesc("subject_parent_id");

        // 调用方法实现条件查询分页
        if (!StringUtils.isEmpty(subjectId)) {

            wrapper.le("subject_id", subjectId);

        }

        eduCourseService.page(pageCourse,wrapper);

        long total = pageCourse.getTotal();
        List<EduCourse> list = pageCourse.getRecords();
        return Result.ok().data("total",total).data("list",list);
    }



    // 添加课程基本信息
    @PostMapping("addCourseInfo")
    public Result addCourseInfo (@RequestBody CourseInfoVo courseInfoVo) {

   String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",id);
    }

    //根据课程id 查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId){
      CourseInfoVo courseInfoVo =  eduCourseService.getCourseInfo(courseId);
      return  Result.ok().data("courseInfoVo",courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    // 根据课程id查询确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id){
      CoursePublishVo coursePublishVo =  eduCourseService.publishCourseInfo(id);
        return Result.ok().data("publishCourse",coursePublishVo);
    }

    // 最终发布
    @PostMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable String id){
         EduCourse eduCourse = new EduCourse();
         eduCourse.setId(id);
         eduCourse.setStatus("Normal"); // 设置课程状态
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }

    // 删除课程
    @DeleteMapping("{courseId}")
    public Result deleteCourse(@PathVariable String courseId){
        eduCourseService.removeCourse(courseId);
        return Result.ok();
    }
}

