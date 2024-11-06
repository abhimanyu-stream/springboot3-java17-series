package com.stream.cqrs.order.command.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

import javax.annotation.Nonnull;


@Slf4j
public class OrderServiceEventsExceptionHandler implements ListenerInvocationErrorHandler {
    @Override
    public void onError(@Nonnull Exception e, @Nonnull EventMessage<?> event, @Nonnull EventMessageHandler eventHandler)
            throws Exception
    {
        log.error("Error in order service event.");
        throw e;
    }
}
