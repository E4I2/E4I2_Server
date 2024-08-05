package io.e4i2.service;

import io.e4i2.dto.ContentDTO;
import io.e4i2.request.ContentRequest;

public interface ContentService {
    
    
    ContentDTO getContentResponse(ContentRequest contentRequest);
}
