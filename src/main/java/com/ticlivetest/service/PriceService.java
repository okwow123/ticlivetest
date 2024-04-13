package com.ticlivetest.service;

import com.ticlivetest.dto.MoneyResponse;
import com.ticlivetest.dto.RawResponse;
import com.ticlivetest.repository.PriceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceQueryRepository priceQueryRepository;

    public Map<String, Object> rawList(String param) {
        List<RawResponse> siteList =  priceQueryRepository.readRawList(param);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("param", param);
        returnMap.put("siteList", siteList);
        return returnMap;
    }

    public Map<String, Object> moneyList(String param) {
        List<MoneyResponse> siteList =  priceQueryRepository.readMoneyList(param);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("param", param);
        returnMap.put("siteList", siteList);
        return returnMap;
    }


    public String rawDate() {
        return priceQueryRepository.readRawDate();
    }
    public String moneyDate() {
        return priceQueryRepository.readMoneyDate();
    }

}

