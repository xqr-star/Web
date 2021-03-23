package org.example.dao;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

//分析了半天，其实想说的就是代码的功能要单一化
//你是做什么就做什么，不要在这里涉及到什么用户不存在
//抛出异常等登录页面做的事情
public class LoginDao {

    public static User query(String username) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        //获取链接
        //创建操纵命令对象
        //执行sql
        //处理结果集
        //释放资源

        try{
            c = DBUtil.getConnection(); // 这里就体现处理啊了创建DBUtil的意义，
            // 不说一个操作执行完以后，由去new对象，之前对象就不能用了

            String sql = "select  id, username, password, nickname, sex, birthday, head " +
                    "from  user where username =?";

            ps = c.prepareStatement(sql);
            //设置占位符

            ps.setString(1,username);


            rs = ps.executeQuery();
            User user  = null;
            while (rs.next()){
                //前面定义成null 有结果及才new的原因
                //loginServlet里面 if(user == null){ 如果没用这个用户，
                //还new了这个对象，前面的永远都不可能是null

                user = new User();
                //这是user的值

                user.setId(rs.getInt("id"));
                user.setUsername(username);
                user.setPassword(rs.getString("password"));
                user.setNickName(rs.getNString("nickname"));
                user.setSex(rs.getBoolean("sex"));

                //关于日期的使用 java.util.Date 一般是年月日时分秒的
                //rs 后面对应的是数据库的字段
                //rs.getDate()返回值是java.sql.Date 时分秒的
                //rs.getTimeStamp() 返回值是java.sql.TimeStamp 年月日时分秒

                //表示获取的是从1966到今天的事件
                //Java Date类的getTime()方法返回自1970年1月1日GTM的00:00:00(由Date对象表示)以来的毫秒数。
                //但我的sql的date 所以要new一个对象

                //因为数据库里面的birthday 没有设置是空的，所以会出现空指针异常。

                java.sql.Date  birthday =rs.getDate("birthday");
                if(birthday !=null)
                    user.setBirthday(new Date(birthday.getTime()));

//                user.setBirthday(new Date( rs.getDate("birthday").getTime()));
                user.setHead(rs.getString("head"));

            }
            return user;
        }catch (Exception e){

            throw new AppException("LG001","查询用户操作出错",e);
        }finally {
            DBUtil.close(c,ps,rs);
        }
    }
}
