package io.e4i2.controller;


import io.e4i2.dto.ContentDTO;
import io.e4i2.request.ContentRequest;
import io.e4i2.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {
    
    private final ContentService contentService;
    
    @PostMapping
    public ContentDTO content(@Validated @RequestBody ContentRequest contentRequest) {
        if (contentRequest.getContentId() >= 10) {
            return contentService.getContentPrompt(contentRequest);
        }else {
            return contentService.getContentResponse(contentRequest);
        }
    }
}
