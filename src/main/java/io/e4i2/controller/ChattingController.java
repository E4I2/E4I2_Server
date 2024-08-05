package io.e4i2.controller;


import io.e4i2.create.ChattingRoomCreate;
import io.e4i2.dto.ChattingRoomDTO;
import io.e4i2.service.ChattingRoomService;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChattingController {
    
    private final ChattingRoomService chattingRoomService;
    
    @PostMapping
    public ChattingRoomDTO.Response chattingRoomSave(@Validated @RequestBody ChattingRoomCreate chattingRoomCreate) {
        return chattingRoomService.createChattingRoom(chattingRoomCreate);
    }
   
    
}
