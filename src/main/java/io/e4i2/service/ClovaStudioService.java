package io.e4i2.service;

import io.e4i2.dto.ResponseDTO;
import io.e4i2.dto.UserMessage;

public interface ClovaStudioService {
    ResponseDTO getResponse(UserMessage userMessage);
}
