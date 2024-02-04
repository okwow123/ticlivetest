package com.itac.login.entity.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    List<Store> findByStoreNameContaining(String searchWord);

    //카테고리가 없어서 비활성화
//    List<Store> findByStoreCategoryContaining(String categoryWord);

    List<Store> findByStoreLocationContaining(String locationWord);

}
