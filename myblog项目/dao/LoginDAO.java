package org.example.dao;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date; // resultSet.getDate("birthday") 用的是util 这个的包 不是sql 的

public class LoginDAO {

    public static User query(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBUtil.getConnection();
            String sql = "select id, username, password, nickname, sex, " +
                    "birthday, head from user where  username =?";
            preparedStatement = connection.prepareStatement(sql);

            //设置占位符
            //吧第一个占位符设置成页面请求来的
            preparedStatement.setString(1,username);


            resultSet = preparedStatement.executeQuery();

            User user = null;//没有结果返回集null
            while (resultSet.next()) {
                user = new User(); // 有结果集的时候new 一个

                //设置User 的值
                user.setId(resultSet.getInt("id"));
                user.setUsername(username);
                user.setPassword(resultSet.getString("password"));
                user.setNickname(resultSet.getString("nickname"));
                user.setSex(resultSet.getBoolean("sex"));
                //日期的使用一般用java.util.Data (年月日时分秒)
                //rs.getDate() 返回值是java.sql.Date(时分秒）
                //rs.getTimeStamp  java .sql.date
                //resultSet.getDate("birthday").getTime()

                java.util.Date birthday = resultSet.getDate("birthday");
                if( birthday !=null)
                    user.setBirthday(new Date( birthday.getTime()));
                user.setHead(resultSet.getString("head"));
            }
            return  user;
        }catch(Exception e){
            //上面的代码肯出现的错误在这里捕获
            throw  new AppException("LOG001","查询用户操作出错",e);
        }finally{
            DBUtil.close(connection,preparedStatement,resultSet);
        }
    }
}
