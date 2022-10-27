package com.ren.service;

import com.ren.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-10-06
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //登录的方法
    String login(UcenterMember member);

    //注册接口
    void register(RegisterVo registerVo);

    // 查新数据库有没有重复的Id
    UcenterMember getOpenIdMember(String oppenid);

    // 查询注册天数
    Integer countRegisterDay(String day);
}
