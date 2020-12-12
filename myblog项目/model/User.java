package org.example.model;


import java.util.Date;

//@Getter
//@Setter
public class User {
    //和数据库表产生连接
    private Integer id ;
    // 为什么用Integer ? 否则默认由0 会产生一些不必要的麻烦
    private String username;
    private String password;
    private String nickname;
    private Boolean sex; // bit 对应的
    private Date birthday; //用的是java .util 包下面的
    private  String head;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
