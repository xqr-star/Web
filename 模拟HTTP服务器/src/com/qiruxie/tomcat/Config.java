package com.qiruxie.tomcat;

import java.util.Map;

/**
 * 该类里面维护了一个配置信息
 * 也就是该类的存在依附于Context类 ，里面存放的时每一个context类的web.xml的解析内容
 */
public class Config {

    public Map<String,String> servletNameToServletClassName ;
    public Map<String,String> urlToServletName;


    //构造方法
    public Config(Map<String,String> servletNameToServletClassName,Map<String,String> urlToServletName){
        this.servletNameToServletClassName = servletNameToServletClassName;
        this.urlToServletName = urlToServletName;
    }




}
