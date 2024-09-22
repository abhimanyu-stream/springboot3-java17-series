package com.stream.order_query_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * @author vishnu.g
 */
@Data
@Embeddable
public class BillingAddress {
    @Column(name = "billing_address_line1")
    private String line1;

    @Column(name = "billing_address_line2")
    private String line2;

    @Column(name = "billing_address_city")
    private String city;

    @Column(name = "billing_address_state")
    private String state;

    @Column(name = "billing_address_zipcode")
    private String zipcode;

}
