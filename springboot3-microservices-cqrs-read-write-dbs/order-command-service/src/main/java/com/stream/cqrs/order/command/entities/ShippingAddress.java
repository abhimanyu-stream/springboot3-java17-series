package com.stream.cqrs.order.command.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class ShippingAddress {

    @Column(name = "shipping_address_line1")
    private String addressLine1;

    @Column(name = "shipping_address_line2")
    private String addressLine2;

    @Column(name = "shipping_address_city")
    private String city;

    @Column(name = "shipping_address_state")
    private String state;

    @Column(name = "shipping_address_zipcode")
    private String zipcode;

}
