package com.ren.demo.execl;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoDate {
    // 设置excel的表头内容
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
