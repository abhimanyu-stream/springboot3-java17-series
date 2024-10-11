package com.stream.cqrs.order.command.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@Slf4j
@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationsException(MethodArgumentNotValidException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        return detail;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail globalExceptionHandler(Exception ex, WebRequest request) {
        log.error("Something went wrong, details: {}", ex.getMessage());
        log.error(ex.getStackTrace().toString());
        ex.printStackTrace();
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong");
        return detail;
    }

    @ExceptionHandler(OrderCreationException.class)
    public ProblemDetail handleOrderCreationException(MethodArgumentNotValidException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return detail;
    }
}
