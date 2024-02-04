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
public class Store extends BaseTimeEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeNum;
    private String storeName;
    private String storeLocation;
    private String storePhoneNum;
    private String grade;
    private String storeInfo;
    private Json images;

    @ManyToOne
    @JoinColumn(name="userNum")
    private Users users;

    /*****************
    //Date 계열 일단 누락된상태
     private LocalDateTime createDate
     private LocalDateTime modificationDate
    *************************/

    @Builder
    public Store(Long storeNum, String storeName, String storeLocation, String storePhoneNum,String grade,String storeInfo, Json images) {
        super();
        this.storeNum = storeNum;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.storePhoneNum = storePhoneNum;
        this.grade = grade;
        this.storeInfo = storeInfo;
        this.images = images;
    }

    public Long getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(Long storeNum) {
        this.storeNum = storeNum;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStorePhoneNum() {
        return storePhoneNum;
    }

    public void setStorePhoneNum(String storePhoneNum) {
        this.storePhoneNum = storePhoneNum;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(String storeInfo) {
        this.storeInfo = storeInfo;
    }

    public Json getImages() {
        return images;
    }

    public void setImages(Json images) {
        this.images = images;
    }

    //
//    //계정이 갖고있는 권한 목록은 리턴
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        Collection<GrantedAuthority> collectors = new ArrayList<>();
//        collectors.add(() -> {
//            return "계정별 등록할 권한";
//        });
//
//        //collectors.add(new SimpleGrantedAuthority("Role"));
//
//        return collectors;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.getUserPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.getUserEmail();
//    }
//
//    //계정이 만료되지 않았는지 리턴 (true: 만료 안됨)
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    //계정이 잠겨있는지 않았는지 리턴. (true: 잠기지 않음)
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    //비밀번호가 만료되지 않았는지 리턴한다. (true: 만료 안됨)
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    //계정이 활성화(사용가능)인지 리턴 (true: 활성화)
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
