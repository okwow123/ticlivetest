package com.itac.login.entity.store;

import com.itac.login.entity.user.Users;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.TypeDef;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(of= {"storeNum"}) // equals, hashCode 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity

@TypeDef(name="json", typeClass= JsonType.class)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeNum;
    private String storeName;
    private String storeLocation;
    private String storePhoneNum;
    private String grade;

    private String storeInfo;
    private LocalDate createDate;
    private LocalDate modificationDate;
    @Type(type="json")
    @Column(name="images",columnDefinition = "json")
    private List<MultipartFile> images;

    @ManyToOne
    @JoinColumn(name="userNum")
    private Users users;


    @Builder
    public Store(Long storeNum, String storeName, String storeLocation, String storePhoneNum,String grade,String storeInfo, List<MultipartFile> images) {
        super();
        this.storeNum = storeNum;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.storePhoneNum = storePhoneNum;
        this.grade = grade;
        this.storeInfo = storeInfo;
        this.images = images;
    }

}
