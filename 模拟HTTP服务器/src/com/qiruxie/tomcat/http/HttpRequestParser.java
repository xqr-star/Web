package com.qiruxie.tomcat.http;


import com.qiruxie.standard.http.Cookie;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.*;

/**
 * 这个类是专门用来解析http对象的
 * 得到的是一个请求对象 request (继承了httpRequest)
 *
 * 这是典型的工厂模式，构造方法比较复杂，使用工厂模式来做这件事
 */
public class HttpRequestParser {
    public Request parse(InputStream inputStream) throws IOException, ClassNotFoundException {

        Scanner scanner = new Scanner(inputStream,"UTF-8");

        String method = scanner.next().toUpperCase(); //当时指定的标准是大写
        String path = scanner.next();
        String version = scanner.nextLine();



        //解析path 找到 context  servlet queryString
        // http://www.主机ip/context/servelt?queryString (k1=v1&k2=v2)
        String requestURI = path;
        String queryString = null;
        int i = path.indexOf("?");
        Map<String,String> paramenters = new HashMap<>();
        if(i != -1) {
            requestURI = path.substring(0, i);
            queryString = path.substring(i+1);

            //一定要记得解码
            for(String kv : queryString.split("&")) {
                String[] kvparts = kv.split("=");
                String name = URLDecoder.decode(kvparts[0].trim(), "UTF-8");
                String value = URLDecoder.decode(kvparts[1].trim(), "UTF-8");
                paramenters.put(name, value);
            }
        }


        //解析Context 和 Servlet
        //   /dictionary/translate
        int index = requestURI.indexOf('/',1);
        String contextPath = "/";
        String servletPath = requestURI;
        if(index != -1){
            contextPath = requestURI.substring(1,index);
            servletPath = requestURI.substring(index);
        }


        String headerLine = null;
        Map<String ,String> headers = new HashMap<>();
        //用来存放Cookie
        List<Cookie> cookieList = new ArrayList<>();


        while (scanner.hasNextLine()){
            headerLine = scanner.nextLine();
            if(headerLine.isEmpty()){ //不为空
                break;
            }else {
                //解析头信息  //cookie : k1=V1   ;k=v2
                String[] parts = headerLine.split(":");
                String name = parts[0].trim().toLowerCase();
                String value = parts[1].trim().toLowerCase();
                headers.put(name,value);


                //如果header中有cookie的话，就把cookie单独拿出来，然后解析返回一个list
                
                if(name.equals("cookie")){
                    String[] cookieKeyValues = value.split(";");

                    for(String kv : cookieKeyValues) {
                        if(kv == null) continue;
                        String[] cookieParts = kv.trim().split("=");
                        String cookieName = cookieParts[0].trim();
                        String cookieValue = cookieParts[1].trim();
                        cookieList.add(new Cookie(cookieName, cookieValue));
                    }
                }
            }

            //因为只支持get方法 所以就不解析具体的请求体了
        }


        return new Request(method,requestURI,contextPath,servletPath,paramenters,headers,cookieList);

    }
}
