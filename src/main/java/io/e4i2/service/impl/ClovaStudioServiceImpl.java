package io.e4i2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.e4i2.dto.ResponseDTO;
import io.e4i2.dto.UserMessage;
import io.e4i2.entity.Mbti;
import io.e4i2.entity.QUploadFile;
import io.e4i2.entity.UploadFile;
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
        
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = setHeader();
        
        Map<String, Object> requestBody = getRequestBody(userMessage);
        
        try {
            String jsonRequestBody = objectMapper.writeValueAsString(requestBody);
            HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            
            String responseBody = response.getBody();
            
            // 이벤트 스트림에서 JSON 부분 추출
            List<String> jsonMessages = extractJsonMessages(responseBody);
            ResponseDTO.MessageWrapper lastMessageWrapper = getMessageWrapper(jsonMessages);
            
            
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
            
            if (lastMessageWrapper != null) {
                data.setMessages(List.of(lastMessageWrapper));
            } else {
                ResponseDTO.Message message = new ResponseDTO.Message();
                message.setRole("assistant");
                
                ResponseDTO.MessageWrapper messageWrapper = new ResponseDTO.MessageWrapper();
                messageWrapper.setMessage(message);
                
                data.setMessages(List.of(messageWrapper));
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
    
    private ResponseDTO.MessageWrapper getMessageWrapper(List<String> jsonMessages) throws JsonProcessingException {
        ResponseDTO.MessageWrapper lastMessageWrapper = null;
        if (!jsonMessages.isEmpty()) {
            String lastJsonMessage = jsonMessages.get(jsonMessages.size() - 2);
            lastMessageWrapper = objectMapper.readValue(lastJsonMessage, ResponseDTO.MessageWrapper.class);
        }
        return lastMessageWrapper;
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
        result.setStatus(200);
        result.setMessage("SUCCESS");
        result.setCode("success");
        
        ResponseDTO.Message message1 = new ResponseDTO.Message();
        ResponseDTO.Message message2 = new ResponseDTO.Message();
        message1.setContent("대화 시뮬레이션을 시작할게요");
        message1.setRole("assistant");
        message2.setContent("안녕하세요");
        message2.setRole("assistant");
        QUploadFile qUploadFile = QUploadFile.uploadFile;
        
        UploadFile findFileUrl = queryFactory
                .selectFrom(qUploadFile)
                .where(qUploadFile.mbti.eq(mbti))
                .fetchOne();
        
        ResponseDTO.MessageWrapper messageWrapper1 = new ResponseDTO.MessageWrapper();
        ResponseDTO.MessageWrapper messageWrapper2 = new ResponseDTO.MessageWrapper();
        messageWrapper1.setMessage(message1);
        messageWrapper2.setMessage(message2);
        ResponseDTO.MessageData data = new ResponseDTO.MessageData();
        
        
        data.setProfileImageUrl(findFileUrl.getFileUrl() == null ? "" : findFileUrl.getFileUrl());
        data.setMessages(List.of(messageWrapper1, messageWrapper2));
        
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(data);
        responseDTO.setResult(result);
        
        return responseDTO;
    }
    
    private static Map<String, Object> getRequestBody(UserMessage userMessage) {
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

