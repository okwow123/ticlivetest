package com.itac.login.common.util;

import com.itac.login.entity.review.ReviewRepository;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class SortMethodClass {

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ReviewRepository reviewRepository;


    public List<Store> recommendfy(List<Store> list){
        final int gradeWeight = 1;
        HashMap<Store,Integer> map = new HashMap<Store,Integer>();

        //별점순으로 점수 매겨주기
        list = sortByGrade(list);
        
        //for문 내부의 Hashmap을 바꾸는 부분은 아래 메서드가 추가 구현되면 반복되는 구문이라서 메서드화예정
        for(Store store:list){
            map.put(store,map.getOrDefault(store,0)+
                    ((list.size())-(list.indexOf(store)+1) /(list.size())*100*gradeWeight));
        }

        //리뷰 많은순 처리 필요
        list = sortByReviews(list);

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
    public List<Store> sortByGrade(List<Store> list,Object ...flag){

        final int orderDirection = (flag.length==0?-1:1); //내림차순일 때 -1, 오름차순일때 1

        list.sort((o1, o2) ->{
            if(orderDirection*(o1.getGrade()-o2.getGrade())<0)
                return -1;
            else if(orderDirection*(o1.getGrade()-o2.getGrade())>0)
                return 1;
            else return 0;
        } );
        return list;
    }

    public List<Store> sortByReviews(List<Store> list,Object...flag){
        final int orderDirection = (flag.length==0?-1:1); //내림차순일 때 -1, 오름차순일때 1

        list.sort((o1,o2)->{
            Long sn1= o1.getStoreNum();
            int src1 = reviewRepository.findAllByStore_StoreNum(o1.getStoreNum()).size();
            int src2 = reviewRepository.findAllByStore_StoreNum(o2.getStoreNum()).size();
            if(src1-src2<0){
                return -1;
            }else if(src1-src2>0){
                return 1;
            }return 0;
        });


        return list;
    }
}
