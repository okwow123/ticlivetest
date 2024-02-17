package com.itac.login.entity.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findAllByStoreNum(Long storeNum);
}