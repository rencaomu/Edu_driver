package com.ren.service.service;

import com.ren.service.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.service.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-09-07
 */
public interface EduSubjectService extends IService<EduSubject> {

    // 添加课程分类
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    // 课程分类列表
    List<OneSubject> getAllOneTwoSubject();
}
