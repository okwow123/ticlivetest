package com.itac.login.service;

import com.itac.login.entity.review.Review;
import com.itac.login.entity.review.ReviewRepository;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
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
        List<Review> reviewList = reviewRepository.findAllByStoreNum(storeNum);
        double sum = 0;
        for(Review review : reviewList){
            sum += Float.parseFloat(review.getGrade());
        }
        float avg = (float) sum/reviewList.size();

        Store store = storeRepository.findById(storeNum).orElse(null);
        if(store==null) {

            return;
        }
        store.setGrade(avg+"");
        Store returnedStore =  storeRepository.save(store);
    }
}
