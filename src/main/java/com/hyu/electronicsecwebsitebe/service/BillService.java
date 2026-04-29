package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.dto.request.ReturnItem;
import com.hyu.electronicsecwebsitebe.model.Bill;

import java.util.List;

public interface BillService {

    List<Bill> getAllBills();

    List<Bill> getBillsByEmployee(String employeeId);

    List<Bill> findByCustomerId(String customerId);

    Bill requestReturnBill(String billId, String reason, List<ReturnItem> returnItems);

    Bill approveReturnBill(String billId);

    Bill rejectReturnBill(String billId);
}
