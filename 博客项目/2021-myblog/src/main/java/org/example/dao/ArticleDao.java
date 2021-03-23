package org.example.dao;


import org.example.exception.AppException;
import org.example.model.Article;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//做的是文章列表查询操作
public class ArticleDao {

    public static List<Article> queryByUserId(Integer id) {
        List<Article> articles = new ArrayList<>();//这里直接new 而不hi是像User查询先null 判断有才new ，即使没有返回一个 空的列表
        Connection c = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        try{
            c = DBUtil.getConnection();
            String sql = "select id,title from article where user_id=?;";
            p = c.prepareStatement(sql);

            //设置占位符
            p.setInt(1,id);
            //执行sql
            rs = p.executeQuery();
            while (rs.next()) {
                // 结果集移动到下一行，返回其是否有值
                //一行结果集转换成一个java对象  // 多行返回list包裹对象
                Article article = new Article();
                //每一个结果集都是一行 然后再对应的一行里面去int String 之类的列值信息
                //从结果及取值设置到文章对象
                article.setId(rs.getInt("id"));
                article.setTitle(rs.getString("title"));
                //然后再将文章对象放到list里面去
                articles.add(article);
            }
            return articles;
        }catch (Exception e){
            throw new AppException("AER001","查询文章列表出错",e);
        }finally {
            DBUtil.close(c,p,rs);
        }

    }

    //删除文章的数据库操作
    public static int delete(String[] split) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            //数据库获取连接创建操作命令对象
            c = DBUtil.getConnection();
            //需要把string数组转换成int数组
            //为什么不打 ？ 直接替换为一个字符串来做
            //就是因为  ps.setString 会自己加上单引号  delete from article where id in (“”); 就会编程这样了
            //但我要的是elete from article where id in ();
            StringBuilder sql = new StringBuilder("delete from article where id in(") ;

            for(int i = 0; i < split.length ;i++) {
                if( i !=0)
                    sql.append(",");
                sql.append("?");
            }
            sql.append(")");
            ps = c.prepareStatement(sql.toString());

            //设置占位符的值
            for(int i = 0; i < split.length;i++){
                ps.setInt(i+1,Integer.parseInt(split[i])); // 注意 ？的下标是从1开始的
            }


            //执行sql
            return ps.executeUpdate();// 这里就是执行sql
        }catch ( Exception e ){
            throw new AppException("ART004","文章删除操作出错",e);
        }finally {
            DBUtil.close(c,ps);
        }

    }

    public static int insert(Article article) {
        Connection c = null;
        PreparedStatement ps = null;
        try{
            c = DBUtil.getConnection();
            String sql = "insert into article (title,content,user_id) values (?,?,?)";
            ps = c.prepareStatement(sql);
            //替换占位符
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setInt(3, article.getUserId());
            return ps.executeUpdate();

        }catch (Exception e){
            throw new AppException("AT005", "新增文章操作出错", e);
        }finally {
            DBUtil.close(c,ps);
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
            while (resultSet.next()){  // 循环处理每一行数据，一行变成一个java对象 // 多行返回list包裹对象
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
