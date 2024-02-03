--회원테이블
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


--blockchain wallet
CREATE TABLE public."wallet" (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	email varchar(200) NOT NULL,
    wallet_address varchar(200) NOT NULL,
    wallet_password varchar(200) NOT NULL,
	last_view_time timestamptz NULL DEFAULT now(),
	CONSTRAINT wallet_primary PRIMARY KEY (id),
	CONSTRAINT wallet_un UNIQUE (email)

);



--blockchain history
--모든 데이터 히스토리 관리
CREATE TABLE public."history" (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	history varchar(max) NULL,
	create_time timestamptz NULL DEFAULT now(),
	CONSTRAINT history_primary PRIMARY KEY (id)
);


--coin_price
CREATE TABLE public."price_information" (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	cName varchar(200) NOT NULL,--coin name
    cPrice float NOT NULL,
	create_time timestamptz NULL DEFAULT now(),
	CONSTRAINT  price_information_primary PRIMARY KEY (id),
);



--blockchain blog information
-- 1 person <-> 1 blog owner <-> 1 minting
--

CREATE TABLE public."blog_block" (
	id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
	wallet_address varchar(200) NOT NULL,
	mintName varchar(200) NOT NULL,
	mintTotalCount int NOT NULL,
    create_time timestamptz NULL DEFAULT now(),
	CONSTRAINT  price_information_primary PRIMARY KEY (id),
);




--register /login
--forum


--update member set pwd='$2a$10$wnxgUHTCVY2VTw22q5IC/uFLKG7FK28rpPBXp0RDOiseESFES.l.G'

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
	usreNum int4 not null,
	constraint sotre_pk primary key (storeNum)
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
