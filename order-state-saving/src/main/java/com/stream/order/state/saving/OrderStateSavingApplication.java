package com.stream.order.state.saving;

import com.stream.order.state.saving.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderStateSavingApplication {


	public static void main(String[] args) {
		SpringApplication.run(OrderStateSavingApplication.class, args);

	}

}
