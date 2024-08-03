package io.e4i2.service.impl;

import io.e4i2.repository.ChattingMessageRepository;
import io.e4i2.service.ChattingMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingMessageServiceImpl implements ChattingMessageService {
    
    private final ChattingMessageRepository chattingMessageRepository;
    
}
