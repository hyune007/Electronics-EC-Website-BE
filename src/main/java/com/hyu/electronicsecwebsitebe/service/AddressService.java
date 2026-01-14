package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Address;
import com.hyu.electronicsecwebsitebe.model.Customer;

public interface AddressService {
    Address createAddress(Address address, Customer customer, String city, String ward, String detailAddress);

    String generateAddressId();

    Void setDefaultAddress(Customer customer, Address address);
}
