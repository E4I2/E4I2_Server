package io.e4i2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@NoArgsConstructor
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String originalFilename;
    private String locationFilename;
    private String fileUrl;
    private LocalDateTime uploadTime;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    
    public UploadFile(String originalFilename, String storeFilename, String fileUrl,Mbti mbti) {
        this.originalFilename = originalFilename;
        this.locationFilename = storeFilename;
        this.fileUrl = fileUrl;
        this.uploadTime = LocalDateTime.now();
        this.mbti = mbti;
    }
}
