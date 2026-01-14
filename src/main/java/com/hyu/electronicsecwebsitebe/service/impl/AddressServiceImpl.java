package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Address;
import com.hyu.electronicsecwebsitebe.model.Customer;
import com.hyu.electronicsecwebsitebe.repository.AddressRepository;
import com.hyu.electronicsecwebsitebe.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address createAddress(Address address, Customer customer, String city, String ward, String detailAddress) {
        address.setId (generateAddressId ());
        address.setCustomer (customer);
        address.setCity (city);
        address.setWard (ward);
        address.setDetailAddress (detailAddress);
        return addressRepository.save (address);
    }

    @Override
    public String generateAddressId() {
        return "DC" + UUID.randomUUID ().toString ().substring (0, 6).toUpperCase ();
    }

    @Override
    public Void setDefaultAddress(Customer customer, Address address) {
        return null;
    }
}
