package com.stream.online.payment.service;

import com.stream.online.payment.dto.PaymentCreateRequest;
import com.stream.online.payment.dto.PaymentResponse;
import com.stream.online.payment.model.Payment;
import com.stream.online.payment.repository.PaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse createPayment(PaymentCreateRequest paymentRequest) {
        Payment payment = convertToEntity(paymentRequest);
        Payment savedPayment = paymentRepository.save(payment);
        return convertToResponseDTO(savedPayment);
    }

    private Payment convertToEntity(PaymentCreateRequest paymentRequestDTO) {
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentRequestDTO, payment);
        return payment;
    }

    private PaymentResponse convertToResponseDTO(Payment payment) {
        PaymentResponse responseDTO = new PaymentResponse();
        BeanUtils.copyProperties(payment, responseDTO);
        return responseDTO;
    }
}
