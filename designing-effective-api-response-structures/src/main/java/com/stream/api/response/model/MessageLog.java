package com.stream.api.response.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "message_log")
public class MessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String language;
    private String recipient;
    private Boolean success;

    //customerId map it with


    public MessageLog() {}
}