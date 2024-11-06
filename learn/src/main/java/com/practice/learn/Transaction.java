package com.practice.learn;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction")
public class Transaction {

    private String id;

    private String transactionId;//uuid
    private String studentId;
    private String courseId;
}
