package io.e4i2.controller;


import io.e4i2.create.ChattingRoomCreate;
import io.e4i2.dto.ChattingRoomDTO;
import io.e4i2.service.ChattingRoomService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChattingController {
    
    private final ChattingRoomService chattingRoomService;
    
    @PostMapping("/")
    public ChattingRoomDTO save(ChattingRoomCreate chattingRoomCreate) {
        return chattingRoomService.createChattingRoom(chattingRoomCreate);
    }
    
}
