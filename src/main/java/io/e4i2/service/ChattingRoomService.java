package io.e4i2.service;

import io.e4i2.create.ChattingRoomCreate;
import io.e4i2.dto.ChattingRoomDTO;

public interface ChattingRoomService {
    
    public ChattingRoomDTO.Response createChattingRoom(ChattingRoomCreate chattingRoomCreate);
}
