package com.stream.completableFuture;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class ExternalApiService {
    @Autowired
    private RestTemplate restTemplate;
    private final String URL_AUTHENTICATION_SERVICE = "http://localhost:AUTHENTICATION_SERVICE:8888";

    public CompletableFuture<String> callExternalApi(String URL_AUTHENTICATION_SERVICE) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return restTemplate.getForObject(URL_AUTHENTICATION_SERVICE, String.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to call external API", e);
            }
        });
    }
}
