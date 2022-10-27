package com.ren.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.exception.RenException;
import com.ren.service.entity.EduCourse;
import com.ren.service.entity.EduCourseDescription;
import com.ren.service.entity.EduTeacher;
import com.ren.service.entity.frontvo.CourseFrontVo;
import com.ren.service.entity.frontvo.CourseWebVo;
import com.ren.service.entity.vo.CourseInfoVo;
import com.ren.service.entity.vo.CoursePublishVo;
import com.ren.service.mapper.EduCourseMapper;
import com.ren.service.service.EduChapterService;
import com.ren.service.service.EduCourseDescriptionService;
import com.ren.service.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.service.service.EduVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    // 课程描述的注入
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    // 小节
    @Autowired
    private EduVideoService videoService;

    // 章节
    @Autowired
    private EduChapterService chapterService;

    // 添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 添加课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0){
            // 添加失败
            throw new RenException(20001,"添加课程失败");
        }
        // 获取添加之后的课程id
        String id = eduCourse.getId();

        //向课程表添加信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        // 设置id相关联
        eduCourseDescription.setId(id);
        eduCourseDescriptionService.save(eduCourseDescription);
       // 返回课程id
        return  id;
    }
    //根据课程id 查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //查询课程表
         EduCourse eduCourse = baseMapper.selectById(courseId);
         CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        // 查询描述信息
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    // 修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        final EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
         int update = baseMapper.updateById(eduCourse);
        if (update == 0){
            throw new RenException(20001,"修改失败");
        }

         EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }
    // 根据课程id查询确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
    // 删除课程方法
    @Override
    public void removeCourse(String courseId){

        //根据课程id删除课程小节
        videoService.removeVideoByCourseId(courseId);

        // 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        // 删除描述
        eduCourseDescriptionService.removeById(courseId);

        // 删除课程
         int result = baseMapper.deleteById(courseId);
        if(result == 0){
            throw new RenException(20001,"删除失败");
        }
    }

    // 条件查询课表带分页
    @Override
    public Map<String, Object> getCourseList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }


    // 课程详情
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
