package com.ren.demo;

import java.util.Scanner;

public class ScoreTestDemo {
    public static void main(String args[]) {
        System.out.println("请输入成绩：");
        Scanner n = new Scanner(System.in);
        int  score= n.nextInt();   //获取输入数据
        String str = ""; // 定义数据格式
        if(score>=95&&score<100) {
            str = "优秀";
        }else if(score>=85&&score<94) {
            str = "良好";
        }else if(score>=75&&score<84) {
            str = "中等";
        }else if(score>=60&&score<74) {
            str = "及格";
        }else if(score>=40&&score<59) {
            str = "补考";
        }else if(score>=30&&score<39) {
            str = "重修";
        }else if(score>=0&&score<29) {
            str = "留级";
        }else {          //再写一个算法来判断输入成绩分数是否超过100分或低于0分
            if(score<0||score>100) {
                str = "ERROR";
            }else {
                str = "成绩无效";
            }
        }
        System.out.println("你的成绩等级为："+str);    //输出
        n.close();   //关闭资源
    }
}
