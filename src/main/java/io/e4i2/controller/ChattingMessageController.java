package io.e4i2.controller;


import io.e4i2.create.ChattingMessageCreate;
import io.e4i2.dto.ChattingMessageDTO;
import io.e4i2.service.ChattingMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chattingMessage")
@RequiredArgsConstructor
public class ChattingMessageController {
    
    private final ChattingMessageService chattingMessageService;
    
    @PostMapping
    public ChattingMessageDTO.Response messageSave(@Validated @RequestBody ChattingMessageCreate chattingMessageCreate) {
        return chattingMessageService.messageSave(chattingMessageCreate);
    }
}
