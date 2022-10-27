package com.ren.demo.execl;

import com.alibaba.excel.EasyExcel;


import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        // 实现excel的写操作
        // 1 设置写入的地址和excel名称
//        String fileName = "G:\\ren.xlsx";
//
//        // 2 调用easyexcel的方法实现写操作
//        EasyExcel.write(fileName,DemoDate.class).sheet("学生列表").doWrite(getData());

        // 实现excel的读操作
        String fileName = "G:\\\\ren.xlsx";
        EasyExcel.read(fileName,DemoDate.class,new ExcelListener()).sheet().doRead();

    }
    // 创建一个方法
    private static List<DemoDate> getData() {
        List<DemoDate> list = new ArrayList<>();
        for (int i = 0;i<10 ;i++){
            DemoDate date = new DemoDate();
            date.setSname("ren"+i);
            date.setSno(i);
            list.add(date);
        }
        return  list;
    }
}
