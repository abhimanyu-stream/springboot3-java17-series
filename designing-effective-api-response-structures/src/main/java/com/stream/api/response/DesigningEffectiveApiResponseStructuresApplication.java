package com.stream.api.response;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesigningEffectiveApiResponseStructuresApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesigningEffectiveApiResponseStructuresApplication.class, args);
	}

}
/**
 *
 * In Spring Boot, MessageSource is a powerful way to manage localized messages for API responses, making it easy to support multiple languages. Here’s a complete example demonstrating how to set up MessageSource for localized messages in API responses, including a Spring Boot 3 configuration with MySQL integration.
 *
 * Scenario
 * In this example, we’ll create an API that responds with messages in different languages based on the client’s locale. We’ll use MessageSource to store and retrieve localized messages and a MySQL database to store other related application data.
 *
 *
 * Explanation of Components
 * MessageSource Bean:
 *
 * MessageSource loads properties files for each language, and we use it in MessageService to retrieve messages based on the locale.
 * LocaleResolver:
 *
 * The LocaleResolver detects the client’s preferred language from the Accept-Language header. If no locale is specified, it defaults to English.
 * Properties Files for Localization:
 *
 * The properties files (messages_en.properties, messages_fr.properties, etc.) contain localized messages for each language. Each key corresponds to a different message, such as greeting and welcome_message.
 * MessageService:
 *
 * MessageService fetches localized messages based on a key and locale. This decouples message retrieval from other parts of the application, making it easier to handle internationalization.
 * MessageLog Entity and Repository:
 *
 * MessageLog represents a database table for storing messages, with fields for the message text, language, and recipient. This can be useful for auditing or analyzing the usage of localized messages.
 * GreetingController:
 *
 * The GreetingController provides an endpoint /api/greet where users can specify a name. The controller retrieves the localized greeting message, logs it to the MySQL database, and sends it as the API response.
 * Example Usage
 * Request:
 *
 * Send a GET request to /api/greet?name=John with an Accept-Language header set to fr (French).
 * http
 * Copy code
 * GET /api/greet?name=John
 * Accept-Language: fr
 * Response:
 *
 * The application responds with: "Bonjour, John!" from the French properties file.
 * Logging to MySQL:
 *
 * In the message_log table, an entry is created with the message text "Bonjour, John!", language "fr", and recipient "John".
 * Benefits
 * Internationalization: The use of MessageSource and properties files allows easy support for multiple languages.
 * Locale Detection: The LocaleResolver ensures that the application respects the client’s language preference.
 * Decoupled Localization: By separating message retrieval into MessageService, we make the application easier to maintain and extend with additional languages.
 * Logging: The MessageLog entity provides a way to track API responses, useful for auditing purposes.
 * This implementation shows how Spring Boot’s localization support can be used to deliver a tailored experience for users across different languages while maintaining a clean and manageable code structure.
 *
 *
 * */