package com.hyu.electronicsecwebsitebe.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ReturnRequest {
    private String billId;
    private String reason;
    private List<ReturnItem> returnItems;
}
