package com.ren.service.service;

import com.ren.service.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
public interface EduVideoService extends IService<EduVideo> {

    // 根据课程id删除小节
    void removeVideoByCourseId(String courseId);
}
