package io.e4i2.controller;


import io.e4i2.dto.ResponseDTO;
import io.e4i2.service.ClovaStudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clova")
public class ClovaController {
    
    private final ClovaStudioService clovaStudioService;



    @PostMapping("/chat")
    public ResponseDTO chat(@RequestBody String messages) {
        
        return clovaStudioService.getResponse(messages);
    }
}
