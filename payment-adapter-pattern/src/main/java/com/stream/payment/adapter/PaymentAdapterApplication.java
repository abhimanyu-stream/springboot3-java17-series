package com.stream.payment.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentAdapterApplication.class, args);
	}

}
/***
 * Using the Adapter Pattern with Spring Boot 3, MySQL, and various payment methods (like PayPal, Stripe, and Square) provides a flexible way to integrate different payment services under a unified interface. In this example, we’ll demonstrate how an e-commerce application can handle multiple payment providers using adapters, enabling each provider to be interchangeable without modifying the core logic.
 *
 * Scenario
 * Imagine an e-commerce system that needs to process payments through multiple third-party payment gateways. Each payment provider has a unique API. Using the Adapter Pattern, we’ll implement a way to interact with each provider in a consistent manner, allowing the application to easily switch between them.
 *
 * Step 1: Define the Target Interface
 * The target interface PaymentProcessor provides a unified method processPayment that will be implemented by each adapter.
 *

 * public interface PaymentProcessor {
 *     void processPayment(double amount);
 * }
 * Step 2: Define the Adaptees (Existing Payment Gateways)
 * These classes represent the third-party payment services, each with its own API. We cannot modify these classes, so we will use adapters to make them compatible with our PaymentProcessor interface.
 *
 * PayPal Payment

 * public class PayPalPayment {
 *     public void payWithPayPal(double amount) {
 *         System.out.println("Processing PayPal payment of $" + amount);
 *     }
 * }
 * Stripe Payment

 * public class StripePayment {
 *     public void chargeWithStripe(double amount) {
 *         System.out.println("Processing Stripe payment of $" + amount);
 *     }
 * }
 * Square Payment

 * public class SquarePayment {
 *     public void processSquarePayment(double amount) {
 *         System.out.println("Processing Square payment of $" + amount);
 *     }
 * }
 * Step 3: Implement Adapters for Each Payment Method
 * Each adapter implements the PaymentProcessor interface and adapts the unique API of its respective payment provider.
 *
 * PayPal Adapter

 * import org.springframework.stereotype.Service;
 *
 * @Service
 * public class PayPalAdapter implements PaymentProcessor {
 *     private final PayPalPayment payPalPayment;
 *
 *     public PayPalAdapter() {
 *         this.payPalPayment = new PayPalPayment();
 *     }
 *
 *     @Override
 *     public void processPayment(double amount) {
 *         payPalPayment.payWithPayPal(amount); // Adapts PayPal's API to processPayment
 *     }
 * }
 * Stripe Adapter

 * import org.springframework.stereotype.Service;
 *
 * @Service
 * public class StripeAdapter implements PaymentProcessor {
 *     private final StripePayment stripePayment;
 *
 *     public StripeAdapter() {
 *         this.stripePayment = new StripePayment();
 *     }
 *
 *     @Override
 *     public void processPayment(double amount) {
 *         stripePayment.chargeWithStripe(amount); // Adapts Stripe's API to processPayment
 *     }
 * }
 * Square Adapter

 * import org.springframework.stereotype.Service;
 *
 * @Service
 * public class SquareAdapter implements PaymentProcessor {
 *     private final SquarePayment squarePayment;
 *
 *     public SquareAdapter() {
 *         this.squarePayment = new SquarePayment();
 *     }
 *
 *     @Override
 *     public void processPayment(double amount) {
 *         squarePayment.processSquarePayment(amount); // Adapts Square's API to processPayment
 *     }
 * }
 * Step 4: Define an Entity to Log Payments in MySQL
 * We’ll log each processed payment in a MySQL database, so we need a PaymentLog entity.
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
 * Step 5: Create a Repository to Save Payment Logs

 * import org.springframework.data.jpa.repository.JpaRepository;
 * import org.springframework.stereotype.Repository;
 *
 * @Repository
 * public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {
 * }
 * Step 6: Create a PaymentServiceManager to Use Adapters and Log Payments
 * The PaymentServiceManager uses the adapters to process payments based on the chosen payment provider and logs the transaction.
 *

 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Service;
 * import org.springframework.transaction.annotation.Transactional;
 *
 * @Service
 * public class PaymentServiceManager {
 *
 *     private final PaymentLogRepository paymentLogRepository;
 *     private final PayPalAdapter payPalAdapter;
 *     private final StripeAdapter stripeAdapter;
 *     private final SquareAdapter squareAdapter;
 *
 *     @Autowired
 *     public PaymentServiceManager(PaymentLogRepository paymentLogRepository,
 *                                  PayPalAdapter payPalAdapter,
 *                                  StripeAdapter stripeAdapter,
 *                                  SquareAdapter squareAdapter) {
 *         this.paymentLogRepository = paymentLogRepository;
 *         this.payPalAdapter = payPalAdapter;
 *         this.stripeAdapter = stripeAdapter;
 *         this.squareAdapter = squareAdapter;
 *     }
 *
 *     @Transactional
 *     public void processPayment(String paymentMethod, double amount) {
 *         PaymentProcessor paymentService;
 *
 *         switch (paymentMethod.toLowerCase()) {
 *             case "paypal" -> paymentService = payPalAdapter;
 *             case "stripe" -> paymentService = stripeAdapter;
 *             case "square" -> paymentService = squareAdapter;
 *             default -> throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
 *         }
 *
 *         paymentService.processPayment(amount);
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
 * Step 7: Create a REST Controller to Trigger Payments
 * The PaymentController provides an endpoint to process payments through the selected method.
 *

 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 *
 * @RestController
 * @RequestMapping("/api/payments")
 * public class PaymentController {
 *
 *     private final PaymentServiceManager paymentServiceManager;
 *
 *     @Autowired
 *     public PaymentController(PaymentServiceManager paymentServiceManager) {
 *         this.paymentServiceManager = paymentServiceManager;
 *     }
 *
 *     @PostMapping("/process")
 *     public String processPayment(@RequestParam String paymentMethod, @RequestParam double amount) {
 *         paymentServiceManager.processPayment(paymentMethod, amount);
 *         return "Payment processed through " + paymentMethod;
 *     }
 * }
 * Step 8: Configure MySQL in application.properties
spring.application.name=payment-adapter
server.port= 8999


# MySQL DB Data Source Configuration  JDBC start

spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost/payment-adapter?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true
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
 *
 *
 *
 *
 * Explanation of Adapter Pattern Implementation
 * Adapters for Payment Methods:
 *
 * Each adapter (PayPalAdapter, StripeAdapter, SquareAdapter) implements the PaymentProcessor interface, making their respective payment APIs compatible with processPayment.
 * PaymentServiceManager Flexibility:
 *
 * The PaymentServiceManager can handle different payment providers by selecting the appropriate adapter based on the paymentMethod parameter. This allows the PaymentServiceManager to stay independent of specific payment APIs.
 * MySQL Logging:
 *
 * Each payment transaction is logged in the payment_log table with relevant details (payment method, amount, status), which supports easy tracking of all transactions.
 * Example Scenario
 * Processing a PayPal Payment:
 *
 * Client sends a POST request to /api/payments/process?paymentMethod=paypal&amount=100.0.
 * The PaymentServiceManager selects PayPalAdapter, processes the payment, and logs the transaction.
 * Processing a Stripe Payment:
 *
 * Client sends a POST request to /api/payments/process?paymentMethod=stripe&amount=200.0.
 * The PaymentServiceManager selects StripeAdapter, processes the payment, and logs the transaction.
 * Processing a Square Payment:
 *
 * Client sends a POST request to /api/payments/process?paymentMethod=square&amount=300.0.
 * The PaymentServiceManager selects SquareAdapter, processes the payment, and logs the transaction.
 * Benefits of Using the Adapter Pattern in this Example
 * Unified Interface: All payment services adhere to the same PaymentProcessor interface, making the PaymentServiceManager flexible and easy to extend with new payment methods.
 * Decoupling: The application logic in the client (PaymentController) and service (PaymentServiceManager) is decoupled from the specific implementations of the payment services.
 * Extensibility: Adding a new payment provider, like GooglePay, would require only creating an adapter and updating PaymentServiceManager to support it.
 * This example demonstrates a scalable, flexible way to integrate various payment services into a Spring Boot application, enabling seamless addition of new payment providers.
 *
 * */