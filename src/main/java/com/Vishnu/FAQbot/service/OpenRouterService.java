package com.Vishnu.FAQbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OpenRouterService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.api.url}")
    private String apiUrl;

    public String getChatResponse(String userQuestion) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("HTTP-Referer", "http://localhost:8080"); // your local or prod URL
        headers.set("X-Title", "FAQBot Vishnu");

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", userQuestion);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "openai/gpt-3.5-turbo"); // or try "mistralai/mistral-7b-instruct"
        requestBody.put("messages", Collections.singletonList(message));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
