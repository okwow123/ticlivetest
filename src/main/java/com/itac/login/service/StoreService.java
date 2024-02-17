package com.itac.login.service;

import com.itac.login.dto.StoreDto;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

    private List<Store> recommend(List<Store> list){
        final int gradeWeight = 1;
        HashMap<Store,Integer> map = new HashMap<Store,Integer>();
        
        //별점순으로 점수 매겨주기
        list = sortByGrade(list);
        for(Store store:list){
            map.put(store,map.getOrDefault(store,0)+
                    ((list.size()-1)-list.indexOf(store))/(list.size()-1)*100*gradeWeight);
        }

        //리뷰 많은순 처리 필요
        //list =

        //가격 낮은순으로 처리 필요

        //거리 순으로 처리 필요

        //처리된 결과에 따라 list 재정렬

        Collections.sort(list,new Comparator<Store>(){
            @Override
            public int compare(Store o1, Store o2) {
                return map.get(o2)-map.get(o1);
            }
        });

        return list;
    }

    //점수 높은 순(내림차순)으로 정렬, 2번째 매개변수가 있을경우에는 (오름차순)으로 정렬
    private List<Store> sortByGrade(List<Store> list,Object ...flag){

        final int orderDirection = (flag.length==0?-1:1); //내림차순일 때 -1, 오름차순일때 1

        list.sort((o1, o2) ->{
            return orderDirection*(int)(Float.parseFloat(o1.getGrade())-Float.parseFloat(o2.getGrade()));
        } );

        return list;
    }
}