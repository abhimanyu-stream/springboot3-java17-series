package com.stream.order_query_service;


import com.stream.order_query_service.exceptions.OrderServiceEventsExceptionHandler;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class OrderQueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderQueryServiceApplication.class, args);
	}


	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("order-query",
				configuration -> new OrderServiceEventsExceptionHandler());
	}
}
