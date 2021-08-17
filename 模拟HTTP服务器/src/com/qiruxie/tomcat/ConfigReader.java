package com.qiruxie.tomcat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * 该类整个工程中只new了一次，用于配置文件的解析工作 返回的是Config 类
 * 所有的Config 类的实例化都是这里做的
 */
public class ConfigReader {


    //这两个map的方式是否允许是空的设置 会影响代码是否抛出异常
    //如果用的是ConcurrentHashmap 的方式 那么 因为不允许键值对为空 就会抛出异常
    public Map<String,String> servletNameToServletClassName = new HashMap<>();
    public Map<String,String> urlToServletName = new LinkedHashMap<>();


    //该类用于根据webapp的名字读取web.xml 里面的配置信息
    public Config read(String name) throws FileNotFoundException {
        String fileName = String.format("%s/%s/WEB-INF/web.conf", HttpServer.WEB_BASE, name);

        String stage = "start"; //servlet / mapping

        //读取文本文件

        InputStream inputStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(inputStream, "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            //如果是空行
            if (line.isEmpty() || line.startsWith("#")) {
                //空行或者是 # 开头的
                continue;
            }
            //使用状态机的方式
            switch (stage) {
                case "start":
                    if (line.equals("servlets:")) {
                        stage = "servlets";
                    }
                    break;
                case "servlets":
                    if (line.equals("servlet-mappings:")) {
                        stage = "mappings";
                    } else {
                        //进行Servlet Name - ServletClassName 的解析
                        String[] parts = line.split("=");
                        String servletName = parts[0].trim();
                        String servletClassName = parts[1].trim();
                        servletNameToServletClassName.put(servletName, servletClassName);
                    }
                    break;
                case "mappings":
                    //进行URL - ServletName 的解析
                    String[] parts = line.split("=");
                    String url = parts[0].trim();
                    String servletName = parts[1].trim();
                    urlToServletName.put(url, servletName);
                    break;
            }
        }
        return new Config(servletNameToServletClassName,urlToServletName);
    }
}
