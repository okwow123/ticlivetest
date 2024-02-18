package com.itac.login.entity.review;

import com.itac.login.entity.StringListConverter;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.user.Users;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of={"reviewNum"})// equals, hashCode 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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
    private String createdDate;

    @ManyToOne
    @JoinColumn(name="userNum")
    private Users users;

    @ManyToOne
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
