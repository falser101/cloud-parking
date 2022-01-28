package com.falser.cloud.common.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileUtil {

    private final COSClient cosClient;

    public FileUtil(COSClient cosClient) {
        this.cosClient = cosClient;
    }

    public String upload(InputStream inputStream) throws IOException {
        // 指定要上传的文件
        // 指定文件将要存放的存储桶
        String bucketName = "bashuculture-1301017257";
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".jpg";
        ObjectMetadata metadata = new ObjectMetadata();
        // 设置输入流长度（STREAMLENGTH根据自己流大小做替换）
        metadata.setContentLength(inputStream.available());
        // 设置 Content type, 默认是 application/octet-stream
        metadata.setContentType("image/jpeg");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return bucketName + key;
    }

    public String upload(File file) {
        // 指定要上传的文件
        // 指定文件将要存放的存储桶
        String bucketName = "bashuculture-1301017257";
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return key;
    }
}
