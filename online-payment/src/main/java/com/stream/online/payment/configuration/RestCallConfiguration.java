package com.stream.online.payment.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestCallConfiguration {



    @Bean("OkHttpClient4.2.2")
    public OkHttpClient getHttpClient(){
        return new OkHttpClient();
    }
}
