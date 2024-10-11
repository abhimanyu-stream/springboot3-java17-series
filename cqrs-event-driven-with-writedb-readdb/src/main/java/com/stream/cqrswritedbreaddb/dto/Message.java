package com.stream.cqrswritedbreaddb.dto;

import org.springframework.http.HttpStatus;

public class Message {

    private HttpStatus status;
    private String message;
}
