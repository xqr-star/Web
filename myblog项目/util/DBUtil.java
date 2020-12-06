package org.example.util;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.exception.AppException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//提供一个工具类 连接数据库
public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/servlet_blog?" +
            "user=root&password=13467289102" +
            "&useUnicode=true&characterEncoding=UTF-8&" +
            "useSSL=false";

    private static final DataSource DS = new MysqlDataSource();
    //静态变量引用不能改变，处在方法区

    /**
     * 工具类提供的数据库JDBC 操作
     * 不足 1.static 代码块出现错误：NoClassDefFoundError : 类已经找到，但是类加载失败，无法运行
     *          ClassNoFundException : 找不到类
     *      2. 多线程讲完之后，可能会采取双重校验锁的单利模式，来创建DataSource
     *      3.工具类的设计不是最优的，数据库框架ORM框架 Mybatis，都是采用模板模式设计的。
     *      静态工具类提供的方式，无法采用模板模式，主要是很多东西实现不了太难了还没学
     */

    //为什么要写静态代码块？随着类的加载而执行，而且只执行一次
    static {
        ((MysqlDataSource)DS).setUrl(URL); // 强转
    }


    //讲了一堆做出的妥协。。。不知道在讲什么
    //返回类型是一个Connection 对象
    public static Connection getConnection(){
        try {
            return DS.getConnection();
        } catch (SQLException e) {
            //抛出自定义异常
            throw   new AppException("DB001","获取数据库连接失败",e);
        }
    }

    public static void close(Connection c, Statement s){
        close(c,s,null);
    }

    public static void close(Connection c, Statement s, ResultSet r){
        try {
            if(r != null) {
                r.close();
            }
            if(s != null) {
                s.close();
            }
            if(c != null) {
                c.close();
            }
        } catch (Exception e) {
            throw new AppException("Login002","数据库释放资源错误",e); //这个是怎么用的
        }

    }

}
