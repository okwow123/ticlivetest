package com.itac.login.entity.review;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itac.login.entity.StringListConverter;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.user.Users;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of={"reviewNum"})// equals, hashCode 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Slf4j
@ToString
@Entity
public class Review implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNum;

    @Column
    private String reviewContent;

    @Column
    private Float grade;

    @Column
    private String createDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonManagedReference // 순환참조 방지
    @JoinColumn(name="userNum")
    private Users users;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonManagedReference // 순환참조 방지
    @JoinColumn(name="storeNum")
    private Store store;

    @Column(name="images")
    @Convert(converter = StringListConverter.class)
    private List<String> images = new ArrayList<>();

    @Builder
    public Review(Long reviewNum,Float grade) {
        super();
        this.reviewNum = reviewNum;
        this.grade = grade;
    }
}
