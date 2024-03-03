package com.itac.login.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itac.login.entity.BaseTimeEntity;
import com.itac.login.entity.reservation.Reservation;
import com.itac.login.entity.store.Store;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(of= {"usernum"}) // equals, hashCode 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@ToString(exclude ={"reservations","stores"})
@Slf4j
public class Users extends BaseTimeEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNum;
    private String userEmail;
    private String userPassword;
    private String auth;

    @Builder
    public Users(Long userNum, String userEmail, String userPassword, String auth) {
        super();
        this.userNum = userNum;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.auth = auth;
    }

    @OneToMany(mappedBy = "users",fetch=FetchType.LAZY)
    @JsonBackReference //순환참조 방지
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "users",fetch=FetchType.LAZY)
    @JsonBackReference //순환참조 방지
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Store> stores;

    public Long getUserNum() {
        return userNum;
    }

    public void setUserNum(Long userNum) {
        this.userNum = userNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String isAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    //계정이 갖고있는 권한 목록은 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "계정별 등록할 권한";
        });

        //collectors.add(new SimpleGrantedAuthority("Role"));

        return collectors;
    }

    @Override
    public String getPassword() {
        return this.getUserPassword();
    }

    @Override
    public String getUsername() {
        return this.getUserEmail();
    }

    //계정이 만료되지 않았는지 리턴 (true: 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 않았는지 리턴. (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지 리턴한다. (true: 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화(사용가능)인지 리턴 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
