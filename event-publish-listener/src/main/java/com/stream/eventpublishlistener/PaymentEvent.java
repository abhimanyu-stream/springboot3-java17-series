package com.stream.eventpublishlistener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
public class PaymentEvent {

    private String statusOfPayment;
}
