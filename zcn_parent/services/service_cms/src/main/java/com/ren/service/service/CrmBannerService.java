package com.ren.service.service;

import com.ren.service.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-09-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    // 查询所有banner
    List<CrmBanner> selectAllBanner();

}
