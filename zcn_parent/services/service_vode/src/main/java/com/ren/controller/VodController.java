package com.ren.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ren.commonutils.Result;
import com.ren.exception.RenException;
import com.ren.service.VodService;
import com.ren.client.ConstantVodUtils;
import com.ren.client.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传视频的方法
    @PostMapping("uploadAIyiVideo")
    public Result uploadAIyiVideo(MultipartFile file) {
       String videoId = vodService.uploadVideoAly(file);

        return Result.ok().data("videoId",videoId);
    }

    // 根据视频id删除视频
    @DeleteMapping("removeAlyVideo/{id}")
    public Result removeAlyVideo (@PathVariable String id) {
        try {
            // 初始化对象
             DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
             // 创建删除的request对象
             DeleteVideoRequest request = new DeleteVideoRequest();
             // 向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象实现删除
            client.getAcsResponse(request);

            return Result.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new RenException(20001,"删除视频失败~");
        }

    }
    // 删除多个视频
    @DeleteMapping("delete-batch")
    public Result deleteBach(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoveAlyVideo(videoIdList);
        return Result.ok();
    }
    // 根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth (@PathVariable String id) {
        try{
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            //创建获取凭证
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(id);

            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        }catch (Exception e){
            throw new RenException(20001,"获取凭证失败");
        }
    }
}
