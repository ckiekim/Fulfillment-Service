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
  vtotal int unsigned default 0,
  vstatus int default 0,
  vlogisId int unsigned default 0,
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

create table deliveries (
  did int unsigned auto_increment primary key,
  dcomId int unsigned not null,
  dinvId int unsigned not null,
  ddate datetime default current_timestamp,
  dstatus int default 0,
  foreign key(dcomId) references companies(cid),
  foreign key(dinvId) references invoices(vid)
) auto_increment=101 default charset=utf8;

create table purchases (
  rid int unsigned auto_increment primary key,
  rcomId int unsigned not null,
  rinvId int unsigned not null,
  rprodId int unsigned not null,
  rquantity int default 0,
  rdate datetime,
  rstatus int default 0,
  foreign key(rcomId) references companies(cid),
  foreign key(rinvId) references invoices(vid),
  foreign key(rproId) references products(pid)
) auto_increment=101 default charset=utf8;

create table inventories (
  iid int unsigned not null auto_increment primary key,
  iprodId int unsigned not null,
  ibase int default 0,
  iinward int default 0,
  ioutward int default 0,
  icurrent int default 0,
  foreign key(iproId) references products(pid)
) auto_increment=1001;

create table sales (
  lid int unsigned not null auto_increment primary key,
  linvId int unsigned not null,
  ldelId int unsigned not null,
  lmonth varchar(8) not null,
  lrevenue int default 0
) auto_increment=101;

create table records (
  rid int unsigned not null auto_increment primary key,
  rcomId int unsigned not null,
  rrole int unsigned not null,
  rmonth varchar(8) not null,
  rdata int default 0
) auto_increment=101;

insert into companies values
(1001, '이젠풀필먼트', 1, '042-345-6780', '042-345-6790', 'efs@ezen.com'),
(1002, '경기물류', 2, '031-234-5670', '031-234-5680', 'logis1@klc.com'),
(1003, '중부물류', 2, '042-567-2340', '042-567-2350', 'logis2@clc.com'),
(1004, '영남물류', 2, '052-456-2340', '052-456-2350', 'logis3@youngnam.com'),
(1005, '서부물류', 2, '062-667-2340', '062-667-2350', 'logis4@westlogis.com'),
(1006, '삼송전자', 3, '031-667-2340', '031-667-2350', 'sales@samsong.com'),
(1007, 'LS전자', 3, '02-3667-2340', '02-3667-2350', 'sales@lselec.com'),
(1008, '트윈스스포츠', 3, '053-867-2340', '053-867-2350', 'sales@twins.com'),
(1009, '신선식품', 3, '031-334-5670', '031-334-5680', 'sales@freshfood.com'),
(1010, '중부식품', 3, '042-467-2340', '042-467-2350', 'food@chungbu.com'),
(1011, '헤이마트몰', 4, '02-8765-1230', '02-8765-1240', 'op@heymart.com'),
(1012, '이젠쇼핑몰', 4, '02-3412-2580', '02-3412-2590', 'op@ezen.com'),
(1013, '한밭쇼핑몰', 4, '042-365-3650', '042-365-3660', 'op@hanbat.com');

// products.csv 파일 처리

insert into inventories(iprodId, ibase, icurrent) values
(3001, 30, 30),(3002, 30, 30),(3003, 30, 30),(3004, 30, 30),(3005, 30, 30),
(3006, 30, 30),(3007, 30, 30),(3008, 30, 30),(3009, 30, 30),(3010, 30, 30),
(4001, 50, 50),(4002, 50, 50),(4003, 50, 50),(4004, 50, 50),(4005, 50, 50),
(4006, 50, 50),(4007, 50, 50),(4008, 50, 50),(4009, 50, 50),(4010, 50, 50),
(5001, 20, 20),(5002, 20, 20),(5003, 20, 20),(5004, 20, 20),(5005, 20, 20),
(5006, 20, 20),(5007, 20, 20),(5008, 20, 20),(5009, 20, 20),(5010, 20, 20);