package io.e4i2.dto;


import io.e4i2.entity.ChattingMessage;
import io.e4i2.entity.Device;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChattingRoomDTO {
    
    
    private Integer chattingId;
    private LocalDateTime createdAt;
    

    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private int status;
        private String message;
        private String code;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Result result;
        private ChattingRoomDTO data;
    }
}