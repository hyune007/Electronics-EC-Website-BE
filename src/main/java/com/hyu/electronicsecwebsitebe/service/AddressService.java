package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAll();

    Address findById(String id);

    Address createAddress(Address employee);

    Address updateAddress(Address employee);

    void deleteById(String id);

    boolean existsById(String id);

    boolean existsByAddress(String city, String ward, String detailAddress);
}
