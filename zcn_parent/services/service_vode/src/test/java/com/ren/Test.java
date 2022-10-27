package com.ren;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ren.client.InitVodClient;

import java.util.List;

public class Test {
    // 上传视频
    public static void main(String[] args) throws Exception {
        String accessKeyId = "LTAI5tNSHwpHMTG1GDynZZds";
        String accessKeySecret = "BGJkZTC9gqc14egSTvfmyKPX8pctJr";
        String title = "mall";
        String fileName = "D:/EnglishInstallProject/443.mp4";

        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

        getAuthor();
    }
    // 获取视频凭证
    public static void getAuthor() throws Exception {
        // 根据视频id获取播放凭证
        //创建初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tNSHwpHMTG1GDynZZds", "BGJkZTC9gqc14egSTvfmyKPX8pctJr");
        // 创建获取视频凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 想request中设置视频id
        request.setVideoId("033b1eb24b464a90bb47729a2ba36c12");
        response = client.getAcsResponse(request);
        System.out.printf("playAuthor:"+response.getPlayAuth());
    }

    // 获取视频地址
    public static void getPlayUrl() throws Exception{
        // 根据视频id获取视频地址

        // 创建初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tNSHwpHMTG1GDynZZds","BGJkZTC9gqc14egSTvfmyKPX8pctJr");
        // 创建视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        request.setVideoId("5d1fc887c70f4e1abdf9f5bd4c89aa75");
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
