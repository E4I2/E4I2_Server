package io.e4i2.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ContentDTO {
    
    
    private Result result;
    private DataContent data;
    
    @Data
    public static class Result {
        private int status;
        private String message;
        private String code;
    }
    
    @Data
    public static class DataContent {
        private String thumbnail;
        private String contentTitle;
        private String title;
        private String description;
    }
    
    public ContentDTO(int status, String message, String code, String thumbnail, String contentTitle, String title, String description) {
        this.result = new Result();
        this.result.setStatus(status);
        this.result.setMessage(message);
        this.result.setCode(code);
        
        this.data = new DataContent();
        this.data.setThumbnail(thumbnail);
        this.data.setContentTitle(contentTitle);
        this.data.setTitle(title);
        this.data.setDescription(description);
    }
}