package com.hyu.electronicsecwebsitebe.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentStatusResponse {
    private String billId;
    private String status;
    private boolean paid;
}
