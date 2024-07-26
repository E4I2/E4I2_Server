package io.e4i2.controller;


import io.e4i2.entity.Mbti;
import io.e4i2.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
public class FileController {
    
    
    private final S3Service s3Service;
    
    @PostMapping("/upload")
    public List<String> uploadFile(@RequestParam("files") List<MultipartFile> files, @RequestParam("mbti") Mbti mbti) {
        return s3Service.saveFile(files,mbti);
    }
    
    @DeleteMapping("/delete")
    public int deleteFile(@RequestBody List<String> files) {
        return s3Service.deleteFile(files);
    }
    
    
}
