package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.dto.request.payment.CreateSePaySessionRequest;
import com.hyu.electronicsecwebsitebe.dto.request.payment.SePayWebhookPayload;
import com.hyu.electronicsecwebsitebe.dto.response.payment.PaymentStatusResponse;
import com.hyu.electronicsecwebsitebe.dto.response.payment.SePaySessionResponse;
import com.hyu.electronicsecwebsitebe.dto.response.payment.SePayWebhookResponse;
import com.hyu.electronicsecwebsitebe.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/sepay/session")
    public ResponseEntity<SePaySessionResponse> createSession(@RequestBody CreateSePaySessionRequest request) {
        if (request == null || request.getBillId() == null || request.getBillId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(paymentService.createPaymentSession(request.getBillId()));
    }

    @PostMapping({"/sepay/webhook", "/sepay-webhook"})
    public ResponseEntity<SePayWebhookResponse> webhook(
            @RequestBody SePayWebhookPayload payload,
            @RequestHeader(value = "Authorization", required = false) String authorization
    ) {
        SePayWebhookResponse response = paymentService.handlePaymentWebhook(payload, authorization);
        if (!response.isSuccess()) {
            HttpStatus status = "Unauthorized".equalsIgnoreCase(response.getMessage())
                    ? HttpStatus.UNAUTHORIZED
                    : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sepay/status/{billId}")
    public ResponseEntity<PaymentStatusResponse> status(@PathVariable String billId) {
        return ResponseEntity.ok(paymentService.getPaymentStatus(billId));
    }
}
