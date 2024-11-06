package com.stream.online.payment.dto;

import lombok.Data;

@Data
public class PaymentResponse {

    private Long id;
    private String name;
    private String number;
    private String email;
    private String address;
    private int billValue;
    private String cardNumber;
    private String cardHolder;
    private String cardExpiryDate;
    private String cvc;
    private String amount;
}
