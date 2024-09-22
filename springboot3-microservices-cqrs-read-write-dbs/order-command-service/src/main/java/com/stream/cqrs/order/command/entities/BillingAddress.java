package com.stream.cqrs.order.command.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class BillingAddress {
    @Column(name = "billing_address_line1")
    private String addressLine1;

    @Column(name = "billing_address_line2")
    private String addressLine2;

    @Column(name = "billing_address_city")
    private String city;

    @Column(name = "billing_address_state")
    private String state;

    @Column(name = "billing_address_zipcode")
    private String zipcode;
}
