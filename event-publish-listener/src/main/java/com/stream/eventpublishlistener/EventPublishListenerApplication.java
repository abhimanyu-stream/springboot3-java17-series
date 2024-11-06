package com.stream.eventpublishlistener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.UUID;


@Configuration
@ComponentScan(basePackages = "com.stream.eventpublishlistener")
public class EventPublishListenerApplication {


	public static void main(String[] args) {
		//SpringApplication.run(EventPublishListenerApplication.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(EventPublishListenerApplication.class);

		Amazon amazon = context.getBean("amazon",Amazon.class);
		amazon.orderCreatedEvent(UUID.randomUUID().toString(),"Abhimanyu", "Home", 898989.00);

	}


}

