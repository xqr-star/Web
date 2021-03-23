package org.example.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
//这里做的就是数据库表到java 实体类之间的映射关系
public class User {
    private Integer id; // 为什么不适用int基本数据类型因为你new对象的时候，直接就会有基本数据类型的默认值，为了避免一些不必要的麻烦操作
    private String username;
    private String password;
    private String nickName;
    private Boolean sex;
    private Date birthday; //util.Date;
    private String head;
}

