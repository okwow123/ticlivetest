package com.itac.login.entity.review;

import com.itac.login.entity.store.Store;
import com.itac.login.entity.user.Users;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
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
    private String grade;

    @Column
    private String createdDate;

    @ManyToOne
    @JoinColumn(name="userNum")
    private Users users;

    @ManyToOne
    @JoinColumn(name="storeNum")
    private Store store;

    @Type(type="json")
    @Column(name="images",columnDefinition = "json")
    private List<MultipartFile> images;

    @Builder
    public Review(Long reviewNum,String grade) {
        super();
        this.reviewNum = reviewNum;
        this.grade = grade;
    }
}
