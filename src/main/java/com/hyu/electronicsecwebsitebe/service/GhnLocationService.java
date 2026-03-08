package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Address;

public interface GhnLocationService {
    Integer getProvinceId(String city);

    Integer getDistrictId(String city, String district);

    String getWardCode(Integer districtId, String ward);

    int calculateShippingFee(Address address, int totalAmount);
}
