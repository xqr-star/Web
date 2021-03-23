drop database if exists servlet_blog;

create database servlet_blog character  set utf8mb4;

use servlet_blog;
create table user(
  id int primary key auto_increment comment'账号',
  username varchar (20) not null unique ,
  password varchar (20) not null ,
  nickname varchar (20) ,
  sex bit ,
  birthday date ,  /* 现在这个只有年月日 timestamp 精确到时分秒*/
  head varchar (50)
);

create table article(
    id int primary key auto_increment,
    title varchar (20) not null ,
    context mediumtext ,
    create_time timestamp default CURRENT_TIMESTAMP,
    view_count int default 0,
    user_id int,
    foreign key(user_id) references user(id)
);

insert  into user(username, password) values ('a','1');
insert  into user(username, password) values ('b','2');
insert  into user(username, password) values ('c','3');


insert into article(title,  user_id) values ('博客学习','1');
insert into article(title, user_id) values ('servlet学习','2');
insert into article(title,   user_id) values ('Web学习','3');
insert into article(title,  user_id) values ('tomcat学习','1');


select  id, username, password, nickname, sex, birthday, head from  user where username = 'a';
select id,title from article where user_id=1;