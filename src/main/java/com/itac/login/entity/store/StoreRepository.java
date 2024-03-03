package com.itac.login.entity.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface StoreRepository extends JpaRepository<Store,Long> {

    List<Store> findByStoreNameContaining(String searchWord);


    //카테고리가 없어서 비활성화
//    List<Store> findByStoreCategoryContaining(String categoryWord);

    List<Store> findByStoreLocationContaining(String locationWord);

    @Query(value="select * from public.store where storenum=(:storenum)", nativeQuery = true)
    Store createUser(Long storenum);

    @Query(value="select * from public.store where usernum=(:usernum)", nativeQuery=true)
    List<Store> findByManageStore(long usernum);

    @Query(value="select * from public.store s where usernum=(:usernum)", nativeQuery=true)
    List<Store> findByManageStore2(long usernum);

}
