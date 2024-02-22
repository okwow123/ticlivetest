package com.itac.login.entity.store;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itac.login.entity.StringListConverter;
import com.itac.login.entity.review.Review;
import com.itac.login.entity.user.Users;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;

import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.TypeDef;

import java.io.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@EqualsAndHashCode(of= {"storeNum"}) // equals, hashCode 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString(exclude = "reviews")
@Entity
@TypeDef(name="json", typeClass= JsonType.class)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="storenum")
    private Long storeNum;
    @Column(name="storename")
    private String storeName;
    @Column(name="storelocation")
    private String storeLocation;
    @Column(name="storephonenum")
    private String storePhoneNum;
    @Column(name="grade")
    private Float grade;
    @Column(name="storeinfo")
    private String storeInfo;
    @Column(name="createdate")
    private LocalDate createDate;
    @Column(name="modificationdate")
    private LocalDate modificationDate;

//    @Column(name="lowprice")
//    private int lowprice;
//    @Column(name="highprice")
//    private int highprice;

//    @Column(name="lat")
//    private float lat;
//    @Column(name="lng")
//    private float lng;

    @Column(name="images")
    @Convert(converter = StringListConverter.class)
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonManagedReference // 순환참조 방지
    @JoinColumn(name="userNum")
    private Users users;


    @OneToMany(mappedBy = "store",fetch=FetchType.LAZY)
    @JsonBackReference //순환참조 방지
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Store(Long storeNum, String storeName, String storeLocation, String storePhoneNum,Float grade,String storeInfo, List<String> images, Users users) {

        super();
        this.storeNum = storeNum;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.storePhoneNum = storePhoneNum;
        this.grade = grade;
        this.storeInfo = storeInfo;
        this.images = images;
        this.users = users;
        this.createDate = LocalDate.now();
    }

    @PostLoad
    private void postLoad() {
        // 엔티티가 로드될 때 실행할 코드 작성
        log.info("Store Entity loaded(storeNum): " + this.toString());
    }
}
