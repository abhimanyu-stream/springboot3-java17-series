package com.stream.online.payment.dto;


import lombok.Data;

@Data

public class PaymentCreateRequest {


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


   /**
    * below value for variables can be set a server side a controller or service or configuration object
    * cardEntryMethod
    * industryType
    * capture
    *
    * */
    //private String cardEntryMethod;
    //private String industryType;
    //private boolean capture;


}