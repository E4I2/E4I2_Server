package io.e4i2.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
    List<String> saveFile(List<MultipartFile> multipartFiles);
    int deleteFile(List<String> fileUrl);
}
