package com.ren.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    String uploadVideoAly(MultipartFile file);

    //删除多个视频
    void removeMoveAlyVideo(List videoIdList);
}
