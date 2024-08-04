package io.e4i2.service.impl;

import io.e4i2.create.ChattingRoomCreate;
import io.e4i2.dto.ChattingRoomDTO;
import io.e4i2.entity.ChattingRoom;
import io.e4i2.repository.ChattingMessageRepository;
import io.e4i2.service.ChattingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingRoomServiceImpl implements ChattingRoomService {
    
    private final ChattingMessageRepository chattingMessageRepository;
    
    @Override
    public ChattingRoomDTO createChattingRoom(ChattingRoomCreate chattingRoomCreate) {
        return null;
    }
}
