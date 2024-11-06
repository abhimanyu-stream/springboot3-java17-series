package com.stream.api.response.exception;

import com.stream.api.response.model.MessageLog;
import com.stream.api.response.transfer.ApiError;
import com.stream.api.response.transfer.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleItemNotFoundException(ItemNotFoundException ex, Locale locale) {

        //Note :- the Locale locale must be same as the Locale locale received in RestController


        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        // Log the message to MySQL database
        MessageLog log = new MessageLog();
        log.setMessage("Item not found");
        log.setLanguage(locale.getLanguage());
        log.setRecipient("username");
        log.setSuccess(Boolean.FALSE);


        ApiResponse<Object> apiResponse = new ApiResponse<>(null, log);
        return new ResponseEntity<>(apiResponse, apiError.getStatus());
    }

    // Other exception handlers can be added here
}