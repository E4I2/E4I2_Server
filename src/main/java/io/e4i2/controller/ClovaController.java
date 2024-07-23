package io.e4i2.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.e4i2.service.ClovaStudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clova")
public class ClovaController {
    
    private final ClovaStudioService clovaStudioService;
    
    @PostMapping("/chat")
    public String chat(@RequestBody String messages) {
        
        return clovaStudioService.getResponse(messages);
    }
}
