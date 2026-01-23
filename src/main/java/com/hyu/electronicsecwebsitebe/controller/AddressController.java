package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.Address;
import com.hyu.electronicsecwebsitebe.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAll() {
        List<Address> addresses = addressService.getAll ();
        return ResponseEntity.ok (addresses);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable String id) {
        if (!addressService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        Address address = addressService.findById (id);
        return ResponseEntity.ok (address);
    }

    @PostMapping("/save")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        if (addressService.existsById (address.getId ()) || addressService.existsByAddress (address.getCustomer().getId(), address.getCity (), address.getWard (), address.getDetailAddress ())) {
            return ResponseEntity.badRequest ().build ();
        }
        Address createdAddress = addressService.createAddress (address);
        return ResponseEntity.status (HttpStatus.CREATED).body (createdAddress);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable String id, @RequestBody Address address) {
        if (!addressService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        address.setId (id);
        Address updatedAddress = addressService.updateAddress (address);
        return ResponseEntity.ok (updatedAddress);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        if (!addressService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        addressService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }
}
