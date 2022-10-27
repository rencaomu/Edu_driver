package com.ren.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.service.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-08-12
 */
public interface EduTeacherService extends IService<EduTeacher> {

    // 分页查询
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
