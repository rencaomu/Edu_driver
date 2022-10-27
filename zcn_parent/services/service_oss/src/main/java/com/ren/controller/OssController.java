package com.ren.controller;

import com.ren.commonutils.Result;
import com.ren.srevice.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;
    @PostMapping
    public Result uploadOssFile(MultipartFile file) {
        // 获取上传的文件 MultipartFile

        // 返回上传的oss路径
        String url = ossService.uploadFileAvatar(file);

        return Result.ok().data("url",url);
    }
}
