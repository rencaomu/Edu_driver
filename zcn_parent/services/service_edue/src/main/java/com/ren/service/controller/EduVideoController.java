package com.ren.service.controller;


import com.ren.commonutils.Result;
import com.ren.service.client.VodClient;
import com.ren.service.entity.EduChapter;
import com.ren.service.entity.EduVideo;
import com.ren.service.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-09-09
 */
@RestController
@RequestMapping("/service/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    // 注入VodClient
    @Autowired
    private VodClient vodClient;
    //添加小节
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return Result.ok();
    }

    //删除小节
    @DeleteMapping("{id}")
    public Result deleteVideo(@PathVariable String id) {
       //根据小节id获取视频id
         EduVideo eduVideo = videoService.getById(id);
         String videoSourceId = eduVideo.getVideoSourceId();
      //调用删除视频
        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeVideo(videoSourceId);
        }
        // 删除小节
        videoService.removeById(id);
        return Result.ok();
    }
    //查询小节信息
    @ApiOperation(value = "查询课程章节小节")
    @GetMapping("{id}")
    public Result getVideoById(
            @ApiParam(name = "id", value = "课程章节小节", required = true)
            @PathVariable String id){
        try {
            EduVideo eduVideo = videoService.getById(id);
            return  Result.ok().data("eduVideo",eduVideo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
    //修改小节
    @ApiOperation(value = "修改课程章节小节")
    @PutMapping("update")
    public Result updateVideo(
            @ApiParam(name = "video", value = "课程章节小节对象", required = true)
            @RequestBody EduVideo video){
        boolean update = videoService.updateById(video);
        if(update){
            return Result.ok();
        }
        return Result.error();
    }
}

