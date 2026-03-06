package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Address;
import com.hyu.electronicsecwebsitebe.service.GhnLocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GhnLocationServiceImpl implements GhnLocationService {
    private static final String GHN_BASE_URL =
            "https://online-gateway.ghn.vn/shiip/public-api/master-data";

    private static final String GHN_FEE_URL =
            "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";

    @Value("${ghn.token}")
    private String token;

    @Value("${ghn.shop-id}")
    private String shopId;

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public Integer getDistrictId(String city, String district) {
        Integer provinceId = getProvinceId(city);
        if (provinceId == null) return null;

        String url = GHN_BASE_URL + "/district?province_id=" + provinceId;

        HttpEntity<Void> request = new HttpEntity<>(headers());
        ResponseEntity<Map> response =
                restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        List<Map<String, Object>> districts =
                (List<Map<String, Object>>) response.getBody().get("data");

        return districts.stream()
                .filter(d -> normalize(district).equalsIgnoreCase(normalize(d.get("DistrictName").toString())))
                .map(d -> (Integer) d.get("DistrictID"))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getWardCode(Integer districtId, String ward) {
        String url = GHN_BASE_URL + "/ward?district_id=" + districtId;

        HttpEntity<Void> request = new HttpEntity<>(headers());
        ResponseEntity<Map> response =
                restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        List<Map<String, Object>> wards =
                (List<Map<String, Object>>) response.getBody().get("data");

        return wards.stream()
                .filter(w -> normalize(ward).equalsIgnoreCase(normalize(w.get("WardName").toString())))
                .map(w -> w.get("WardCode").toString())
                .findFirst()
                .orElse(null);
    }

    @Override
    public int calculateShippingFee(Address address, int totalAmount) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", token);
        headers.set("ShopId", shopId);

        Map<String, Object> body = new HashMap<>();
        body.put("service_type_id", 2);
        body.put("to_district_id", address.getGhnDistrictId());
        body.put("to_ward_code", address.getGhnWardCode());
        body.put("weight", 500);
        body.put("length", 20);
        body.put("width", 20);
        body.put("height", 10);
        int insuranceValue = Math.min(totalAmount, 20000000);
        body.put("insurance_value", insuranceValue);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                GHN_FEE_URL,
                request,
                Map.class
        );

        Map data = (Map) response.getBody().get("data");
        return (int) data.get("total");
    }

    private Integer getProvinceId(String city) {

        String url = GHN_BASE_URL + "/province";

        HttpEntity<Void> request = new HttpEntity<>(headers());
        ResponseEntity<Map> response =
                restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        List<Map<String, Object>> provinces =
                (List<Map<String, Object>>) response.getBody().get("data");

        return provinces.stream()
                .filter(p -> normalize(city).equalsIgnoreCase(normalize(p.get("ProvinceName").toString())))
                .map(p -> (Integer) p.get("ProvinceID"))
                .findFirst()
                .orElse(null);
    }

    private String normalize(String s) {
        return s.trim()
                .toLowerCase()
                .replace("quận", "")
                .replace("huyện", "")
                .replace("thành phố", "")
                .replace("tỉnh", "")
                .replace("tp", "")
                .replace("phường", "")
                .replace("xã", "")
                .replaceAll("\\s+", "");
    }
}
