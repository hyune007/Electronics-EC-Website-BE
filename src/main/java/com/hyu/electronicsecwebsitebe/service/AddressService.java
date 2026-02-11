package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAll();

    List<Address> getAddressesByCustomerId(String id);

    Address findById(String id);

    Address createAddress(Address address);

    Address updateAddress(String id, Address address);

    void deleteById(String id);

    boolean existsById(String id);
}
