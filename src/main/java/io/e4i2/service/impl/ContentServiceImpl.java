package io.e4i2.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.e4i2.dto.ContentDTO;
import io.e4i2.entity.Content;
import io.e4i2.entity.ContentPrompt;
import io.e4i2.repository.ContentPromptRepository;
import io.e4i2.repository.ContentRepository;
import io.e4i2.request.ContentRequest;
import io.e4i2.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ContentServiceImpl implements ContentService {
    
    
    @Value("${clovastudio.api.url}")
    private String apiUrl;
    
    @Value("${clovastudio.api.key}")
    private String apiKey;
    
    @Value("${apigw.api.key}")
    private String apigwApiKey;
    
    @Value("${clovastudio.api.request.id}")
    private String requestId;
    
    private final ContentRepository contentRepository;
    private final ContentPromptRepository contentPromptRepository;
    private final ObjectMapper objectMapper;
    
    @Override
    public ContentDTO getContentResponse(ContentRequest contentRequest) {
        Content content = contentRepository.findById(contentRequest.getContentId()).get();
        return new ContentDTO(200, "SUCCESS", "success", content.getThumbnail(), content.getContentTitle(), content.getTitle(), content.getDescription());
    }
    
    @Override
    public ContentDTO getContentPrompt(ContentRequest contentRequest) {
        // 1. ContentPrompt에서 contentPrompt를 가져옵니다.
        ContentPrompt findByContentPrompt = contentPromptRepository.findById(contentRequest.getContentId())
                .orElseThrow(() -> new RuntimeException("ContentPrompt not found"));
        String contentPrompt = findByContentPrompt.getContentPrompt();
        
        // 2. 클로바 스튜디오 API에 요청을 보냅니다.
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeader();
        Map<String, Object> requestBody = getRequestBody(contentPrompt);
        
        try {
            String jsonRequestBody = objectMapper.writeValueAsString(requestBody);
            log.info("Request Body: {}", jsonRequestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();
            
            // 3. API 응답을 받아서 ContentDTO 객체를 생성합니다.
            return parseResponse(responseBody);
            
        } catch (Exception e) {
            
            throw new RuntimeException("Failed to get response from Clova Studio API", e);
        }
    }
    
    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apigwApiKey);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
        headers.set("Content-Type", "application/json");
        return headers;
    }
    
    private Map<String, Object> getRequestBody(String contentPrompt) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", contentPrompt)
        ));
        requestBody.put("maxTokens", 256);
        requestBody.put("temperature", 0.7);
        return requestBody;
    }
    
    private ContentDTO parseResponse(String responseBody) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        
        // JSON 응답에서 필요한 데이터 추출

        
        // JSON 응답에서 필요한 데이터 추출
        String thumbnail = jsonNode.path("data").path("thumbnail").asText();
        String contentTitle = jsonNode.path("data").path("contentTitle").asText();
        String title = jsonNode.path("data").path("title").asText();
        String description =  jsonNode.path("result").path("message").path("content").asText();
        
        return new ContentDTO(200, "SUCCESS", "success", thumbnail, contentTitle, title, description);
    }
}