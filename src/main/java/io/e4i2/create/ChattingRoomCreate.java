package io.e4i2.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class ChattingRoomCreate {
    
    @NotBlank(message = "디바이스 네임을 입력해주세요")
    private String deviceName;
    
    
}
