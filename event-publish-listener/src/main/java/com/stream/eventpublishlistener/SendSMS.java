package com.stream.eventpublishlistener;


import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class SendSMS {

    //@EventListener(classes = {OrderEvent.class, PaymentEvent.class}) Listening multiple Events, You can not capture Object of event
    //@EventListener(classes = {OrderEvent.class})
    //@EventListener(classes = OrderEvent.class)
    @EventListener
    @Order(1)
    public void  sendSMS(OrderEvent orderEvent){

        System.out.println(orderEvent.getId()+ "  : "+ orderEvent.getUserName()+ "  : " +orderEvent.getProductId()+ " : "+ orderEvent.getCost());

        System.out.println("SMS sent successfully..");
    }


}
