package com.stream.online.payment.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String number;
    private String email;
    private String address;
    private int billValue;
    private String cardNumber;
    private String cardHolder;
    private String dateValue;
    private String cvc;


    public Payment(){}
    public Payment(Long id, String name, String number, String email, String address, int billValue, String cardNumber, String cardHolder, String dateValue, String cvc) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.address = address;
        this.billValue = billValue;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.dateValue = dateValue;
        this.cvc = cvc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (billValue != payment.billValue) return false;
        if (! Objects.equals(id, payment.id)) return false;
        if (! Objects.equals(name, payment.name)) return false;
        if (! Objects.equals(number, payment.number)) return false;
        if (! Objects.equals(email, payment.email)) return false;
        if (! Objects.equals(address, payment.address)) return false;
        if (! Objects.equals(cardNumber, payment.cardNumber)) return false;
        if (! Objects.equals(cardHolder, payment.cardHolder)) return false;
        if (! Objects.equals(dateValue, payment.dateValue)) return false;
        return Objects.equals(cvc, payment.cvc);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + billValue;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (cardHolder != null ? cardHolder.hashCode() : 0);
        result = 31 * result + (dateValue != null ? dateValue.hashCode() : 0);
        result = 31 * result + (cvc != null ? cvc.hashCode() : 0);
        return result;
    }
}
