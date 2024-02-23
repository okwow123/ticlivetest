package com.itac.login.common.util;

import com.itac.login.entity.review.ReviewRepository;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class SortMethod {

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ReviewRepository reviewRepository;


    public List<Store> recommendfy(List<Store> list){
        final int gradeWeight = 100,
                reviewWeight=100,
                priceWeight=100,
                distanceWeight=100;
        HashMap<Store,Integer> map = new HashMap<Store,Integer>();

        //별점순으로 점수 매겨주기
        list = sortByGrade(list);
        
        //for문 내부의 Hashmap을 바꾸는 부분은 아래 메서드가 추가 구현되면 반복되는 구문이라서 메서드화예정
        // -> 순회할리스트, 점수를 기록할 map, 가중치  순으로 매개변수입력 (반환형 map)
        map = calculateMap(list,map,gradeWeight);

        // 리뷰 많은순 처리 필요
        list = sortByReviews(list);
        map = calculateMap(list,map,reviewWeight);
//
//        // 가격 낮은순으로 처리 필요
//        list = sortByPrice(list);
//        map = calculateMap(list,map,priceWeight);

//        // 거리 순으로 처리 필요
//        list = sortByDistance(list,new xyPoint(37.489552f,126.724072f));
//        map = calculateMap(list,map,distanceWeight);


        // 처리된 결과에 따라 list 재정렬
        HashMap<Store, Integer> finalMap = map;
        Collections.sort(list,new Comparator<Store>(){
            @Override
            public int compare(Store o1, Store o2) {
                return finalMap.get(o2)- finalMap.get(o1);
            }
        });
        return list;
    }

    private HashMap<Store, Integer> calculateMap(List<Store> list, HashMap<Store, Integer> map, int Weight) {
        for(Store store:list){
            map.put(store,map.getOrDefault(store,0)+
                    ((list.size())-(list.indexOf(store)+1) /(list.size())*Weight));
        }
        return map;
    }

    //점수 높은 순(내림차순)으로 정렬, 2번째 매개변수가 있을경우에는 (오름차순)으로 정렬
    public List<Store> sortByGrade(List<Store> list,Object ...flag){
        final int orderDirection = (flag.length==0?-1:1); //내림차순일 때 -1, 오름차순일때 1

        list.sort((o1, o2) ->{
//            log.info("o1.toString(): "+ o1.toString());
//            log.info("o2.toString(): "+ o2.toString());
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
        log.info("sortByReviews메서드 시작");
        list.sort((Store o1,Store o2)->{
//            log.info("o1.toString(): "+ o1.toString());
//            log.info("Store o1의 리뷰들 :"+ reviewRepository.findAllByStore(o1).toString());
//            log.info("Store o1의 리뷰들 :"+ reviewRepository.findAllByStoreStoreNum(o1.getStoreNum()).toString());
//            log.info("Store o1의 리뷰들의 크기 :"+ reviewRepository.findAllByStoreStoreNum(o1.getStoreNum()).size());

//            log.info("o2.toString(): "+ o2.toString());
//            log.info("Store o2의 리뷰들 :"+ reviewRepository.findAllByStore(o2).toString());
//            log.info("Store o2의 리뷰들 :"+ reviewRepository.findAllByStoreStoreNum(o2.getStoreNum()).toString());
//            log.info("Store o2의 리뷰들의 크기 :"+ reviewRepository.findAllByStoreStoreNum(o2.getStoreNum()).size());
            int src1 = reviewRepository.findAllByStoreStoreNum(o1.getStoreNum()).size();
            int src2 = reviewRepository.findAllByStoreStoreNum(o2.getStoreNum()).size();
            if(src1-src2!=0){
                //return (src1>src2?-1:1)*orderDirection;
                return (src1>src2?-1:1)*orderDirection;
            }else
                return 0;
        });

        return list;
    }

//    public List<Store> sortByPrice(List<Store> list,Object...flag){
//        final int orderDirection = (flag.length==0?-1:1); //내림차순일 때 -1, 오름차순일때 1
//
//        if(orderDirection==-1) //내림차순으로 정렬할때는 (각 매장당) 비싼가격이 높은것이 먼저 보이게 정렬
//            list.sort((o1,o2)->{
//                int src1 = o1.getHighprice();
//                int src2 = o2.getHighprice();
//                if(src1-src2!=0){
//                    return (src1>src2?-1:1)*orderDirection;
//                }else
//                    return 0;
//            });
//        else // 오름차순으로 정렬할때는 (각 매장당) 싼 가격이 낮은것이 먼저 보이게 정렬
//            list.sort((o1,o2)->{
//                int src1 = o1.getLowprice();
//                int src2 = o2.getLowprice();
//                if(src1-src2!=0){
//                    return (src1<src2?1:-1)*orderDirection;
//                }else
//                    return 0;
//            });
//
//        return list;
//    }

//    public List<Store> sortByDistance(List<Store> list,xyPoint point,Object...flag){ //
//        final int orderDirection = (flag.length==0?1:-1); //오름차순일 때 -1, 내림차순일때 1
//        float lat = point.getLat();
//        float lng = point.getLng();
//
//        list.sort((o1,o2)->{
//            double distance1 = getDistanceBetween2Points(o1.getLng(),o1.getLat(),lat,lng),
//                    distance2 = getDistanceBetween2Points(o2.getLng(),o2.getLat(),lat,lng);
//            if(distance1<distance2)
//                return -1;
//            else if(distance1>distance2)
//                return 1;
//            else
//                return 0;
//        });
//        return list;
//    }

//    public double getDistanceBetween2Points(float x1, float y1,float x2, float y2){
//        return Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2));
//    }
}
