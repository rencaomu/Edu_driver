package com.ren.service.controller;


import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.ren.commonutils.Result;
import com.ren.service.entity.CrmBanner;
import com.ren.service.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端banner显示
 * </p>
 *
 * @author test.java
 * @since 2022-09-27
 */
@RestController
@RequestMapping("/cmsservice/bannerfront")
//@CrossOrigin
public class FrontBannerController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "查询所有banner")
    @GetMapping("getAllBanner")
    public Result getAllBanner() {
       List<CrmBanner> list =  bannerService.selectAllBanner();
       return Result.ok().data("list",list);
    }

}

