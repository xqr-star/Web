package org.example.util;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.exception.AppException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//提供一个工具类 连接数据库
public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/servlet_blog?" +
            "user=root&password=13467289102" +
            "&useUnicode=true&characterEncoding=UTF-8&" +
            "useSSL=false";

    private static final DataSource DS = new MysqlDataSource();

    //为什么要写静态代码块？随着类的加载而执行，而且只执行一次
    static {
        ((MysqlDataSource)DS).setUrl(URL); // 强转
    }

    //返回类型是一个Connection 对象
    public static Connection getConnection(){
        try {
            return DS.getConnection();
        } catch (SQLException e) {
            //抛出自定义异常
            throw   new AppException("DB001","获取数据库连接失败",e);
        }
    }


}
