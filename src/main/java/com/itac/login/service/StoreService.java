package com.itac.login.service;

import com.itac.login.dto.StoreDto;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private Sort gradeDesc = Sort.by(Sort.Direction.DESC, "grade");

    public List<Store> allStores() {
        return storeRepository.findAll(gradeDesc);
    }

    public List<Store> searchWithWord(String searchWord) {

        return storeRepository.findByStoreNameContaining(searchWord);
    }

    @GetMapping("/location/{locationWord}")
    public List<Store> searchWithLocation(String locationWord){
        return storeRepository.findByStoreLocationContaining(locationWord);
    }

    public Store create(StoreDto storeDto){
        storeDto.setStoreNum(null);
        Store store = storeDto.toEntity();
        return storeRepository.save(store);
    }

    public boolean update(Long id, StoreDto dto){

        Store store = dto.toEntity();
        Store target = storeRepository.findById(id).orElse(null);
        if(target == null || !dto.getStoreNum().equals(id)){
            return false;
        }

        storeRepository.save(store);

        return true;
    }

    public Store show(Long id){

        return storeRepository.findById(id).orElse(null);
    }

    public boolean delete(Long id){

        Store target = storeRepository.findById(id).orElse(null);
        if(target!=null){
            storeRepository.delete(target);
            return true;
        }
        return false;

    }
}