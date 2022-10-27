package com.ren.demo.execl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoDate> {

  // 一行一行读取excel内容
    @Override
    public void invoke(DemoDate demoDate, AnalysisContext analysisContext) {
        System.out.printf("****"+demoDate);
    }
        //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    // 读取完成后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
