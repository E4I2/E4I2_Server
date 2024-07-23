package io.e4i2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.e4i2.service.ClovaStudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ClovaStudioServiceImpl implements ClovaStudioService {
    
    @Value("${clovastudio.api.url}")
    private String apiUrl;
    
    @Value("${clovastudio.api.key}")
    private String apiKey;
    
    @Value("${clovastudio.api.request.id}}")
    private String requestId;
    
    
    private final ObjectMapper objectMapper;
    
    public String getResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", "4vL7buWu31HnrCQDxzJzBffKtYbp3stF3pstGgmX");
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "text/event-stream");
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "사용자의 메시지에 따라 응답을 생성합니다."),
                Map.of("role", "user", "content", userMessage)
        ));
        requestBody.put("topP", 0.8);
        requestBody.put("topK", 0);
        requestBody.put("maxTokens", 256);
        requestBody.put("temperature", 0.5);
        requestBody.put("repeatPenalty", 5.0);
        requestBody.put("includeAiFilters", true);
        requestBody.put("seed", 0);
        
        try {
            String jsonRequestBody = objectMapper.writeValueAsString(requestBody);
            
            HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            
            return response.getBody();
        } catch (JsonProcessingException e) {
            return "Error processing request: " + e.getMessage();
        } catch (HttpClientErrorException e) {
            // 클라이언트 오류
            return "Client error: " + e.getStatusCode() + " " + e.getResponseBodyAsString();
        } catch (HttpServerErrorException e) {
            // 서버 오류
            return "Server error: " + e.getStatusCode() + " " + e.getResponseBodyAsString();
        } catch (Exception e) {
            // 기타 예외
            return "Unexpected error: " + e.getMessage();
        }
    }
}
