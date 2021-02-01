package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.example.model.Result;
import org.example.model.Weight;
import org.example.util.Index;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//根据前端的请求路径，定义后端的服务路径
@WebServlet(value = "/search",loadOnStartup =1 )  // int loadOnStartup() default -1; -1 表示不由tomcat启动的时候再
//是否在启动的时候初始化操作   默认-1 tomcat 启动不初始化，第一次请求才初始化
//看ajax请求里面的url
//继承一个抽象类但没有重写所有方法？
public class SearchServlet  extends HttpServlet {
    //所以在这里初始化操作tomcat 初始化的时候，new Servlet对象的时候就初始化
    @Override
    public void init(ServletConfig config) throws ServletException {
        Index.buildForwardIndex();
        Index.buildInvertedIndex();
        System.out.println("init complete");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //初始化操作，先构建正排索引，再根据正排构建倒排--这样的操作会让每一次都构建--耗时长
        //所以在init里面写
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json"); //ajax请求，响应为json格式

        //构造返回给前端的内容 ，使用一个对象，之后将对象序列化为json字符串
        Map<String,Object> map = new HashMap<>();

        //解析请求数据 做搜索业务的处理
        String query = req.getParameter("query");//通过query键获取内容，这是搜索框的内容//键再前端约定过
        List<Result>  results = new ArrayList<>();
        try{
            //根据搜索内容处理搜索业务

            //校验请求数据：搜索内容
            if(query == null || query.trim().length() == 0) {
                //如果是空字符串或者是实际长度没有
                map.put("ok",false);
                map.put("message","搜索内容为空");
            }else {
                //1. 解析搜索内容 ，根据搜索内容分词 遍历每个分词
                for(Term t : ToAnalysis.parse(query).getTerms()){
                    String fenCi = t.getName();//搜索的分词
                    //如果分词是没有意义的分词，就跳过它
                    //TODO 定义一个数组包含没有意义的关键词 如果当前的分词在数组里面找到了就跳过 if(   isValid(fenCi) )   返回的布尔值是false就跳过这个分词 continue;

                    //2.对每一个分词在倒排中查找对应的文档（多个 -- 一个关键字对应多个文档）
                    List<Weight> weights = Index.get(fenCi);


                    //3.一个文档转换为一个result（不同分词可能存在相同文档，需要合并） 遍历文档
                    for(Weight w : weights){
                        //先转换weight为result
                        Result r = new Result();
                        r.setId(w.getDoc().getId());
                        r.setTitle(w.getDoc().getTitle());
                        r.setWeight(w.getWeight());
                        r.setUrl(w.getDoc().getUrl());

                        //文档内容超过60长度(字符串) 隐藏为...
                        String content = w.getDoc().getContent();
                        r.setDesc(content.length() <= 60 ? content:content.substring(0,60)+"...");

                        //TODO 合并操作，先不做--暂时不做 合并操作需要List<Result>
                        // 1.找已有的，判断doc id相同，直接把已有的权重+现有的权重
                        // 2. 不存在就直接放进去
                        results.add(r);
                    }
                }



                //4.在合并完成后，对list包裹的result进行排序 (根据权重降序排序)
               results.sort(new Comparator<Result>() {
                   @Override
                   public int compare(Result o1, Result o2) {
                       //return Integer.compare(o1.getWeight(),o2.getWeight());//权重升序
                       return Integer.compare(o2.getWeight(),o2.getWeight());//权重降序
                   }
               });
                //lambda
               /* results.sort((o1,o2)->{
                    return Integer.compare(o2.getWeight(),o2.getWeight());//权重降序
                });*/
                //lambda 进阶
               /* results.sort(((o1, o2) -> Integer.compare(o2.getWeight(),o2.getWeight())));*/
                map.put("ok",true);
                map.put("data",results);

            }


        }catch (Exception e ){
            e.printStackTrace();
            map.put("ok",false);//前端解析ok这个键，看业务是否处理成功//前端怎么解析的ok？?
            map.put("message","位置错误");
        }

        PrintWriter pw = resp.getWriter();//获取输出流
        //设置响应体的内容
        //jckson 框架里面的    new ObjectMapper()类
        pw.println(new ObjectMapper().writeValueAsString(map)); //输出map对象序列化为json字符串






    }
}
