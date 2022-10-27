package com.ren.mapper;

import com.ren.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author test.java
 * @since 2022-10-06
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // 根据天数查询注册人数
    Integer countRegister(String day);
}
