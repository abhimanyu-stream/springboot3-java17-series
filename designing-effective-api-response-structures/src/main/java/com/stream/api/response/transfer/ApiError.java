package com.stream.api.response.transfer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;


@Slf4j
@Builder
@Setter
@Getter
public class ApiError {

    private HttpStatus status;
    private String message;

    public ApiError(){}
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


}
