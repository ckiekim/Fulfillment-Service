create table companies (
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
  foreign key(ucomId) references companies(cid)
) default charset=utf8;

create table products (
  pid int unsigned not null primary key,
  pname varchar(20) not null,
  pprice int unsigned default 0,
  pstock int unsigned default 0,
  pimage varchar(20) not null,
  pcategory varchar(20) not null
) default charset=utf8;

create table invoices (
  vid int unsigned not null auto_increment primary key,
  vname varchar(20) not null,
  vtel varchar(16) not null,
  vaddr varchar(40) not null,
  vcomId int unsigned not null,
  vdate datetime default current_timestamp,
  vtotal int unsigned not null,
  foreign key(vcomId) references companies(cid)
) auto_increment=10001 default charset=utf8;

create table soldProducts (
  sinvId int unsigned not null,
  sprodId int unsigned not null,
  squantity int unsigned default 0,
  primary key(sinvId, sprodId),
  foreign key(sinvId) references invoices(vid),
  foreign key(sprodId) references products(pid)
);

insert into companies values
(1001, '이젠풀필먼트', 1, '042-345-6780', '042-345-6790', 'efs@ezen.com'),
(1002, '경기물류', 2, '031-234-5670', '031-234-5680', 'logis1@klc.com'),
(1003, '중부물류', 2, '042-567-2340', '042-567-2350', 'logis2@clc.com'),
(1004, '영남물류', 2, '052-456-2340', '052-456-2350', 'logis3@youngnam.com'),
(1005, '서부물류', 2, '062-667-2340', '062-667-2350', 'logis4@westlogis.com'),
(1006, '삼송전자', 3, '031-667-2340', '031-667-2350', 'sales@samsong.com'),
(1007, 'LS전자', 3, '02-3667-2340', '02-3667-2350', 'sales@lselec.com'),
(1008, '현대전기', 3, '053-867-2340', '053-867-2350', 'sales@hyundae.com'),
(1009, '경기종합상사', 3, '031-334-5670', '031-334-5680', 'sales@kyongki.com'),
(1010, '중부식품', 3, '042-467-2340', '042-467-2350', 'food@chungbu.com'),
(1011, '헤이마트몰', 4, '02-8765-1230', '02-8765-1240', 'op@heymart.com'),
(1012, '트윈스스포츠', 4, '02-3412-2580', '02-3412-2590', 'op@twinssports.com'),
(1013, '신선식품몰', 4, '042-365-3650', '042-365-3660', 'op@freshfood.com');