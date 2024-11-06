package com.stream.completableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class Controller {

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/call-api")
    public CompletableFuture<String> callApi(@RequestParam String url) {
        return externalApiService.callExternalApi(url);
    }
}
