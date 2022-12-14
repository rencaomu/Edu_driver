package com.ren.service.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Chapter {
    private String id;
    private String title;

    // 展示小节
    private List<VideoVo> children = new ArrayList<>();

}
