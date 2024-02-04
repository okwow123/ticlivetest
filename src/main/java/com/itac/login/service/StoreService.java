package com.itac.login.service;

import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    public List<Store> allStores(){
        return storeRepository.findAll();
    }
    public List<Store> searchWithWord(String searchWord){
        List<Store> storeList = storeRepository.findByStoreNameContaining(searchWord);
        if(!storeList.isEmpty()){
            return storeList;
        }
        return null;
    }

    @GetMapping("/location/{locationWord}")
    public List<Store> searchWithLocation(String locationWord){
        List<Store> storeList = storeRepository.findByStoreLocationContaining(locationWord);
        if(!storeList.isEmpty()){
            return storeList;
        }
        return null;
    }
}
