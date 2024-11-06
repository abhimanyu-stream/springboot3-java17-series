package com.stream.message.strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StrategyPatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrategyPatternApplication.class, args);
	}

}
/***
 *
 * In this example, we’ll use the Strategy Pattern to implement a message-sending system with multiple strategies for sending messages through different channels like Email, SMS, and WhatsApp. Each message strategy is encapsulated in its own class, making it easy to add, modify, or replace strategies without changing the core application logic.
 *
 * Scenario
 * Imagine a notification system in a Spring Boot application that needs to send messages through various channels (Email, SMS, and WhatsApp). By using the Strategy Pattern, we can encapsulate each messaging method in its own class and dynamically select which messaging strategy to use based on user preference or specific criteria.
 *
 * Step 1: Define the Message Strategy Interface
 * Define a MessageStrategy interface with a method to send a message.
 *

 * public interface MessageStrategy {
 *     void sendMessage(String recipient, String message);
 * }
 * Step 2: Implement Concrete Message Strategies
 * Each messaging method (Email, SMS, WhatsApp) implements the MessageStrategy interface to provide its unique implementation of sendMessage.
 *
 * Email Message Strategy

 * import org.springframework.stereotype.Component;
 *
 * @Component("email")
 * public class EmailMessageStrategy implements MessageStrategy {
 *     @Override
 *     public void sendMessage(String recipient, String message) {
 *         System.out.println("Sending Email to " + recipient + " with message: " + message);
 *         // Implement actual email-sending logic here (e.g., using JavaMailSender)
 *     }
 * }
 * SMS Message Strategy

 * import org.springframework.stereotype.Component;
 *
 * @Component("sms")
 * public class SmsMessageStrategy implements MessageStrategy {
 *     @Override
 *     public void sendMessage(String recipient, String message) {
 *         System.out.println("Sending SMS to " + recipient + " with message: " + message);
 *         // Implement actual SMS-sending logic here (e.g., using Twilio API)
 *     }
 * }
 * WhatsApp Message Strategy

 * import org.springframework.stereotype.Component;
 *
 * @Component("whatsapp")
 * public class WhatsAppMessageStrategy implements MessageStrategy {
 *     @Override
 *     public void sendMessage(String recipient, String message) {
 *         System.out.println("Sending WhatsApp message to " + recipient + " with message: " + message);
 *         // Implement actual WhatsApp-sending logic here (e.g., using WhatsApp API)
 *     }
 * }
 * Step 3: Create a Message Service (Strategy Context)
 * Define a MessageService class that acts as the context for selecting and executing the appropriate message strategy.
 *

 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Service;
 *
 * import java.util.Map;
 *
 * @Service
 * public class MessageService {
 *
 *     private final Map<String, MessageStrategy> messageStrategies;
 *
 *     @Autowired
 *     public MessageService(Map<String, MessageStrategy> messageStrategies) {
 *         this.messageStrategies = messageStrategies;
 *     }
 *
 *     public void sendMessage(String method, String recipient, String message) {
 *         MessageStrategy strategy = messageStrategies.get(method.toLowerCase());
 *         if (strategy == null) {
 *             throw new IllegalArgumentException("Unknown message method: " + method);
 *         }
 *         strategy.sendMessage(recipient, message);
 *     }
 * }
 * In this code, Spring injects all MessageStrategy beans into a Map, allowing MessageService to dynamically select and execute the appropriate strategy based on the method name (e.g., "email", "sms", "whatsapp").
 *
 * Step 4: Define an Entity for Logging Messages in MySQL (Optional)
 * Create a MessageLog entity to store details of each message sent in a MySQL database.
 *

 * import jakarta.persistence.*;
 * import lombok.Data;
 *
 * @Data
 * @Entity
 * @Table(name = "message_log")
 * public class MessageLog {
 *
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.IDENTITY)
 *     private Long id;
 *
 *     private String method;
 *     private String recipient;
 *     private String message;
 *     private String status;
 * }
 * Step 5: Create a Repository for the MessageLog Entity
 * Define a repository to save MessageLog entries in the database.
 *

 * import org.springframework.data.jpa.repository.JpaRepository;
 * import org.springframework.stereotype.Repository;
 *
 * @Repository
 * public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {
 * }
 * Step 6: Extend MessageService to Log Messages
 * Add logging capabilities to MessageService by saving each message to the database after it is sent.
 *

 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Service;
 * import org.springframework.transaction.annotation.Transactional;
 *
 * import java.util.Map;
 *
 * @Service
 * public class MessageService {
 *
 *     private final Map<String, MessageStrategy> messageStrategies;
 *     private final MessageLogRepository messageLogRepository;
 *
 *     @Autowired
 *     public MessageService(Map<String, MessageStrategy> messageStrategies, MessageLogRepository messageLogRepository) {
 *         this.messageStrategies = messageStrategies;
 *         this.messageLogRepository = messageLogRepository;
 *     }
 *
 *     @Transactional
 *     public void sendMessage(String method, String recipient, String message) {
 *         MessageStrategy strategy = messageStrategies.get(method.toLowerCase());
 *         if (strategy == null) {
 *             throw new IllegalArgumentException("Unknown message method: " + method);
 *         }
 *         strategy.sendMessage(recipient, message);
 *
 *         logMessage(method, recipient, message, "Success");
 *     }
 *
 *     private void logMessage(String method, String recipient, String message, String status) {
 *         MessageLog log = new MessageLog();
 *         log.setMethod(method);
 *         log.setRecipient(recipient);
 *         log.setMessage(message);
 *         log.setStatus(status);
 *         messageLogRepository.save(log);
 *     }
 * }
 * Step 7: Create a REST Controller for Sending Messages
 * Create a REST controller with an endpoint that allows clients to send messages through different channels.
 *

 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 *
 * @RestController
 * @RequestMapping("/api/messages")
 * public class MessageController {
 *
 *     private final MessageService messageService;
 *
 *     @Autowired
 *     public MessageController(MessageService messageService) {
 *         this.messageService = messageService;
 *     }
 *
 *     @PostMapping("/send")
 *     public String sendMessage(@RequestParam String method, @RequestParam String recipient, @RequestParam String message) {
 *         messageService.sendMessage(method, recipient, message);
 *         return "Message sent via " + method;
 *     }
 * }
 * Step 8: Configure MySQL in application.properties
 * Add your MySQL database connection details in src/main/resources/application.properties.
 *
 * properties
spring.application.name=message-strategy-pattern

server.port= 8999


# MySQL DB Data Source Configuration  JDBC start

spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost/message-strategy-pattern?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true
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


 * Explanation of the Strategy Pattern Implementation
 * Message Strategies:
 *
 * Each messaging method is implemented as a separate class (EmailMessageStrategy, SmsMessageStrategy, WhatsAppMessageStrategy) that follows the MessageStrategy interface. Each strategy encapsulates the logic specific to each messaging service.
 * MessageService (Context):
 *
 * MessageService acts as the context in the Strategy Pattern. It selects and executes the appropriate strategy based on the provided method. Using a Map with Spring’s @Autowired feature allows each strategy to be accessed by name.
 * Database Logging:
 *
 * Each message sent is logged in the MessageLog entity, which is saved to a MySQL database. This allows for auditing or tracking messages.
 * REST Controller:
 *
 * The MessageController provides a REST API endpoint (/api/messages/send) for clients to send messages via different channels by specifying the method (email, sms, or whatsapp), recipient, and message.
 * Example Usage
 * Sending an Email:
 *
 * The client sends a POST request to /api/messages/send?method=email&recipient=jane.doe@example.com&message=Hello%20Jane.
 * MessageService retrieves the EmailMessageStrategy and sends the email. It then logs the message as "Success" in MySQL.
 * Sending an SMS:
 *
 * The client sends a POST request to /api/messages/send?method=sms&recipient=+1234567890&message=Hello%20via%20SMS.
 * MessageService selects the SmsMessageStrategy, sends the SMS, and logs the transaction.
 * Sending a WhatsApp Message:
 *
 * The client sends a POST request to /api/messages/send?method=whatsapp&recipient=+1234567890&message=Hello%20on%20WhatsApp.
 * MessageService retrieves WhatsAppMessageStrategy, sends the WhatsApp message, and logs the transaction.
 * Benefits of Using the Strategy Pattern in This Example
 * Interchangeability: The Strategy Pattern allows easy switching between messaging methods without changing the business logic. This makes the system flexible and easier to maintain.
 * Extensibility: New messaging methods can be added by simply creating a new MessageStrategy implementation (e.g., Slack or Telegram) without modifying existing code.
 * Single Responsibility: Each strategy focuses only on its specific messaging method, following the Single Responsibility Principle.
 * Separation of Concerns: The MessageService is responsible for selecting and executing the strategy, keeping the core logic clean and manageable.
 * This setup provides a scalable, maintainable solution for handling multiple messaging methods in a Spring Boot application using the Strategy Pattern.
 *
 * */