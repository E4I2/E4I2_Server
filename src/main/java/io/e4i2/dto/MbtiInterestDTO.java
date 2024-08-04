package io.e4i2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MbtiInterestDTO {

    private int InterestId;

    private int memoId;

    @NotBlank(message = "관심사값이 올바르지 않습니다.")
    private String Interest;

}
