package org.example.dao;

import org.example.exception.AppException;
import org.example.model.Article;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ArticleListDAO   {

    public static List<Article> queryByUserId(Integer userId) {

        List<Article> articleList = new ArrayList<Article>();
        Connection connection = null;
       // Statement statement = null;
        PreparedStatement preparedStatement = null;
//        CallableStatement callableStatement = null;
        //自上到下的接口继承
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String  sql = "select id,title from article where  user_id =?";
            preparedStatement = connection.prepareStatement(sql);

            //设置占位符
            preparedStatement.setInt(1,userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Article article = new Article();

                //结果集取值获取到文章对象
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                articleList.add(article);
            }
            return articleList;

        }catch (Exception e){
            throw new AppException("ART001","查询文章列表出错",e);
        }finally {
            //资源释放
            DBUtil.close(connection,preparedStatement,resultSet);
        }
    }


    //删除博客
    public static int delete(String[] split) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            //为什么不用字符串拼接 += 的本质上使用StringBuilder 来做
            //StringBuilder（sql）。appen（“”）.toString();
            //会不断地产生新的字符串
            //StringBuilder 线程不安全，但效率块
            //StringBuffer 线程安全，效率不高
            StringBuilder sql = new StringBuilder("delete from article where id in(") ;

            for(int i = 0; i < split.length ;i++) {
                if( i !=0)
                    sql.append(",");
                sql.append("?");
            }
            sql.append(")");
            //为什么要tostring
            preparedStatement = connection.prepareStatement(sql.toString());

            //设置占位符
            for(int i = 0; i<split.length;i++) {
                preparedStatement.setInt(i+1,Integer.parseInt(split[i]));
            }
            return preparedStatement.executeUpdate();
        }catch (Exception e){
            throw new  AppException("ART004","文章删除操作出错",e);
        }finally {
            DBUtil.close(connection,preparedStatement);
        }
    }

    //新增那个博客的具体实现
    public static int insert(Article a) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBUtil.getConnection();

            String sql = "insert  into article (title,content ,user_id) values (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);

            //替换占位符
            preparedStatement.setString(1, a.getTitle());
            preparedStatement.setString(2, a.getContent());
            preparedStatement.setInt(3, a.getUserId());

            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new AppException("AT005", "新增文章操作出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }

    }

    //做出文章详情展示
    public static Article query(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            //根据文章id 去查询
            String sql = "select id,title,content from article where id =? ";
            preparedStatement = connection.prepareStatement(sql);
            //替换占位符
            preparedStatement.setInt(1,id);
            resultSet =  preparedStatement.executeQuery();
            //我最后要返回一个article 对象，如果没有查找到就要返回null
            Article a = null;
            //处理结果集对象
            while (resultSet.next()){
                a = new Article();
                //根据结果集设置文章属性
                a.setId(id);
                a.setTitle(resultSet.getString("title"));
                a.setContent(resultSet.getString("content"));
            }
            return a;
        } catch (Exception e) {
            throw new AppException("AT006", "查询文章详情出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement,resultSet);
        }

    }

    public static int update(Article a) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //获取数据库连接
            connection = DBUtil.getConnection();

            String sql = " update  article set title = ? ,content = ? where  id= ?";
            //根据字符串创建操作命令对象
            preparedStatement = connection.prepareStatement(sql); // 预编译的
            //替换占位符 -- a 是从前面反序列化得来的对象
            preparedStatement.setString(1,a.getTitle());
            preparedStatement.setString(2,a.getContent());
            preparedStatement.setInt(3,a.getId());

            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new AppException("AT007", "修改文章操作出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }
}
