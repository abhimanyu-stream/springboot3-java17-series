package com.stream.api.response.transfer;

import com.stream.api.response.model.MessageLog;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Objects;

@Slf4j
@Builder
public class ApiResponse<T> {

    private T data;
    private MessageLog messageLog;


    public ApiResponse(){}

    public ApiResponse(T data, MessageLog messageLog) {
        this.data = data;
        this.messageLog = messageLog;

    }
}