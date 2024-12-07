package com.atguigu.spzx.manger.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: FileUploadService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/4 下午6:58
 * Version 1.8
 */
public interface FileUploadService {
    String upload(MultipartFile file);
}

