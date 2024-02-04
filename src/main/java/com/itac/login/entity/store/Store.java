package com.itac.login.entity.store;

import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.itac.login.entity.BaseTimeEntity;
import com.itac.login.entity.user.Users;
import io.swagger.v3.core.util.Json;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(of= {"id"}) // equals, hashCode 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Store{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeNum;
    private String storeName;
    private String storeLocation;
    private String storePhoneNum;
    private String grade;
    private String storeInfo;

    @Type(type="json")
    @Column(name="images",columnDefinition = "json")
    private List<MultipartFile> images;

    @ManyToOne
    @JoinColumn(name="userNum")
    private Users users;

    /*****************
    //Date 계열 일단 누락된상태
     private LocalDateTime createDate
     private LocalDateTime modificationDate
    *************************/

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


