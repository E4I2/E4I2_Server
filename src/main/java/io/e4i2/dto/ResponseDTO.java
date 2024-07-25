package io.e4i2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDTO {
    private Result result;
    private MessageData data;
    
    @Getter
    @Setter
    public static class MessageData {
        private String profileImageUrl;
        private List<MessageWrapper> messages;
    }
    
    @Getter
    @Setter
    public static class MessageWrapper {
        private Message message;
    }
    
    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;
    }
    
    @Getter
    @Setter
    public static class Result {
        private int status;
        private String message;
        private String code;
    }
}
