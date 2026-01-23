package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Bill;

import java.util.List;
public interface BillService {

    List<Bill> findByCustomerId(String customerId);

}
