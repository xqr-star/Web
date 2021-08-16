package com.qiruxie.tomcat;

import com.qiruxie.standard.Servlet;
import com.qiruxie.tomcat.http.HttpRequestParser;
import com.qiruxie.tomcat.http.Request;
import com.qiruxie.tomcat.http.Response;

import java.net.Socket;

public class RequestResponseTask implements Runnable {


    private  final Socket socket;

    public RequestResponseTask(Socket socket){
        this.socket = socket;
    }

    //做解析请求的类其实有一个就可以了 所有这里是用的饿汉式进行单例模式的加载
    private static final HttpRequestParser  parser  = new HttpRequestParser();

    @Override
    public void run() {



        try {
            //1.解析并得到请求对象  通过调用 HttpRequestParser 类的parse的方法进行解析并返回对象
            Request request = null;
            request = parser.parse(socket.getInputStream());
            System.out.println(request);



            //2.实例化一个响应对象
            Response response = new Response();



            //3.根据request.getContextPath() 进行找具体那一个应用处理
            Context  handleContext  = null;
            for(Context context: HttpServer.contextList){ //静态属性通过类名访问
                if(context.getName().equals(request.getContentPath())){
                    handleContext = context;
                    break;
                }
            }


            //4.根据request.getServletRequest() 找到对应的servlet
            Servlet servlet = null;
            if(handleContext != null){
                servlet = handleContext.get(request.getServletPath());
            }



            //5.调用servlet.service (req,resp) 交给业务处理
            //自己写的逻辑是找servce 然后由于只支持get方法，看servlet的那个类有没有重写doGet
            servlet.service(request,response);
            System.out.println(response);


//            //6.根据resp对象 中的数据，发送HTTP响应
//            sendResponse(response);



            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
