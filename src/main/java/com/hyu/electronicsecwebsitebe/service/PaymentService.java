package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.dto.request.payment.SePayWebhookPayload;
import com.hyu.electronicsecwebsitebe.dto.response.payment.PaymentStatusResponse;
import com.hyu.electronicsecwebsitebe.dto.response.payment.SePaySessionResponse;
import com.hyu.electronicsecwebsitebe.dto.response.payment.SePayWebhookResponse;

public interface PaymentService {
    SePaySessionResponse createPaymentSession(String billId);

    SePayWebhookResponse handlePaymentWebhook(SePayWebhookPayload payload, String authHeader);

    PaymentStatusResponse getPaymentStatus(String billId);
}
