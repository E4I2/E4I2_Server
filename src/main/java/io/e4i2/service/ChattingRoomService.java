package io.e4i2.service;

import io.e4i2.create.ChattingRoomCreate;
import io.e4i2.dto.ChattingRoomDTO;
import io.e4i2.entity.ChattingRoom;

public interface ChattingRoomService {
    
    public ChattingRoomDTO createChattingRoom(ChattingRoomCreate chattingRoomCreate);
}
