package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.DetailBill;

import java.util.List;

public interface DetailBillService {

    List<DetailBill> findBybillId(String billId);

}
