package io.e4i2.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.e4i2.dto.ResponseDTO;
import io.e4i2.dto.Data;
import io.e4i2.dto.Message;
import io.e4i2.dto.Result;
import io.e4i2.service.ClovaStudioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClovaStudioServiceImpl implements ClovaStudioService {
    
    @Value("${clovastudio.api.url}")
    private String apiUrl;
    
    @Value("${clovastudio.api.key}")
    private String apiKey;
    
    @Value("${clovastudio.api.request.id}}")
    private String requestId;
    
    @Value("${apigw.api.key}")
    private String apigwApiKey;
    
    
    private final ObjectMapper objectMapper;
    
    
    public ResponseDTO getResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apigwApiKey);
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
            log.debug("Request Body: {}", jsonRequestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            
            String responseBody = response.getBody();
            log.debug("Response Body: {}", responseBody);
            
            // 이벤트 스트림에서 JSON 부분 추출
            List<String> jsonMessages = extractJsonMessages(responseBody);
            List<Message> aiMessages = new ArrayList<>();
            
            for (String jsonMessage : jsonMessages) {
                JsonNode rootNode = objectMapper.readTree(jsonMessage);
                JsonNode messageNode = rootNode.path("message");
                if (!messageNode.isMissingNode()) {
                    String messageText = messageNode.path("content").asText();
                    String[] splitMessages = messageText.split("\\r?\\n\\r?\\n"); // 두 줄바꿈 문자를 기준으로 분리
                    for (String splitMessage : splitMessages) {
                        if (!splitMessage.trim().isEmpty()) {
                            Message message = new Message();
                            message.setText(splitMessage.trim());
                            aiMessages.add(message);
                        }
                    }
                }
            }
            
            // 응답 구성
            Result result = new Result();
            result.setStatus(200);
            result.setMessage("SUCCESS");
            result.setCode("success");
            
            List<Message> responseMessages = new ArrayList<>();
            if (!aiMessages.isEmpty()) {
                responseMessages.addAll(aiMessages);
            } else {
                Message defaultMessage = new Message();
                defaultMessage.setText("AI 응답 메시지가 없습니다.");
                defaultMessage.setImageUrl(null);
                responseMessages.add(defaultMessage);
            }
            
            // 추가 메시지 설정
            
            Data data = new Data();
            data.setProfileImageUrl("https://imageUrl");
            data.setMessages(responseMessages);
            
            ResponseDTO finalResponseDTO = new ResponseDTO();
            finalResponseDTO.setResult(result);
            finalResponseDTO.setData(data);
            
            return finalResponseDTO;
        } catch (HttpClientErrorException e) {
            log.error("Client error", e);
            throw new RuntimeException("Client error: " + e.getStatusCode() + " " + e.getResponseBodyAsString(), e);
        } catch (HttpServerErrorException e) {
            log.error("Server error", e);
            throw new RuntimeException("Server error: " + e.getStatusCode() + " " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
    private List<String> extractJsonMessages(String responseBody) {
        List<String> jsonMessages = new ArrayList<>();
        String[] events = responseBody.split("\n\n");
        for (String event : events) {
            if (event.contains("data:")) {
                String jsonMessage = event.substring(event.indexOf("{"));
                jsonMessages.add(jsonMessage);
            }
        }
        return jsonMessages;
    }
}