package com.s008.smartcanteen.controller;

import com.s008.smartcanteen.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    // 临时存放在项目本地，后期可以改成本地绝对路径
    private String uploadPath = System.getProperty("user.dir") + "/uploads/";

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }

        // 生成唯一文件名防止覆盖
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        try {
            file.transferTo(new File(uploadPath + fileName));
            // 返回图片的访问路径
            return Result.success("/uploads/" + fileName);
        } catch (IOException e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }
}