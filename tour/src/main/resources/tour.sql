DROP TABLE GALLERY;
CREATE TABLE GALLERY(
	gallery_id 	NUMBER PRIMARY KEY,
	owner   	VARCHAR2(50),
	password 	VARCHAR2(50),
	title   	VARCHAR2(256 CHAR),
	description VARCHAR2(512 CHAR),
	read_cnt    NUMBER DEFAULT 0,
	reg_date    DATE DEFAULT SYSDATE,
	update_date DATE DEFAULT SYSDATE
);

CREATE SEQUENCE GALLERY_SEQ;

DROP TABLE IMAGE;
CREATE TABLE IMAGE(
	image_id 	NUMBER PRIMARY KEY,
	gallery_id 	NUMBER REFERENCES GALLERY(gallery_id),
	orginal_name VARCHAR2(128 CHAR),
	file_size   NUMBER,
	mime_type   VARCHAR(56),
	reg_date    DATE DEFAULT SYSDATE
);

CREATE SEQUENCE IMAGE_SEQ;

drop table image;
drop sequence image_seq;

drop table gallery;
drop sequence gallery_seq;