package com.ren.srevice.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ren.srevice.OssService;
import com.ren.client.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
//    private static final java.util.UUID UUID = ;

    // 上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // 创建oss对象
        String uploadUrl = null;
        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
           //调用 oss输入流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();
            // 在文件名称中添加随机数
            String uuid = UUID.randomUUID().toString().replaceAll("-","-");
            fileName = uuid+fileName;

            // 把文件按照日期进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath+"/"+fileName;
            // 调用oss方法实现上传

            // 第一个参数bucket

            // 第二个上传到oss的文件路径

            // 第三个上传文件输入流

            ossClient.putObject(bucketName,fileName,inputStream);

            //关闭对象
            ossClient.shutdown();

            // 把上传文件的路径返回

            // 需要上传的路径
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }



}
