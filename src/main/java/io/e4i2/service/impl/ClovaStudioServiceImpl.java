package io.e4i2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.e4i2.dto.ResponseDTO;
import io.e4i2.dto.UserMessage;
import io.e4i2.entity.*;
import io.e4i2.service.ClovaStudioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    
    private final JPAQueryFactory queryFactory;
    
    public ResponseDTO getResponse(UserMessage userMessage) {
        if (!StringUtils.hasText(userMessage.getMessage())) {
            return basicMessage(userMessage.getMbti());
        }
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = setHeader();
            Map<String, Object> requestBody = getRequestBody(userMessage);
            String jsonRequestBody = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();
            
            // 이벤트 스트림에서 JSON 부분 추출
            List<String> jsonMessages = extractJsonMessages(responseBody);
            ResponseDTO.Message lastMessage = getMessage(jsonMessages);
            
            // 응답 구성
            ResponseDTO.Result result = new ResponseDTO.Result();
            result.setStatus(200);
            result.setMessage("SUCCESS");
            result.setCode("success");
            
            ResponseDTO.MessageData data = new ResponseDTO.MessageData();
            QUploadFile qUploadFile = QUploadFile.uploadFile;
            UploadFile findUploadUrl = queryFactory
                    .selectFrom(qUploadFile)
                    .where(qUploadFile.mbti.eq(userMessage.getMbti()))
                    .fetchOne();
            data.setProfileImageUrl(findUploadUrl.getFileUrl() == null ? "" : findUploadUrl.getFileUrl());
            
            if (lastMessage != null) {
                data.setMessages(List.of(lastMessage));
            } else {
                ResponseDTO.Message message = new ResponseDTO.Message();
                message.setText("assistant");
                
                data.setMessages(List.of(message));
            }
            
            ResponseDTO finalResponseDTO = new ResponseDTO();
            finalResponseDTO.setData(data);
            finalResponseDTO.setResult(result);
            
            return finalResponseDTO;
        } catch (Exception e) {
            log.error("Unexpected error", e);
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }
    
    private ResponseDTO.Message getMessage(List<String> jsonMessages) throws JsonProcessingException {
        if (!jsonMessages.isEmpty()) {
            String lastJsonMessage = jsonMessages.get(jsonMessages.size() - 2); // 마지막 JSON 메시지 가져오기
            JsonNode jsonNode = objectMapper.readTree(lastJsonMessage);
            JsonNode messageNode = jsonNode.get("message");
            if (messageNode != null) {
                ResponseDTO.Message message = new ResponseDTO.Message();
                message.setText(messageNode.get("content").asText());
                return message;
            }
        }
        return null; // 메시지가 없는 경우 null 반환
    }
    
    
    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apigwApiKey);
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "text/event-stream");
        return headers;
    }
    
    private ResponseDTO basicMessage(Mbti mbti) {
        ResponseDTO.Result result = new ResponseDTO.Result();
        RestTemplate restTemplate = new RestTemplate();
        
        QPrompt qPrompt = QPrompt.prompt;
        Prompt prompt = queryFactory
                .selectFrom(qPrompt)
                .where(qPrompt.mbti.eq(mbti))
                .fetchOne();
        
        UserMessage userMessage = new UserMessage();
        userMessage.setMessage(prompt.getDefaultPrompt());
        Map<String, Object> requestBody = getRequestBody(userMessage);
        HttpHeaders headers = setHeader();
        String jsonRequestBody = null;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
        
        restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
        result.setStatus(200);
        result.setMessage("SUCCESS");
        result.setCode("success");
        
        ResponseDTO.Message message1 = new ResponseDTO.Message();
        ResponseDTO.Message message2 = new ResponseDTO.Message();
        message1.setText("대화 시뮬레이션을 시작할게요");
        message2.setText("안녕하세요");
        
        QUploadFile qUploadFile = QUploadFile.uploadFile;
        UploadFile findFileUrl = queryFactory
                .selectFrom(qUploadFile)
                .where(qUploadFile.mbti.eq(mbti))
                .fetchOne();
        
        ResponseDTO.MessageData data = new ResponseDTO.MessageData();
        data.setProfileImageUrl(findFileUrl.getFileUrl() == null ? "" : findFileUrl.getFileUrl());
        data.setMessages(List.of(message1, message2));
        
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(data);
        responseDTO.setResult(result);
        
        return responseDTO;
    }
    
    private Map<String, Object> getRequestBody(UserMessage userMessage) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", userMessage.getMessage())
        ));
        requestBody.put("topP", 0.8);
        requestBody.put("topK", 0);
        requestBody.put("maxTokens", 256);
        requestBody.put("temperature", 0.5);
        requestBody.put("repeatPenalty", 5.0);
        requestBody.put("includeAiFilters", true);
        requestBody.put("seed", 0);
        return requestBody;
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