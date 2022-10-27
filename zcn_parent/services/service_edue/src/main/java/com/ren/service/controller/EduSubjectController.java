package com.ren.service.controller;


import com.ren.commonutils.Result;
import com.ren.service.entity.subject.OneSubject;
import com.ren.service.service.EduSubjectService;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-09-07
 */
@RestController
@RequestMapping("/service/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类

    // 获取上传的文件再进行读取
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return  Result.ok();
    }

    // 课程列表分类
    @GetMapping("getAllSubject")
    public Result getAllSubject() {
    // 泛型为二级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return  Result.ok().data("list",list);
    }

}

