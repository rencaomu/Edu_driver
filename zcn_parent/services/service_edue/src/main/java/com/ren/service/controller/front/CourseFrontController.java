package com.ren.service.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.commonutils.JwtUtils;
import com.ren.commonutils.Result;
import com.ren.service.client.OrderClient;
import com.ren.service.entity.EduCourse;
import com.ren.service.entity.EduTeacher;
import com.ren.service.entity.chapter.Chapter;
import com.ren.service.entity.frontvo.CourseFrontVo;
import com.ren.service.entity.frontvo.CourseWebVo;
import com.ren.service.service.EduChapterService;
import com.ren.service.service.EduCourseService;
import com.ren.service.service.EduTeacherService;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(value = "前端讲师查询")
@RestController
@RequestMapping("/service/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduCourseService courseService;

    // 条件查询课表带分页
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseList(pageCourse,courseFrontVo);
        return  Result.ok().data(map);
    }

    // 课程详情
    @GetMapping("getFrontInfo/{courseId}")
    public Result getFrontInfo(@PathVariable String courseId, HttpServletRequest request) {
       CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        List<Chapter> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        // 根据课程id和用户id查询当前课程是否已经支付
        String token = JwtUtils.getMemberIdByJwtToken(request);
        boolean isBuy = false;
        if (!StringUtils.isEmpty(token)) {
            isBuy = orderClient.isBuyCourse(courseId,token);
        }
        return Result.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",isBuy);
    }

    // 根据课程id查询课程信息
    @PostMapping("getCourseOrderInfo/{id}")
    public CourseWebVo getCourseOrderInfo(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVo courseOrder = new CourseWebVo();
        BeanUtils.copyProperties(courseInfo,courseOrder);

        return  courseOrder;
    }
}
