package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Address;
import com.hyu.electronicsecwebsitebe.repository.AddressRepository;
import com.hyu.electronicsecwebsitebe.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private static final int MAX_ADDRESS = 5;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private GhnLocationServiceImpl ghnLocationService;

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> getAddressesByCustomerId(String id) {
        return addressRepository.findByCustomer_Id(id);
    }

    @Override
    public Address findById(String id) {
        return addressRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Address not found: " + id));
    }

    @Override
    public Address createAddress(Address address) {
        String customerId = address.getCustomer().getId();

        long count = addressRepository.countByCustomer_Id(customerId);
        if (count >= MAX_ADDRESS) {
            throw new IllegalStateException(
                    "Mỗi khách hàng chỉ được tối đa " + MAX_ADDRESS + " địa chỉ"
            );
        }

        boolean exists = addressRepository
                .existsByCustomer_IdAndCityAndDistrictAndWardAndDetailAddress(
                        customerId,
                        address.getCity(),
                        address.getDistrict(),
                        address.getWard(),
                        address.getDetailAddress()
                );

        if (exists) {
            throw new IllegalArgumentException("Địa chỉ đã tồn tại");
        }

        Integer districtId = ghnLocationService.getDistrictId(
                address.getCity(),
                address.getDistrict()
        );

        String wardCode = ghnLocationService.getWardCode(
                districtId,
                address.getWard()
        );

        if (districtId == null || wardCode == null) {
            throw new IllegalArgumentException(
                    "Địa chỉ chưa được hỗ trợ giao hàng"
            );
        }

        address.setGhnDistrictId(districtId);
        address.setGhnWardCode(wardCode);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(String id, Address address) {

        Address existing = findById(id);

        address.setId(existing.getId());
        address.setCustomer(existing.getCustomer());

        return addressRepository.save(address);
    }

    @Override
    public void deleteById(String id) {
        if (!addressRepository.existsById(id)) {
            throw new EntityNotFoundException("Address not found: " + id);
        }
        addressRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return addressRepository.existsById (id);
    }

    @Override
    @Transactional
    public Address setDefaultAddress(String id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Address not found: " + id));
        String customerId = address.getCustomer().getId();

        List<Address> addresses =
                addressRepository.findByCustomer_Id(customerId);

        for (Address a : addresses) {
            if (a.isDefault()) {
                a.setDefault(false);
            }
        }

        address.setDefault(true);

        addressRepository.saveAll(addresses);
        return addressRepository.save(address);
    }
}
