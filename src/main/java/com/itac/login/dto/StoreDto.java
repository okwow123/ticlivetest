package com.itac.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.user.Users;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    private String grade;
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
                .grade(grade)
                //.createDate(createDate)
                //.modificationDate(modificationDate)
                .images(images)
                //.users(users)
                .build();
    }

}
