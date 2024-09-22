package com.stream.cqrs.order.command.utils;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> implements Serializable {

    private static final long serialVersionUID = 5950557411032115488L;

    private T data;
    private String message = null;
    private String apiStatus = "success";
}
