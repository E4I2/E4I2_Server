package io.e4i2.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    
    private  final Integer code;
    
    private final String message;
    
    private List<Map<String,String>> validation;
    
    public void addValidation(String fieldName, String errorMessage) {
        if (this.validation == null) {
            this.validation = new ArrayList<>();
        }
        Map<String, String> map = new HashMap<>();
        map.put(fieldName, errorMessage);
        this.validation.add(map);
    }
}