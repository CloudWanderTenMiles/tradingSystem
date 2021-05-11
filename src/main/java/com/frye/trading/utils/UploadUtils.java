package com.frye.trading.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author frye
 * 完成图片上传工具类
 */
@Component
public class UploadUtils {

    // 从application.yaml中获取图片上传路径
    private static String IMAGE_PATH;
    // 从application.yaml中获取浏览器访问地址
    private static String SERVER_PATH;

    // 注入
    @Value("${upload-path}")
    public void setImagePath(String imagePath) {
        IMAGE_PATH = imagePath;
    }

    @Value("${ip-address}")
    public void setServerPath(String serverPath) {
        SERVER_PATH = serverPath;
    }


    public static String upload(MultipartFile multipartFile) {
        // 更改文件名
        String suffix = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-","");
        String newFilename = "Image" + uuid + suffix;

        // 检测文件夹是否存在
        File dir = new File(IMAGE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File image = new File(IMAGE_PATH,newFilename);
        try {
            multipartFile.transferTo(image);
        } catch (IOException e) {
            return null;
        }
        return SERVER_PATH + newFilename;
    }
}
