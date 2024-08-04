package io.e4i2.dto;


import io.e4i2.entity.ChattingMessage;
import io.e4i2.entity.Device;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class ChattingRoomDTO {
    
    
    private Integer chattingId;
    
    private Device device;
    
    private List<ChattingMessage> chattingMessages;
    
    private LocalDateTime createdAt;
    

    
    
}
