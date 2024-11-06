package com.stream.message.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageAdapterApplication.class, args);
	}

}
/***
 *
 * The Adapter Pattern is a structural design pattern that allows incompatible interfaces to work together. It's especially useful when you need to integrate new systems with existing code but don’t want to modify the existing code. This pattern provides a bridge between two incompatible interfaces, making it ideal for scenarios like payment processing or messaging where you may need to support multiple vendors or messaging channels.
 *
 * Scenario: Payment Processing and Messaging System Adapter
 * In this example, we’ll design an adapter pattern that supports multiple payment methods (such as PayPal and Stripe) and multiple messaging options (email, SMS, and WhatsApp). The PaymentAdapter will standardize different payment gateways to a common processPayment method, and MessageAdapter will provide a consistent way to send messages via different channels.
 *
 * Step 1: Define the Target Interface
 * Define the interfaces that the client will use to interact with the payment and messaging systems.
 *

 * // Payment interface that all adapters must implement
 * public interface Payment {
 *     void processPayment(double amount);
 * }
 *
 * // Message interface that all adapters must implement
 * public interface Message {
 *     void sendMessage(String recipient, String message);
 * }
 * Step 2: Implement Adaptees (Existing Systems)
 * These classes simulate third-party libraries or APIs that we want to adapt. These systems have different method names or parameters, which we'll adapt to match our interfaces.
 *
 * Payment Adaptees (Existing Payment Systems)

 * // Simulate PayPal payment system
 * public class PayPalPayment {
 *     public void makePayment(double amount) {
 *         System.out.println("Processing PayPal payment of $" + amount);
 *     }
 * }
 *
 * // Simulate Stripe payment system
 * public class StripePayment {
 *     public void charge(double amount) {
 *         System.out.println("Processing Stripe payment of $" + amount);
 *     }
 * }
 * Messaging Adaptees (Existing Messaging Systems)

 * // Simulate Email messaging system
 * public class EmailService {
 *     public void sendEmail(String recipient, String message) {
 *         System.out.println("Sending email to " + recipient + ": " + message);
 *     }
 * }
 *
 * // Simulate SMS messaging system
 * public class SMSService {
 *     public void sendSMS(String phoneNumber, String message) {
 *         System.out.println("Sending SMS to " + phoneNumber + ": " + message);
 *     }
 * }
 *
 * // Simulate WhatsApp messaging system
 * public class WhatsAppService {
 *     public void sendWhatsAppMessage(String phoneNumber, String message) {
 *         System.out.println("Sending WhatsApp message to " + phoneNumber + ": " + message);
 *     }
 * }
 * Step 3: Implement Adapters
 * Create adapter classes that implement the Payment and Message interfaces and adapt the existing classes.
 *
 * Payment Adapters

 * // Adapter for PayPal
 * public class PayPalAdapter implements Payment {
 *     private final PayPalPayment payPalPayment = new PayPalPayment();
 *
 *     @Override
 *     public void processPayment(double amount) {
 *         payPalPayment.makePayment(amount);  // Adapting makePayment to processPayment
 *     }
 * }
 *
 * // Adapter for Stripe
 * public class StripeAdapter implements Payment {
 *     private final StripePayment stripePayment = new StripePayment();
 *
 *     @Override
 *     public void processPayment(double amount) {
 *         stripePayment.charge(amount);  // Adapting charge to processPayment
 *     }
 * }
 * Message Adapters

 * // Adapter for Email
 * public class EmailAdapter implements Message {
 *     private final EmailService emailService = new EmailService();
 *
 *     @Override
 *     public void sendMessage(String recipient, String message) {
 *         emailService.sendEmail(recipient, message);  // Adapting sendEmail to sendMessage
 *     }
 * }
 *
 * // Adapter for SMS
 * public class SMSAdapter implements Message {
 *     private final SMSService smsService = new SMSService();
 *
 *     @Override
 *     public void sendMessage(String recipient, String message) {
 *         smsService.sendSMS(recipient, message);  // Adapting sendSMS to sendMessage
 *     }
 * }
 *
 * // Adapter for WhatsApp
 * public class WhatsAppAdapter implements Message {
 *     private final WhatsAppService whatsAppService = new WhatsAppService();
 *
 *     @Override
 *     public void sendMessage(String recipient, String message) {
 *         whatsAppService.sendWhatsAppMessage(recipient, message);  // Adapting sendWhatsAppMessage to sendMessage
 *     }
 * }
 * Step 4: Client Code to Use Adapters
 * In the client code, you can switch between different payment and messaging services without changing the core logic. The adapters provide a consistent interface for both payment processing and messaging.
 *

 * public class Client {
 *     public static void main(String[] args) {
 *         // Process payments using different payment methods
 *         Payment payPalPayment = new PayPalAdapter();
 *         Payment stripePayment = new StripeAdapter();
 *
 *         System.out.println("Using PayPal:");
 *         payPalPayment.processPayment(100.0);
 *
 *         System.out.println("Using Stripe:");
 *         stripePayment.processPayment(250.0);
 *
 *         // Send messages using different messaging channels
 *         Message emailMessage = new EmailAdapter();
 *         Message smsMessage = new SMSAdapter();
 *         Message whatsAppMessage = new WhatsAppAdapter();
 *
 *         System.out.println("\nSending Email:");
 *         emailMessage.sendMessage("user@example.com", "Your order has been processed!");
 *
 *         System.out.println("\nSending SMS:");
 *         smsMessage.sendMessage("+123456789", "Your package is on the way!");
 *
 *         System.out.println("\nSending WhatsApp message:");
 *         whatsAppMessage.sendMessage("+123456789", "Hello! This is a WhatsApp message.");
 *     }
 * }
 * Explanation of the Adapter Pattern Implementation
 * Adapters for Payment and Messaging:
 *
 * Each adapter class (PayPalAdapter, StripeAdapter, EmailAdapter, SMSAdapter, WhatsAppAdapter) implements a consistent interface (Payment or Message), allowing the client to use different payment and messaging services with a unified API.
 * The adapters wrap the respective adaptee classes (like PayPalPayment or EmailService), translating the calls to a format that each service expects.
 * Client Flexibility:
 *
 * The client (Client class) can use any payment or messaging service without worrying about specific details of each service. It only interacts with the Payment and Message interfaces, ensuring flexibility and simplifying switching between services.
 * Extensibility:
 *
 * If a new payment gateway or messaging service is added, only a new adapter class is needed. The client code remains unchanged, and the adapter provides seamless integration.
 * Output Example
 * The output will vary depending on which adapter is in use:
 *

 * Using PayPal:
 * Processing PayPal payment of $100.0
 *
 * Using Stripe:
 * Processing Stripe payment of $250.0
 *
 * Sending Email:
 * Sending email to user@example.com: Your order has been processed!
 *
 * Sending SMS:
 * Sending SMS to +123456789: Your package is on the way!
 *
 * Sending WhatsApp message:
 * Sending WhatsApp message to +123456789: Hello! This is a WhatsApp message.
 * Benefits of the Adapter Pattern in this Context
 * Loose Coupling: The client code is decoupled from specific implementations of payment or messaging services.
 * Flexibility: New payment or messaging services can be added without modifying the client code.
 * Consistency: The client code interacts with unified Payment and Message interfaces, regardless of the specific underlying implementation.
 * This example demonstrates how the adapter pattern enables a flexible, maintainable architecture for integrating multiple, incompatible systems under a unified interface.
 *
 *
 * */