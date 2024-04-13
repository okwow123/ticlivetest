package com.ticlivetest.service;

import com.ticlivetest.entity.review.Review;
import com.ticlivetest.entity.review.ReviewRepository;
import com.ticlivetest.entity.store.Store;
import com.ticlivetest.entity.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    public void revalidateGrade(Long storeNum){
        List<Review> reviewList = reviewRepository.findAllByStoreStoreNum(storeNum);
        double sum = 0;
        for(Review review : reviewList){
            sum += review.getGrade();
        }
        float avg = (float) sum/reviewList.size();

        Store store = storeRepository.findById(storeNum).orElse(null);
        if(store==null) {
            log.info("조회된 store없음");
            return;
        }
        store.setGrade(avg);
        Store returnedStore =  storeRepository.save(store);
    }
}
