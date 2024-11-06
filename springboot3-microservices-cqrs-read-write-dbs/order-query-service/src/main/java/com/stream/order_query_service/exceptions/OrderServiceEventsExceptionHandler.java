package com.stream.order_query_service.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author vishnu.g
 */
@Slf4j
@Component
public class OrderServiceEventsExceptionHandler implements ListenerInvocationErrorHandler {
    @Override
    public void onError(@Nonnull Exception e, @Nonnull EventMessage<?> event, @Nonnull EventMessageHandler eventHandler)
            throws Exception
    {
        log.error("error");
        throw e;
    }
}
