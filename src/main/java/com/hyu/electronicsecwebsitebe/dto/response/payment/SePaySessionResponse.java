package com.hyu.electronicsecwebsitebe.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SePaySessionResponse {
    private String billId;
    private String description;
    private String bankCode;
    private String accountNumber;
    private String accountName;
    private String amount;
    private String qrUrl;
}
