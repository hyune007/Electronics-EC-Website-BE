package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Address;
import com.hyu.electronicsecwebsitebe.repository.AddressRepository;
import com.hyu.electronicsecwebsitebe.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(String id) {
        return addressRepository.findById (id).orElse (null);
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save (address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save (address);
    }

    @Override
    public void deleteById(String id) {
        addressRepository.deleteById (id);
    }

    @Override
    public boolean existsById(String id) {
        return addressRepository.existsById (id);
    }

    @Override
    public boolean existsByAddress(String customerId, String city, String ward, String detailAddress) {
        return addressRepository.existsByCustomerIdAndCityAndWardAndDetailAddress(customerId, city, ward, detailAddress);
    }
}
