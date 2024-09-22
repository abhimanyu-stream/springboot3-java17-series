package com.stream.order_query_service.configuration;


import com.stream.order_query_service.exceptions.ProductServiceEventsErrorHandler;
import jakarta.annotation.PostConstruct;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.config.EventProcessingModule;
import org.axonframework.eventhandling.*;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.springboot.autoconfig.EventProcessingAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class AxonConfig {

  private static final Logger logger = LoggerFactory.getLogger(AxonConfig.class);
  // Define the EventProcessingConfigurer without causing cyclic dependencies





}
