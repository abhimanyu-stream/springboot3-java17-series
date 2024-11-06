package com.stream.eventpublishlistener;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component

public class SendEmail {

    @EventListener
    @Order(2)
    public void sendEmail(OrderEvent orderEvent){

        System.out.println(orderEvent.getId()+ "  : "+ orderEvent.getUserName()+ "  : " +orderEvent.getProductId()+ " : "+ orderEvent.getCost());

        System.out.println("Email sent successfully..");
    }
}
