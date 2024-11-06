package com.stream.online.payment.service;

import com.stream.online.payment.dto.PaymentCreateRequest;
import com.stream.online.payment.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse createPayment(PaymentCreateRequest paymentRequestDTO);
}
