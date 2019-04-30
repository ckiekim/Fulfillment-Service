create table company (
  cid int unsigned not null primary key,
  cname varchar(20) not null,
  crole int unsigned not null,
  ctel varchar(16) not null,
  cfax varchar(16),
  cmail varchar(20)
) default charset=utf8;

create table users (
  uid varchar(10) not null primary key,
  upass char(60) not null,
  uname varchar(10) not null,
  ucomId int unsigned not null,
  foreign key(ucomId) references company(cid)
) default charset=utf8;

insert into company values
(1001, '이젠풀필먼트', 1, '042-345-6780', '042-345-6790', 'efs@ezen.com'),
(1002, '경기물류', 2, '031-234-5670', '031-234-5680', 'logis1@klc.com'),
(1003, '중부물류', 2, '042-567-2340', '042-567-2350', 'logis2@clc.com'),
(1004, '영남물류', 2, '052-456-2340', '052-456-2350', 'logis3@youngnam.com'),
(1005, '서부물류', 2, '062-667-2340', '062-667-2350', 'logis4@westlogis.com'),
(1006, '삼송전자', 3, '031-667-2340', '031-667-2350', 'sales@samsong.com'),
(1007, 'LS전자', 3, '02-3667-2340', '02-3667-2350', 'sales@lselec.com'),
(1008, '현대전기', 3, '053-867-2340', '053-867-2350', 'sales@hyundae.com');