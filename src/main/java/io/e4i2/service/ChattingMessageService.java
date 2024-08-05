package io.e4i2.service;

import io.e4i2.create.ChattingMessageCreate;
import io.e4i2.dto.ChattingMessageDTO;
import io.e4i2.entity.ChattingMessage;

public interface ChattingMessageService {
    
    ChattingMessageDTO.Response messageSave(ChattingMessageCreate chattingMessageCreate);
}
