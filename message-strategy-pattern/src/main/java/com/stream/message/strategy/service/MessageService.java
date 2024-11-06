package com.stream.message.strategy.service;

import com.stream.message.strategy.model.MessageLog;
import com.stream.message.strategy.repository.MessageLogRepository;
import com.stream.message.strategy.strategy.MessageStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final Map<String, MessageStrategy> messageStrategiesMap;
    private final MessageLogRepository messageLogRepository;

    @Autowired
    public MessageService(List<MessageStrategy> messageStrategyList,MessageLogRepository messageLogRepository) {
        this.messageStrategiesMap = getMessageStrategyMap(messageStrategyList);
        this.messageLogRepository = messageLogRepository;
    }
/***
 *
 * In Spring Boot, you can inject a List of components directly into a method parameter, rather than using a constructor. Spring automatically detects and injects all beans of a specified type when using List<T> in a method. This technique is useful when you need a group of components only within the scope of a specific method.
 *
 * Here’s an example of how to create multiple @Component beans, store them in a List, and inject them directly into a method.
 *
 * Step-by-Step Example
 * Let’s walk through an example similar to a payment system, where we have multiple implementations of a common interface, and we want to process each one in a service method.
 *
 * Define a common interface.
 * Create multiple implementations of the interface as components.
 * Inject the List of components directly into a method.
 *
 * */
    public Map<String, MessageStrategy> getMessageStrategyMap(List<MessageStrategy> messageStrategyList) {

        return messageStrategyList.stream().collect(Collectors.toMap(processor -> processor.getClass().getSimpleName(), Function.identity()));
    }

    @Transactional
    public void sendMessage(String method, String recipient, String message) {
        MessageStrategy strategy = messageStrategiesMap.get(method.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown message method: " + method);
        }
        strategy.sendMessage(recipient, message);

        logMessage(method, recipient, message, "Success");
    }

    private void logMessage(String method, String recipient, String message, String status) {
        MessageLog log = new MessageLog();
        log.setMethod(method);
        log.setRecipient(recipient);
        log.setMessage(message);
        log.setStatus(status);
        messageLogRepository.save(log);
    }
}