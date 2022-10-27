package com.ren.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ren.service.entity.CrmBanner;
import com.ren.service.mapper.CrmBannerMapper;
import com.ren.service.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-09-27
 */
@Service

public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    // 查询所有banner
    @Cacheable(key="'selectBannerList'",value = "items")
    @Override
    public List<CrmBanner> selectAllBanner() {
        // 根据id进行降序排列
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");

        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
