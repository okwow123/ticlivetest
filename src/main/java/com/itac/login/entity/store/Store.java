package com.itac.login.entity.store;

import com.itac.login.entity.BaseTimeEntity;
import com.itac.login.entity.user.Users;
import io.swagger.v3.core.util.Json;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@EqualsAndHashCode(of= {"id"}) // equals, hashCode 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Store extends BaseTimeEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storenum;
    private String storename;
    private String storelocation;
    private String storephonenum;
    private String grade;
    private String storeinfo;
    private String images;

    private Long usernum;

    /*****************
     //Date 계열 일단 누락된상태
     private LocalDateTime createDate
     private LocalDateTime modificationDate
     *************************/

    @Builder
    public Store(String storename, String storelocation, String storephonenum, String grade, String storeinfo, String images, Long usernum) {
        this.storename = storename;
        this.storelocation = storelocation;
        this.storephonenum = storephonenum;
        this.grade = grade;
        this.storeinfo = storeinfo;
        this.images = images;
        this.usernum = usernum;
    }
}
