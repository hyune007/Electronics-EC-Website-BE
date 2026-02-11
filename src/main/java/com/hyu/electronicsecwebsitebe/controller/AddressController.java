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

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Address>> getAddressesByCustomerId(@PathVariable String customerId) {
        List<Address> addresses = addressService.getAddressesByCustomerId(customerId);
        return ResponseEntity.ok (addresses);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable String id) {
        Address address = addressService.findById (id);
        return ResponseEntity.ok (address);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        try {
            Address createdAddress = addressService.createAddress(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable String id, @RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress (id, address);
        return ResponseEntity.ok (updatedAddress);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        addressService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }
}
