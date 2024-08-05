package io.e4i2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChattingMessageDTO {
    
    private Integer messageId;
    private Integer chattingId;
    private LocalDateTime createdAt;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result{
        private int status;
        private String message;
        private String code;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private Result result;
        private ChattingMessageDTO data;
    }
}
