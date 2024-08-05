package io.e4i2.service.impl;

import io.e4i2.dto.ContentDTO;
import io.e4i2.entity.Content;
import io.e4i2.repository.ContentRepository;
import io.e4i2.request.ContentRequest;
import io.e4i2.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    
    private final ContentRepository contentRepository;
    
    @Override
    public ContentDTO getContentResponse(ContentRequest contentRequest) {
        Content content = contentRepository.findById(contentRequest.getContentId()).get();
        return new ContentDTO(200, "SUCCESS", "success", content.getThumbnail(), content.getContentTitle(), content.getTitle(), content.getDescription());
        
    }
}
