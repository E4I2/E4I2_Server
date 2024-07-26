package io.e4i2.service;

import io.e4i2.entity.Mbti;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
    List<String> saveFile(List<MultipartFile> multipartFiles, Mbti mbti);
    int deleteFile(List<String> fileUrl);
}
