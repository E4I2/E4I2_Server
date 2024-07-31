package io.e4i2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
public class DeviceEvtDTO {
    @NotBlank(message = "디바이스 아이디 값이 올바르지 않습니다.")
    private String deviceId;
    private Integer devicePk;
    @Pattern(regexp = "launch|LAUNCH|chat|CHAT|share|SHARE|", message = "이벤트 네임이 올바르지 않습니다.")
    @NotBlank(message = "이벤트 네임이 공백이나 null 이 있습니다.")
    private String eventName;


}
