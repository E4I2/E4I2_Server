package io.e4i2.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentRequest {
    
    @NotNull(message = "콘텐츠 Id 값이 null 입니다.")
    private Integer contentId;
}
