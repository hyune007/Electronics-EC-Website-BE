package com.hyu.electronicsecwebsitebe.dto.request;

import lombok.Data;

@Data
public class CreateBillRequest {
    private String customerId;
    private String employeeId;
    private String addressId;
    private String paymentMethod;
}
