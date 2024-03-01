package com.itac.login.entity.image;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(of={"imageId"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@Slf4j
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long imageId;

    private String originalfilename;
    private String realpath;
    private String savefilename;

    @Builder
    public Image(String originalfilename, String realpath, String savefilename) {
        super();
        this.originalfilename = originalfilename;
        this.realpath = realpath;
        this.savefilename = savefilename;
    }

    @PostLoad
    private void postLoad() {
        // 엔티티가 로드될 때 실행할 코드 작성
        log.info("Image Entity loaded: " + this.toString());
    }
}
