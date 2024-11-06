package com.stream.eventpublishlistener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Getter
@Setter

public class OrderEvent {
    private String id;
    private String userName;
    private String productId;
    private Double cost;


    public OrderEvent(String id, String userName, String productId, Double cost) {
        this.id = id;
        this.userName = userName;
        this.productId = productId;
        this.cost = cost;
    }


}
/***
 *
 * All right it works
 public class OrderEvent extends  ApplicationEvent{
 private String id;
 private String userName;
 private String productId;
 private Double cost;


 public OrderEvent(String id, String userName, String productId, Double cost) {
 super(id);
 this.id = id;
 this.userName = userName;
 this.productId = productId;
 this.cost = cost;
 }


 }
 * */