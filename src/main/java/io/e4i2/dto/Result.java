package io.e4i2.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private int status;
    private String message;
    private String code;
}
