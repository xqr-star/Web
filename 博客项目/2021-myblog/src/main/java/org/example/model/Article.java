package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Article {
//    id int primary key auto_increment,
//    title varchar (20) not null ,
//    context mediumtext ,
//    create_time timestamp default CURRENT_TIMESTAMP,
//    view_count int default 0,
//    user_id int,
//    foreign key(user_id) references user(id)

    private Integer id;
    private String title; //varchar(20) 对应的
    private String content;//mediumtext
    private Date creatTime; // 是带时分秒的
    private Integer viewCount;
    private Integer userId;
}
