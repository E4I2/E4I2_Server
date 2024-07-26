package io.e4i2.service.impl;

import io.e4i2.entity.Mbti;
import io.e4i2.entity.UploadFile;
import io.e4i2.repository.UploadFileRepository;
import io.e4i2.s3.S3FileUtils;
import io.e4i2.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class S3ServiceImpl implements S3Service {
    private final UploadFileRepository uploadFileRepository;
    private final S3FileUtils s3FileUtils;
    
    public List<String> saveFile(List<MultipartFile> multipartFiles, Mbti mbti) {
        List<UploadFile> uploadFiles = s3FileUtils.storeFiles(multipartFiles,mbti);
        uploadFileRepository.saveAll(uploadFiles);
        return uploadFiles.stream()
                .map(UploadFile::getFileUrl)
                .collect(Collectors.toList());
    }
    
    public int deleteFile(List<String> fileUrl) {
        for (String urlFile : fileUrl) {
            s3FileUtils.deleteFile(urlFile);
        }
        return uploadFileRepository.deleteByFileUrlIn(fileUrl);
    }
}
