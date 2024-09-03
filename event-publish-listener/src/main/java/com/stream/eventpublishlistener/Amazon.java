package com.stream.eventpublishlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Amazon {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    public void orderCreatedEvent(String id,String username, String product, Double cost){

        applicationEventPublisher.publishEvent( new OrderEvent(id, username, product, cost));
    }


}
