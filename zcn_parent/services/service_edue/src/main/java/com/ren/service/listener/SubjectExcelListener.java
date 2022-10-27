package com.ren.service.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.exception.RenException;
import com.ren.service.entity.EduSubject;
import com.ren.service.entity.excel.SubjectData;
import com.ren.service.service.EduSubjectService;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {


    public EduSubjectService subjectService;
    public SubjectExcelListener(){}
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    // 读取excel的内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new RenException(20001,"文件数据为空");
        }
        // 以行读取
        // 先判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null){// 没有重复进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);

        }

        // 获取一级分类的id值
        String pid = existOneSubject.getId();
        // 判断二级分类
        EduSubject existTwoSubject = this.excelTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    // 判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    // 判断二级分类不能重复添加
    private EduSubject excelTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
