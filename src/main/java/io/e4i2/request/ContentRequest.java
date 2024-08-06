package io.e4i2.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ContentRequest {
    
    @NotNull(message = "콘텐츠 Id 값이 null 입니다.")
    private Integer contentId;
    
    private String mbti;
    private String name;
    private String age;
    private String sex;
    private String relation;
    private List <String> interest;
    
}
