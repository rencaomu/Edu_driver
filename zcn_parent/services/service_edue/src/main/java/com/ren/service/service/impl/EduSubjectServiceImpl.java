package com.ren.service.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.service.entity.EduSubject;
import com.ren.service.entity.excel.SubjectData;
import com.ren.service.entity.subject.OneSubject;
import com.ren.service.entity.subject.TwoSubject;
import com.ren.service.listener.SubjectExcelListener;
import com.ren.service.mapper.EduSubjectMapper;
import com.ren.service.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-09-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            // 文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 查询所有的一级分类

        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> oneSubjectList =  baseMapper.selectList(wrapperOne);

        //查询所有的二级分类

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id",0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建一个List存储最终数据
        List<OneSubject> finalSubjectList = new ArrayList<>();


        // 分装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++) { //遍历one的集合
            // 得到每个one中的对象
            EduSubject eduSubject = oneSubjectList.get(i);
            // 把eduSubject的值获取到，放到one中
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            // 把subject中的值放到oneSubject中
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            // 封装二级分类
            // 在一级分类的循环中遍历所有的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            // 遍历二级分类
            for (int m = 0; m < twoSubjectList.size(); m++) {
                 EduSubject tSubject = twoSubjectList.get(m);
                 // 判断二级分类的parent_id是否为一级分类的id
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    // 如果一样就进行传值
                     TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            // 把一级分类下面的二级分类再放回去
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }
}
