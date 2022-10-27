package com.ren.service.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.commonutils.Result;
import com.ren.service.entity.EduCourse;
import com.ren.service.entity.EduTeacher;
import com.ren.service.service.EduCourseService;
import com.ren.service.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service/indexFront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "查询首页的名师接口")
    @GetMapping("index")
    public Result index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return Result.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
