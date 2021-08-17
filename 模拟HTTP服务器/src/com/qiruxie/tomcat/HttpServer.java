package com.qiruxie.tomcat;

//这里是Http服务器

import com.qiruxie.standard.Servlet;
import com.qiruxie.standard.ServletException;
import com.qiruxie.tomcat.servlets.DefaultServlet;
import com.qiruxie.tomcat.servlets.NotFoundServlet;

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
 *
 * 做了三件事情
 * 1.找到所有的Servlet对象并且初始化
 * 2.调用服务器的处理逻辑(解析请求 调用service 做出响应)
 * 3.销毁所有的Servlet对象
 */
public class HttpServer {

    //这两个是在加载静态资源 和 不存在的资源的servelt
    public static DefaultServlet defaultServlet = new DefaultServlet();
    public static NotFoundServlet notFoundServlet = new NotFoundServlet();



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


    /**
     * 单次的请求响应处理逻辑 使用多线程的方式
     * 真正的任务都是在 RequestResponseTask 里面写的
     * @throws IOException
     */
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

        //也需要把静态的和 不存在的资源的对应的servlet进行销毁
        defaultServlet.destroy();
        notFoundServlet.destroy();
    }


    /**
     * 找到所有的Servlet阶段 都是在这里做的
     *
     */
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

        //同时在这里把静态的和不存在的也做init
        defaultServlet.init();
        notFoundServlet.init();
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

    //不直到这里是在干什么
    public  static final DefaultContext defaultContext = new DefaultContext(reader);


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
