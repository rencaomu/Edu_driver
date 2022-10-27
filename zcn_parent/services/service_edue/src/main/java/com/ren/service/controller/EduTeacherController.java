package com.ren.service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.commonutils.Result;
import com.ren.service.entity.EduTeacher;
import com.ren.service.entity.vo.TeacherQuery;
import com.ren.service.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-08-12
 */
@RestController
@RequestMapping("/service/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    // 查询教师的数据
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("findAll")
    public Result findAllTeacher(){
       List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public Result removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true ) @PathVariable String id) {

         boolean flag = eduTeacherService.removeById(id);
         if (flag){
             return Result.ok();
         }else {
             return Result.error();
         }

    }

    // 分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable long current,
                                  @PathVariable long limit) {
        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        eduTeacherService.page(pageTeacher,null);

        // 总记录数
        long total = pageTeacher.getTotal();

        // 数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();

        return Result.ok().data("total",total).data("rows",records);
    }

    // 条件查询
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@PathVariable long current,
                                       @PathVariable long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {


        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        // 多条件组合查询

        String level = teacherQuery.getLevel();
        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 判断条件是否为空
        if (!StringUtils.isEmpty(name)) {

            wrapper.like("name", name);

        }

        if (!StringUtils.isEmpty(level) ) {

            wrapper.eq("level", level);

        }

        if (!StringUtils.isEmpty(begin)) {

            wrapper.ge("gmt_create", begin);

        }

        // 排序
        wrapper.orderByDesc("gmt_create");

        // 调用方法实现条件查询分页
        if (!StringUtils.isEmpty(end)) {

            wrapper.le("gmt_create", end);

        }

        eduTeacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return Result.ok().data("total",total).data("rows",records);
    }

    // 添加讲师接口
    @ApiOperation(value = "添加讲师接口")
    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    // 根据id进行查询
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return Result.ok().data("teacher",eduTeacher);
    }
    // 修改讲师
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }

    }
}

