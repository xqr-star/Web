package org.example.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.exception.AppException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//连接到数据库的工具类

/**
 * 工具类提供JDBC操作
 * 不足：1.satic代码块出现错误 NoClassFoundError :类可以找到，但是类加载失败，无法运行
 *                          ClassNotFound:找不到类
 *       2.多线程之后，采用双重校验锁单例模式创建DataSource对象
 *       3.工具类设计不是最优的，没有采用模板模式设计
 *       数据库ORM框架 Mybatis是用模板模式做的
 */
public class DBUtil {
    private static final String URL="jdbc:mysql://localhost:3306/servlet_blog?user=root&password=13467289102&useUnicode=true&characterEncoding=UTF-8&useSSL=false";

    //创建连接池  连接对象 享元模式 -- 使用饿汉式的单例模式 进行改进
    private static final DataSource DS = new MysqlDataSource();
    //它的构造方法写成了一个静态代码块
    static {
        ( (MysqlDataSource)DS).setUrl(URL);//强制转换
    }

    //采用静态的方法，只会加载一次，没用采用模板模式来做，说是很复杂。
    public static Connection getConnection(){
        try {
            return DS.getConnection();
        } catch (SQLException e) {
            //抛出自定义异常
            throw new AppException("DB001","获取数据库连接失败",e);
        }
    }

    public static void close(Connection c, Statement s){
        close(c,s,null);
    }
    public static void close(Connection c, Statement s, ResultSet r){

        try {
            if(r != null){
                r.close();
            }
            if(s != null) {
                s.close();
            }
            if (c != null){
                c.close();
            }
        } catch (SQLException e) {
            throw  new AppException("DB002","数据库释放资源出错",e);
        }

    }





}