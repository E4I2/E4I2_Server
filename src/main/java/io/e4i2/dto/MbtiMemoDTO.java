package io.e4i2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MbtiMemoDTO {
    
    private Result result;
    private MbtiMemoData data;
    
    @Getter
    @Setter
    public static class Result {
        private int status;
        private String message;
        private String code;
        
        public Result() {}
        
        public Result(int status, String message, String code) {
            this.status = status;
            this.message = message;
            this.code = code;
        }
    }
}