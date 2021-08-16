package com.qiruxie.tomcat;

//这里是Http服务器

import com.qiruxie.standard.Servlet;
import com.qiruxie.standard.ServletException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 通过socket 编程 来做得 和之前review 阶段做的是一样的、
 * 这里是整个项目的启动阶段 模拟的就是tomcat帮我们做的事情
 */
public class HttpServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ServletException {




        // 1.找到所有的servlet 对象进行初始化 只会被调用一次
        initServer();
        for(Context context : contextList){
            for(Servlet servlet : context.servletList){
                System.out.println(servlet);
            }
        }

        //2.处理服务器逻辑
        startServer();

        //3.找到所有的servlet对象进行销毁 被调用一次
        destroyServer();

    }


    //单次的请求响应处理逻辑
    private static void startServer() throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);

        // 使用多线程的方式
        ExecutorService executorService = new ThreadPoolExecutor(10,
                15,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        while (true){
            Socket accept = serverSocket.accept();
            Runnable task = new RequestResponseTask(accept);
            executorService.execute(task);
        }
    }


    private static void destroyServer() {
        for(Context context : contextList){
            context.destroyServlets();
        }
    }

    private static void initServer() throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException {
        //1.扫描所有的web应用
        scanContexts();
        //2.对一个web应用进行解析，需要直到支持哪些Servelt
        parseContextConf();
//        //3.利用反射的机制加载servlet名称对应的servlet类
        loadServletClasses();
//        //4.对类进行实例化
        instantiateServletObjects();
//        //5.调用每一个Servlet类的init方法  对每一类进行初始化的工作
        initializeServletObjects();
    }

    private static void initializeServletObjects() throws ServletException {


        System.out.println("第五步：执行每个对象的init");
        for(Context context : contextList){
            context.initServletObjects();
        }
    }

    private static void instantiateServletObjects() throws InstantiationException, IllegalAccessException {

        System.out.println("第四步：实例化每个Servlet对象");
        for(Context context : contextList){
            context.instantiateServletObjects();
        }
    }

    private static void loadServletClasses() throws ClassNotFoundException {
        System.out.println("第三步:加载每个servlet的类");
        for(Context context :contextList){
            context.loadServletClass();
        }
    }


    private static void parseContextConf() throws FileNotFoundException {

        System.out.println("第二步：解析每个的we的web.xml");
        //web.conf文件的读取
        for(Context context : contextList){
            //对每一个web应用的web.cof进行解析
            //web.cof 放在哪里，必须符合规范，否则就会出现出问题
            //根据每一个context读取它的配置文件
            context.readConfigFile();

            //进行解析 配置文件的工作
            //指定配置文件的标准

        }
    }


    public static final  String WEB_BASE = "E:\\javacode\\模拟HTTP服务器\\webapps";
    public static  final List<Context> contextList = new ArrayList<>();

    ////该类用于读取文件的配置只需要一个就可以了 一样是单例模式
    private static final ConfigReader reader = new ConfigReader();
    private static void scanContexts() {

        System.out.println("第一步：扫描所有的context");
        //目录扫描
        File webappsRoot = new File(WEB_BASE);

        //获取目录下的所有文件
        File[] files = webappsRoot.listFiles();

        if(files == null) {
            throw new RuntimeException();
        }

        //抽象出一个Context 类，每一个Context 对象保存对应的信息
        for(File file :files){
            if(!file.isDirectory()){ //如果获取的不是目录，那么就跳过
                continue;
            }
            String contextName = file.getName();
            Context context = new Context(contextName,reader);
            contextList.add(context);
        }


    }
}
