package com.ren.service.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.commonutils.Result;
import com.ren.service.entity.EduCourse;
import com.ren.service.entity.EduTeacher;
import com.ren.service.service.EduCourseService;
import com.ren.service.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "前端讲师查询")
@RestController
@RequestMapping("/service/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    // 分页查询讲师
    @PostMapping("getTeacherPage/{page}/{limit}")
    public Result getTeacherPage (@PathVariable long page,@PathVariable long limit){

        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
       Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);

        // 返回分页中的所有数据


        return Result.ok().data(map);
    }

    @ApiOperation(value = "根据id查询讲师信息")
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId) {

        EduTeacher eduTeacher = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return Result.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }

}
