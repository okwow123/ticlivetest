package com.itac.login.service;

import com.itac.login.entity.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;
}
