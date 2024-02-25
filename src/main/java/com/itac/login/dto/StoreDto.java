package com.itac.login.dto;

import com.itac.login.entity.store.Store;
import com.itac.login.entity.user.Users;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {

    private Long storeNum;
    private String storeName;
    private String storeLocation;
    private String storePhoneNum;
    private Float grade;
    private String storeInfo;
    private LocalDate createDate;
    private LocalDate modificationDate;
    private List<String> images;
    private Users users;


    public Store toEntity(){
        return Store.builder().storeNum(storeNum)
                .storeName(storeName)
                .storeLocation(storeLocation)
                .storePhoneNum(storePhoneNum)
                .storeInfo(storeInfo)
                .grade(grade)
                //.createDate(createDate)
                //.modificationDate(modificationDate)
                .images(images)
                .users(users)
                .build();
    }
}
