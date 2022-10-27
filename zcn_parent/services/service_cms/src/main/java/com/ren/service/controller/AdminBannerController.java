package com.ren.service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.commonutils.Result;
import com.ren.service.entity.CrmBanner;

import com.ren.service.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-09-27
 */
@RestController
@RequestMapping("/cmsservice/banneradmin")
//@CrossOrigin
public class AdminBannerController {

    @Autowired
    private CrmBannerService bannerService;

    // 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public  Result pageBanner(@PathVariable long page ,@PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return Result.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    // 添加banner
    @ApiOperation(value = "添加banner")
    @PostMapping("addBanner")
    public  Result addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return Result.ok();
    }
    // 修改banner
    @ApiOperation(value = "修改banner")
    @PutMapping("update")
    public Result update (@RequestBody CrmBanner banner){
        bannerService.updateById(banner);
        return Result.ok();
    }

    // 根据id查询banner
    @ApiOperation(value = "根据id查询banner")
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id){
         CrmBanner banner = bannerService.getById(id);
         return Result.ok().data("item",banner);
    }

    // 删除banner
    @ApiOperation(value = "删除banner")
    @DeleteMapping("remove/{id}")
    public Result remove (@PathVariable String id){
        bannerService.removeById(id);
        return  Result.ok();
    }

}

