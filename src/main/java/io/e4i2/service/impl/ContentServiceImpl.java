package io.e4i2.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.e4i2.dto.ContentDTO;
import io.e4i2.entity.Content;
import io.e4i2.entity.ContentPrompt;
import io.e4i2.entity.QContentPrompt;
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

import java.util.ArrayList;
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
        return new ContentDTO(
                200,
                "SUCCESS",
                "success",
                content.getThumbnail(),
                content.getContentTitle(),
                content.getTitle(),
                content.getDescription(),
                content.getContentId() // 추가된 필드
        );
    }
    
    @Override
    public ContentDTO getContentPrompt(ContentRequest contentRequest) {
        ContentPrompt findByContentPrompt = contentPromptRepository.findById(contentRequest.getContentId())
                .orElseThrow(() -> new RuntimeException("ContentPrompt not found"));
        String contentPrompt = findByContentPrompt.getContentPrompt();
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeader();
        Map<String, Object> requestBody = getRequestBody(contentRequest, contentPrompt);
        
        try {
            String jsonRequestBody = objectMapper.writeValueAsString(requestBody);
            log.info("Request Body: {}", jsonRequestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();
            
            return parseResponse(responseBody,contentRequest);
            
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
    
    private Map<String, Object> getRequestBody(ContentRequest contentRequest, String contentPrompt) {
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();
        
        String systemPrompt = String.format(
                "You information: MBTI: %s, Name: %s, Age: %s, Sex: %s, Relation: %s, Interests: %s. Give a detailed response based on this information.",
                contentRequest.getMbti(), contentRequest.getName(), contentRequest.getAge(), contentRequest.getSex(),
                contentRequest.getRelation(), String.join(", ", contentRequest.getInterest())
        );
        String s = systemPrompt + " " + contentPrompt;
        messages.add(Map.of("role", "system", "content", s));
        //messages.add(Map.of("role", "user", "content", contentPrompt));
        
        requestBody.put("messages", messages);
        requestBody.put("maxTokens", 256);
        requestBody.put("temperature", 0.7);
        requestBody.put("topP", 0.8);
        requestBody.put("topK", 0);
        requestBody.put("repeatPenalty", 5.0);
        requestBody.put("includeAiFilters", true);
        requestBody.put("seed", 0);
        
        return requestBody;
    }
    private ContentDTO parseResponse(String responseBody, ContentRequest contentRequest) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        
        // JSON 응답에서 필요한 데이터 추출
        
        ContentPrompt contentPrompt = contentPromptRepository.findById(contentRequest.getContentId()).get();
        
        String description = jsonNode.path("result").path("message").path("content").asText();
        String titlePrefix = "제목 :";
        String title = "";
        String contentWithoutTitle = description; // 내용에서 제목 부분을 제거한 텍스트
        
        if (description.startsWith(titlePrefix)) {
            int endOfTitleIndex = description.indexOf("\n", titlePrefix.length());
            if (endOfTitleIndex != -1) {
                title = description.substring(titlePrefix.length(), endOfTitleIndex).trim();
                contentWithoutTitle = description.substring(endOfTitleIndex + 1).trim(); // 제목 이후 부분만 남김
            } else {
                title = description.substring(titlePrefix.length()).trim();
                contentWithoutTitle = ""; // 제목만 있고 내용이 없으면 빈 문자열로 설정
            }
        }
        
        return new ContentDTO(200, "SUCCESS", "success", contentPrompt.getThumbnail(), contentPrompt.getContentTitle(), title.isEmpty() ? "방금까지 나눈 대화로 온도를 측정했어요!" : title, contentWithoutTitle, contentRequest.getContentId());
    }
}