package com.stream.payment.strategy.service;

import com.stream.payment.strategy.model.PaymentLog;
import com.stream.payment.strategy.repository.PaymentLogRepository;
import com.stream.payment.strategy.strategy.PaymentStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final Map<String, PaymentStrategy> paymentStrategies;
    private final PaymentLogRepository paymentLogRepository;

    @Autowired
    public PaymentService(List<PaymentStrategy> messageStrategyList, PaymentLogRepository paymentLogRepository) {
        this.paymentStrategies = getPaymentStrategyMap(messageStrategyList);
        this.paymentLogRepository = paymentLogRepository;
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
    public Map<String, PaymentStrategy> getPaymentStrategyMap(List<PaymentStrategy> messageStrategyList) {

        return messageStrategyList.stream().collect(Collectors.toMap(processor -> processor.getClass().getSimpleName(), Function.identity()));
    }

    @Transactional
    public void processPayment(String paymentMethod, double amount) {
        PaymentStrategy strategy = paymentStrategies.get(paymentMethod.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
        }
        strategy.pay(amount);

        logPayment(paymentMethod, amount, "Success");
    }

    private void logPayment(String paymentMethod, double amount, String status) {
        PaymentLog log = new PaymentLog();
        log.setPaymentMethod(paymentMethod);
        log.setAmount(amount);
        log.setStatus(status);
        paymentLogRepository.save(log);
    }
}