package com.ren.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.commonutils.JwtUtils;
import com.ren.commonutils.Result;
import com.ren.entity.Order;
import com.ren.service.OrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //1 生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public Result saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderNo =
                orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        System.out.println("订单号:"+orderNo);
        return Result.ok().data("orderId",orderNo);
    }

    //2 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        System.out.println("支付状态"+orderId);
        return Result.ok().data("item",order);
    }

    //根据课程id和用户id查询订单表中订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);//支付状态 1代表已经支付
        int count = orderService.count(wrapper);
        if(count>0) { //已经支付
            return true;
        } else {
            return false;
        }
    }
}

