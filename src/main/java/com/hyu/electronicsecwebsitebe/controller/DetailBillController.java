package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.DetailBill;
import com.hyu.electronicsecwebsitebe.repository.DetailBillRepository;
import com.hyu.electronicsecwebsitebe.service.DetailBillService;
import com.hyu.electronicsecwebsitebe.service.impl.DetailBillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detail-bill")
public class DetailBillController {

    @Autowired
    public DetailBillServiceImpl detailBillService;

    @GetMapping("/bill/{billId}")
    public ResponseEntity<List<DetailBill>> getDetailBillBybillId(@PathVariable String billId) {
        List<DetailBill> detailBills = detailBillService.findBybillId(billId);
        return ResponseEntity.ok(detailBills);
    }

}
