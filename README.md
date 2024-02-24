웹,앱 프로젝트

링크 : https://dalgom.app

![image](https://github.com/okwow123/itca/assets/11327395/9e951cab-ab76-496f-a7c0-e6f260500cf5)

![image](https://github.com/okwow123/itca/assets/11327395/4a9cc5d2-a155-418b-b85a-e18d2917a72e)


컨셉 : 디저트,커피 위주의 캐치테이블 버전

URL : 20.214.108.168

젠킨스 접속정보 : http://20.214.108.168:9090/

도메인 www.dalgom.app / dalgom.app

https 인증서 추가 설치 요청중...


프로젝트 개발 참여자 및 역할

김민제님 -> 환경구성

권영환님 -> 백엔드 개발

안효진님 -> 백엔드 개발(사진 업로드) 및 앱 개발

최태형님 -> 백엔드 개발

이진원님 -> 백엔드 개발 및 다이어그램 설계


![image](https://github.com/okwow123/itca/assets/11327395/9d4f5286-c5df-4340-9203-7dfb4fa5929a)

안효진님 DB설계
![image](https://github.com/okwow123/itca/assets/11327395/e94714b6-1290-46da-892a-aac991425b93)

최태형님 SQL 수정 

--회원테이블

```
CREATE TABLE public."member" (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	email varchar(200) NOT NULL,
	pwd varchar(200) NOT NULL,
	last_login_time timestamptz NULL DEFAULT now(),
	register_time timestamptz NULL DEFAULT now(),
	update_time timestamptz NULL DEFAULT now(),
	CONSTRAINT member_primary PRIMARY KEY (id),
	CONSTRAINT member_un UNIQUE (email)
);


--게시판 테이블
CREATE TABLE public."forum" (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	subject varchar(300) ,
	contents text,
	writer  varchar(200) not NULL,
	create_time timestamptz NULL DEFAULT now(),
	update_time timestamptz NULL DEFAULT now(),
	CONSTRAINT forum_primary PRIMARY KEY (id)
);






-- 2024. 02. 03 
create sequence user_userNum_seq
	increment 1
	start 1
	minvalue 1
	maxvalue 9223372036854775807
	cache 1;

create sequence store_storeNum_seq
	increment 1
	start 1
	minvalue 1
	maxvalue 9223372036854775807
	cache 1;

create sequence review_reviewNum_seq
	increment 1
	start 1
	minvalue 1
	maxvalue 9223372036854775807
	cache 1;

create sequence reservation_reservationNum_seq
	increment 1
	start 1
	minvalue 1
	maxvalue 9223372036854775807
	cache 1;

-- 회원테이블
create table public."user"(
	userNum int4 not null default nextval('user_usernum_seq'::regclass),
	userEmail varchar(20) not null unique,
	userPassword varchar(20) not null,
	-- 권한 여부(사용자 0, 가맹점 1)
	auth boolean not null default false,
	constraint user_pk primary key (userNum)
);

create table public."store"(
	storeNum int4 not null default nextval('store_storeNum_seq'::regclass),
	storeName varchar(20) not null,
	storeLocation varchar(20) not null,
	storePhoneNum varchar(20) not null,
	-- 평점 저장여부
	grade varchar(5) not null default 0,
	storeInfo varchar(200),
	-- 텍스트 json 데이터
	images json,
	-- images jsonb, > 바이너리 json 데이터
	createDate date not null,
	modificationDate date,
	userNum int4 not null,
	constraint store_pk primary key (storeNum)
);

create table public."review"(
	reviewNum int4 not null default nextval('review_reviewNum_seq'::regclass),
	writer varchar(20) not null,
	reviewContent varchar(1000),
	-- reviewContent text, > 가변 길이 문자열
	grade varchar(5) not null,
	-- 텍스트 json 데이터
	images json,
	-- images jsonb, > 바이너리 json 데이터
	createDate date not null,
	constraint review_pk primary key (reviewNum)
);

create table public."reservation"(
	reservationNum int4 not null default nextval('reservation_reservationNum_seq'::regclass),
	numberOfPerson int4 not null,
	reservationDate date not null,
	reservationTime time not null,
	reservationUserNum int4 not null,
	constraint reservation_pk primary key (reservationNum)
);

```





권영환님 초기 아키텍처 설계
![image](https://github.com/okwow123/itca/assets/11327395/5ac45d4b-dad6-4e3e-ba7b-0a064ab5d262)


최태형님 초기 아키텍처 설계
![image](https://github.com/okwow123/itca/assets/11327395/ce2b2266-19ac-4111-97ad-f73556a38905)


이진원님 다이어그램 설계
![image](https://github.com/okwow123/itca/assets/11327395/8ccec4e7-7286-45d1-bfec-949784a664b0)

![image](https://github.com/okwow123/itca/assets/11327395/1fcf548e-42c4-4854-95f2-96293f56244c)


![image](https://github.com/okwow123/itca/assets/11327395/458d24bf-b94a-479d-9fe3-39a1a2f1ce77)


화면설계
![image](https://github.com/okwow123/itca/assets/11327395/5e6e17da-f9ba-4074-883c-1ead8081c0fb)

![image](https://github.com/okwow123/itca/assets/11327395/6dd3c50d-f720-4bdc-965a-1e96a204b37f)

![image](https://github.com/okwow123/itca/assets/11327395/93dff479-082f-44b7-a4de-b91ab326ff22)
![image](https://github.com/okwow123/itca/assets/11327395/8696f49c-0e72-438c-ae84-fb1c94d5f66c)

![image](https://github.com/okwow123/itca/assets/11327395/05d61065-8af5-4162-801e-582db2e18701)

![image](https://github.com/okwow123/itca/assets/11327395/bc68b046-0c3e-4525-b828-e1fe3bd28463)
![image](https://github.com/okwow123/itca/assets/11327395/a87b38c7-85ab-4861-952f-7335a5a771a5)
[화면설계서_캐치테이블 (3).pptx](https://github.com/okwow123/itca/files/14155466/_.3.pptx)



- 젠킨스 셋팅
![image](https://github.com/okwow123/itca/assets/11327395/a039f30b-b25b-4500-a9a0-449f7ba209e3)

![image](https://github.com/okwow123/itca/assets/11327395/c0e69976-add5-48ff-9c61-2d8e70d5da98)



- https 인증서 추가
- 
![image](https://github.com/okwow123/itca/assets/11327395/d09f976b-9cc7-41f9-90e8-ab7c936eb0e1)


- 웹 도커 셋팅

![image](https://github.com/okwow123/itca/assets/11327395/6f81edc8-ed55-4c26-bcfd-76222a0750a5)


- nginx 셋팅

  ![image](https://github.com/okwow123/itca/assets/11327395/bd058996-b66b-4fe4-bebe-e77fb2532dd5)




- build.gradle 셋팅 +JPA +QUERY DSL

```
buildscript {
    ext {
        queryDslVersion = "5.0.0"
    }
}

plugins {
    id 'org.springframework.boot' version '2.6.4'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

group = 'com.itac.login'
version = '0.3.0'
sourceCompatibility = '11'
archivesBaseName = 'login'

ext['log4j2.version'] = '2.17.1'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation fileTree(dir: './src/main/libs', include: '**/*.jar')

    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-elasticsearch'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
    implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310'
    implementation 'org.springdoc:springdoc-openapi-ui:1.6.6'


    implementation 'nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect'
    implementation 'org.springframework.boot:spring-boot-starter-batch'
    runtimeOnly 'org.postgresql:postgresql'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    implementation 'com.opencsv:opencsv:5.3'

    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'

    implementation 'com.google.code.gson:gson:2.9.0'

    // QueryDSL
    implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
    annotationProcessor(
            "javax.persistence:javax.persistence-api",
            "javax.annotation:javax.annotation-api",
            "com.querydsl:querydsl-apt:${queryDslVersion}:jpa")

    implementation group: 'org.hibernate', name: 'hibernate-spatial', version: '5.5.0.Final'
    implementation 'org.springframework.boot:spring-boot-starter-test'

    implementation group: 'org.apache.poi', name: 'poi', version: '3.17'
    implementation group: 'org.apache.poi', name: 'poi-ooxml', version: '3.17'

    implementation 'com.vladmihalcea:hibernate-types-52:2.19.2'
}

// QueryDSL
sourceSets {
    main {
        java {
            srcDirs = ["$projectDir/src/main/java", "$buildDir/generated"]
        }
    }
}

tasks.named('test') {
    useJUnitPlatform()
}
```
