package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.DetailBill;
import com.hyu.electronicsecwebsitebe.repository.DetailBillRepository;
import com.hyu.electronicsecwebsitebe.service.DetailBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailBillServiceImpl implements DetailBillService {

    @Autowired
    private DetailBillRepository DetailBillRepository;

    @Override
    public List<DetailBill> findBybillId(String billId){
        return DetailBillRepository.findBybillId(billId);
    }

}
