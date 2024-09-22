package com.stream.cqrs.order.command.exceptions;


public class OrderCreationException extends RuntimeException {
    public OrderCreationException() {
        super();
    }

    public OrderCreationException(String message) {
        super(message);
    }
}
