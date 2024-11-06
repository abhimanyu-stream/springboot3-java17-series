package com.stream.cqrs.order.command.utils;


public class ResponseBuilder {
    private static final String DEFAULT_MESSAGE = "Success";

    public static <T> ResponseWrapper successResponse(T t) {

        return successResponse(t, DEFAULT_MESSAGE);
    }

    public static <T> ResponseWrapper successResponse(T t, String msg) {
        return ResponseWrapper.builder()
                .data(t).message(msg).build();
    }
}
