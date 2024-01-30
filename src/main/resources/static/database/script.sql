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