package com.lazer.backend.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class BotRest {
    public static void send(String message) {
        try{
            String url = "http://telegram-bot-service:41401/";
            Map<String, String> requestBody = Map.of("message", message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            // Отправка запроса
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(url, request, String.class);
        }
        catch (RestClientException e) {
            System.out.println("can`t send data to bot");
        }
    }
}
