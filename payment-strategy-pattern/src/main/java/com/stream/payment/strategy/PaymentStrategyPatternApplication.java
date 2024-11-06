package com.stream.payment.strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentStrategyPatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentStrategyPatternApplication.class, args);
	}

}
/***
 * The Strategy Pattern is ideal for a situation where multiple algorithms (or strategies) can be applied to a specific task. In this example, we’ll use the Strategy Pattern to implement an e-commerce application that can handle different payment methods (like PayPal, Stripe, and Square), making it easy to switch between them without modifying the core logic of the application.
 *
 * Scenario
 * Imagine an e-commerce application that needs to process payments using different payment providers. Each provider has a different way of handling payments. Using the Strategy Pattern, we can encapsulate each payment strategy in its own class, making it easy to add or change payment methods without changing the application’s main logic.
 *
 * Step 1: Define the Payment Strategy Interface
 * Define a PaymentStrategy interface to represent the different payment methods.
 *

 * public interface PaymentStrategy {
 *     void pay(double amount);
 * }
 * Step 2: Implement Concrete Payment Strategies
 * Each payment method (like PayPal, Stripe, and Square) implements the PaymentStrategy interface with its specific way of processing payments.
 *
 * PayPal Payment Strategy

 * import org.springframework.stereotype.Component;
 *
 * @Component("paypal")
 * public class PayPalPaymentStrategy implements PaymentStrategy {
 *     @Override
 *     public void pay(double amount) {
 *         System.out.println("Processing PayPal payment of $" + amount);
 *         // Implement PayPal-specific payment logic here
 *     }
 * }
 * Stripe Payment Strategy

 * import org.springframework.stereotype.Component;
 *
 * @Component("stripe")
 * public class StripePaymentStrategy implements PaymentStrategy {
 *     @Override
 *     public void pay(double amount) {
 *         System.out.println("Processing Stripe payment of $" + amount);
 *         // Implement Stripe-specific payment logic here
 *     }
 * }
 * Square Payment Strategy

 * import org.springframework.stereotype.Component;
 *
 * @Component("square")
 * public class SquarePaymentStrategy implements PaymentStrategy {
 *     @Override
 *     public void pay(double amount) {
 *         System.out.println("Processing Square payment of $" + amount);
 *         // Implement Square-specific payment logic here
 *     }
 * }
 * Step 3: Create a Payment Context
 * The PaymentService (or payment context) will use the PaymentStrategy interface to execute the appropriate payment method based on the user’s choice.
 *

 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Service;
 *
 * import java.util.Map;
 *
 * @Service
 * public class PaymentService {
 *
 *     private final Map<String, PaymentStrategy> paymentStrategies;
 *
 *     @Autowired
 *     public PaymentService(Map<String, PaymentStrategy> paymentStrategies) {
 *         this.paymentStrategies = paymentStrategies;
 *     }
 *
 *     public void processPayment(String paymentMethod, double amount) {
 *         PaymentStrategy strategy = paymentStrategies.get(paymentMethod.toLowerCase());
 *         if (strategy == null) {
 *             throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
 *         }
 *         strategy.pay(amount);
 *     }
 * }
 * In this code, we use Spring’s @Autowired to inject a map of all PaymentStrategy beans, making it easy to retrieve the appropriate strategy based on the paymentMethod key. Spring automatically injects each component using the bean name, making each payment method available by name in the map.
 *
 * Step 4: Define an Entity to Log Payments in MySQL (Optional)
 * For a realistic setup, we’ll log each processed payment to a MySQL database.
 *

 * import jakarta.persistence.*;
 * import lombok.Data;
 *
 * @Data
 * @Entity
 * @Table(name = "payment_log")
 * public class PaymentLog {
 *
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     private Long id;
 *
 *     private String paymentMethod;
 *     private double amount;
 *     private String status;
 * }
 * Step 5: Create a Repository for the PaymentLog Entity
 * Define a repository for PaymentLog so we can save each payment transaction.
 *

 * import org.springframework.data.jpa.repository.JpaRepository;
 * import org.springframework.stereotype.Repository;
 *
 * @Repository
 * public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {
 * }
 * Step 6: Extend PaymentService to Log Payments
 * In the PaymentService, log each transaction to MySQL by adding a new method to save the PaymentLog.
 *

 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Service;
 * import org.springframework.transaction.annotation.Transactional;
 *
 * import java.util.Map;
 *
 * @Service
 * public class PaymentService {
 *
 *     private final Map<String, PaymentStrategy> paymentStrategies;
 *     private final PaymentLogRepository paymentLogRepository;
 *
 *     @Autowired
 *     public PaymentService(Map<String, PaymentStrategy> paymentStrategies, PaymentLogRepository paymentLogRepository) {
 *         this.paymentStrategies = paymentStrategies;
 *         this.paymentLogRepository = paymentLogRepository;
 *     }
 *
 *     @Transactional
 *     public void processPayment(String paymentMethod, double amount) {
 *         PaymentStrategy strategy = paymentStrategies.get(paymentMethod.toLowerCase());
 *         if (strategy == null) {
 *             throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
 *         }
 *         strategy.pay(amount);
 *
 *         logPayment(paymentMethod, amount, "Success");
 *     }
 *
 *     private void logPayment(String paymentMethod, double amount, String status) {
 *         PaymentLog log = new PaymentLog();
 *         log.setPaymentMethod(paymentMethod);
 *         log.setAmount(amount);
 *         log.setStatus(status);
 *         paymentLogRepository.save(log);
 *     }
 * }
 * Step 7: Create a REST Controller for the Payment Endpoint
 * Create a REST API to trigger payments through different strategies.
 *

 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 *
 * @RestController
 * @RequestMapping("/api/payments")
 * public class PaymentController {
 *
 *     private final PaymentService paymentService;
 *
 *     @Autowired
 *     public PaymentController(PaymentService paymentService) {
 *         this.paymentService = paymentService;
 *     }
 *
 *     @PostMapping("/process")
 *     public String processPayment(@RequestParam String paymentMethod, @RequestParam double amount) {
 *         paymentService.processPayment(paymentMethod, amount);
 *         return "Payment processed through " + paymentMethod;
 *     }
 * }
 * Step 8: Configure MySQL Database in application.properties
 * Add your MySQL database configuration in src/main/resources/application.properties.
 *
 * properties

spring.application.name=strategy-pattern
server.port= 8999


# MySQL DB Data Source Configuration  JDBC start

spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost/payment-strategy-pattern?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


spring.jpa.properties.hibernate.ddl-auto=update
spring.jpa.properties.generate-ddl=true
#below properties will automatically create and updates database schema
spring.jpa.properties.hibernate.hbm2ddl.auto=update
spring.jpa.properties.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.connection.pool_size=5
spring.jpa.properties.hibernate.current_session_context_class=thread
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# MySQL DB Data Source Configuration  JDBC end
#git pull --rebase origin main
#git push origin main

 * Explanation of Strategy Pattern Implementation
 * Payment Strategies:
 *
 * Each payment method is implemented as a separate class (PayPalPaymentStrategy, StripePaymentStrategy, SquarePaymentStrategy) that follows the PaymentStrategy interface. This allows each payment method to have its specific logic, making it easy to add new payment strategies without modifying existing code.
 * Payment Context (PaymentService):
 *
 * PaymentService acts as the context for the Strategy Pattern. It selects the appropriate payment strategy based on the paymentMethod parameter and executes it. The strategy beans are injected into a Map by Spring, enabling quick retrieval of each payment strategy by name.
 * Database Logging:
 *
 * Every payment transaction is logged to MySQL using PaymentLog, providing a record of each payment for auditing or reporting purposes.
 * REST Controller:
 *
 * The PaymentController provides a simple REST API endpoint to trigger payments by specifying the paymentMethod and amount parameters. This endpoint calls the PaymentService, which selects the appropriate payment strategy and logs the transaction.
 * Example Scenario
 * Processing a PayPal Payment:
 *
 * The client sends a POST request to /api/payments/process?paymentMethod=paypal&amount=100.0.
 * PaymentService retrieves PayPalPaymentStrategy from the map, processes the payment, and logs the transaction as "Success" in MySQL.
 * Processing a Stripe Payment:
 *
 * The client sends a POST request to /api/payments/process?paymentMethod=stripe&amount=200.0.
 * PaymentService selects StripePaymentStrategy, processes the payment, and logs the transaction.
 * Processing a Square Payment:
 *
 * The client sends a POST request to /api/payments/process?paymentMethod=square&amount=300.0.
 * PaymentService retrieves SquarePaymentStrategy, processes the payment, and logs the transaction.
 * Benefits of Using the Strategy Pattern in this Example
 * Interchangeability: The Strategy Pattern allows for easy switching between payment methods by simply selecting a different strategy in PaymentService.
 * Extensibility: New payment methods can be added without modifying existing code. Adding a GooglePayPaymentStrategy would only require creating a new class and updating PaymentService to include the new strategy.
 * Single Responsibility: Each payment strategy is responsible for its specific payment logic, adhering to the Single Responsibility Principle.
 * Clean Code: The main business logic in PaymentService is clean and easy to understand, as it simply delegates the payment processing to the appropriate strategy.
 * This example demonstrates a flexible, maintainable way to handle different payment methods in a Spring Boot application by applying the Strategy Pattern.
 *
 *
 * */