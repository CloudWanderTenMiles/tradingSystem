package com.frye.trading.controller;

import com.frye.trading.utils.DataJsonUtils;
import com.frye.trading.utils.UploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author frye
 * 上传图片的controller
 */

@RequestMapping("/upload")
@Controller
public class UploadController {

    @RequestMapping("/image")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        String imagePath = UploadUtils.upload(file);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (imagePath != null) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("upload successfully!");
            Map<String,String> map = new LinkedHashMap<>();
            map.put("src",imagePath);
            dataJsonUtils.setData(map);
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("upload error!");
        }
        return dataJsonUtils.toString();
    }
}
