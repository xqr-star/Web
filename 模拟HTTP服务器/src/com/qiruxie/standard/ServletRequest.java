package com.qiruxie.standard;

//参考tomcat的官方文档自己定义了一些方法
//很多方法自己定义的时候都省略掉了
//只支持get方法
public interface ServletRequest {

    //获取请求参数 url
    String getParameter(String name);



    //只支持get方法，所以这里暂时先不用了
    //ServletInputStream getInputStream();


}
