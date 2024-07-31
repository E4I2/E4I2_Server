package io.e4i2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDTO {
    private Result result;
    private MessageData data;
    
    public ResponseDTO() {
    }
    
    public ResponseDTO(Result result) {
        this.result = result;
    }
    
    @Getter
    @Setter
    public static class MessageData {
        private String profileImageUrl;
        private List<Message> messages;
    }
    
    @Getter
    @Setter
    public static class Message {
        private String text;
    }
    
    @Getter
    @Setter
    public static class Result {
        private int status;
        private String message;
        private String code;
        
        public Result() {
        }
        
        public Result(int status, String message, String code) {
            this.status = status;
            this.message = message;
            this.code = code;
        }
    }
}
