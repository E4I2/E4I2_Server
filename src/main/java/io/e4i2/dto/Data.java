package io.e4i2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Data {
    
    private String profileImageUrl;
    private List<Message> messages;
}
