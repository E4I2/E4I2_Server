package io.e4i2.dto;

import io.e4i2.entity.Mbti;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMessage {
    private String message;
    private Mbti mbti;
    
}
