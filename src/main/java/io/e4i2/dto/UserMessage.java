package io.e4i2.dto;

import io.e4i2.entity.Mbti;
import io.e4i2.validate.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserMessage {
    @NotBlank(message = "메시지가 올바르지 않습니다.")
    private String message;
    @ValidEnum(enumClass = Mbti.class , message = "MBTI 값이 올바르지 않습니다.")
    private Mbti mbti;

    private String name;
    private String age;
    private String sex;
    private String relation;
    private List<String> interest;
    
}
