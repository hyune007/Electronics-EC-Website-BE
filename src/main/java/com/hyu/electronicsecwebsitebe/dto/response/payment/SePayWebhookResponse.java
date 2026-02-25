package com.hyu.electronicsecwebsitebe.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SePayWebhookResponse {
    private boolean success;
    private String message;
}
