DROP TABLE POST_ATTACHMENT;
DROP TABLE POST;
DROP TABLE BLOG;

create table board_attachment(
  attachment_id number primary key,
  board_id number,
  original_name varchar2(100),
  file_size number,
  update_date number,
  constraint FK_board_id foreign key(board_id) references board(board_id)
);
alter table board_attachment drop constraint FK_board_id;
alter table board_attachment add constraint FK_board_attchement foreign key(board_id)
references board(board_id) on delete cascade;

create table board(
  board_id number primary key,
  title varchar2(256),
  writer varchar2(50 byte),
  password varchar2(256),
  content varchar2(1024),
  read_cnt number,
  reg_date date default sysdate,
  update_date date default sysdate
);

create sequence board_seq;
drop sequence board_seq;
CREATE TABLE BLOG(
  blog_id number primary key,
  title varchar2(256),
  owner varchar2(50 BYTE) ,
  description varchar2(1024),
  image	BLOB,
  reg_date	DATE default sysdate,
  update_date	DATE default sysdate
);

CREATE SEQUENCE BLOG_SEQ;

CREATE TABLE POST(
  POST_ID	NUMBER PRIMARY KEY,
  BLOG_ID 	NUMBER REFERENCES BLOG(BLOG_ID),
  TITLE		VARCHAR2(256 BYTE),
  READ_CNT	NUMBER default 0,
  CONTENT	CLOB,
  REG_DATE	DATE default sysdate,
  UPDATE_DATE	DATE default sysdate
);

CREATE SEQUENCE POST_SEQ;

CREATE TABLE POST_ATTACHMENT(
  ATTACHMENT_ID NUMBER PRIMARY KEY,
  POST_ID NUMBER REFERENCES POST(POST_ID),
  ORIGINAL_NAME	VARCHAR2(128 CHAR),
  FILE_SIZE	NUMBER,
  REG_DATE	DATE default sysdate
);

CREATE SEQUENCE POST_ATTACH_SEQ;

CREATE TABLE BOARD_ATTACHMENT(
  ATTACHMENT_ID NUMBER PRIMARY KEY,
  BOARD_ID NUMBER REFERENCES BOARD(BOARD_ID),
  ORIGINAL_NAME	VARCHAR2(128 CHAR),
  FILE_SIZE	NUMBER,
  REG_DATE	DATE default sysdate
);

CREATE SEQUENCE BOARD_ATTACH_SEQ;
SET FOREIGN_KEY_CHECKS = 0;
select * from board_attachment;
select * from blog;
select * from board;
truncate table board;
drop table board cascade constraint;

insert into board(board_id, title, writer, password, read_cnt, content) values(board_seq.nextval, 'test1', 'hong', '111111', 0, 'test1');

select * from member;


commit;
