package com.stream.order_query_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * @author vishnu.g
 */
@Data
@Embeddable
public class ShippingAddress {

    @Column(name = "shipping_address_line1")
    private String line1;

    @Column(name = "shipping_address_line2")
    private String line2;

    @Column(name = "shipping_address_city")
    private String city;

    @Column(name = "shipping_address_state")
    private String state;

    @Column(name = "shipping_address_zipcode")
    private String zipcode;

}
