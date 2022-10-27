package com.ren.client;

import com.ren.commonutils.ordervo.CourseWebVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface EduClient {

    // 根据课程id查询课程信息
    @PostMapping("/service/coursefront/getCourseOrderInfo/{id}")
    public CourseWebVo getCourseOrderInfo(@PathVariable("id") String id);

}