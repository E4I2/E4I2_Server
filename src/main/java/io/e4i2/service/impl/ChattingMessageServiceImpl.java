package io.e4i2.service.impl;

import io.e4i2.create.ChattingMessageCreate;
import io.e4i2.dto.ChattingMessageDTO;
import io.e4i2.dto.ChattingRoomDTO;
import io.e4i2.entity.ChattingMessage;
import io.e4i2.entity.ChattingRoom;
import io.e4i2.repository.ChattingMessageRepository;
import io.e4i2.repository.ChattingRoomRepository;
import io.e4i2.service.ChattingMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingMessageServiceImpl implements ChattingMessageService {
    
    private final ChattingMessageRepository chattingMessageRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    
    @Override
    public ChattingMessageDTO.Response messageSave(ChattingMessageCreate chattingMessageCreate) {
        
        ChattingRoom chattingRoom = chattingRoomRepository.findById(chattingMessageCreate.getChattingId()).get();
        ChattingMessage chattingMessage = new ChattingMessage(
                chattingMessageCreate.getMessageContent(),
                chattingMessageCreate.getSender());
        chattingMessage.setChattingRoom(chattingRoom);
        ChattingMessage save = chattingMessageRepository.save(chattingMessage);
        
        return new ChattingMessageDTO.Response(
                new ChattingMessageDTO.Result(200, "SUCCESS", "success"),
                new ChattingMessageDTO(save.getMessageId(), save.getChattingRoom().getChattingId(), save.getCreatedAt())
        );
    }
}
