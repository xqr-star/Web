package com.qiruxie.tomcat;

import com.qiruxie.standard.Servlet;
import com.qiruxie.standard.http.Cookie;
import com.qiruxie.standard.http.HttpSession;
import com.qiruxie.tomcat.http.HttpRequestParser;
import com.qiruxie.tomcat.http.Request;
import com.qiruxie.tomcat.http.Response;

import java.io.*;
import java.net.Socket;
import java.util.Map;

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

            Context  handleContext  = HttpServer.defaultContext;
            for(Context context: HttpServer.contextList){ //静态属性通过类名访问
                if(context.getName().equals(request.getContentPath())){
                    handleContext = context;
                    break;
                }
            }


            //4.根据request.getServletRequest() 找到对应的servlet
            //通过这里如果get一个应用存在但是资源不存在的时候
            Servlet servlet = handleContext.get(request.getServletPath());
//            这里处理空的时候直接抛出异常了
            if(servlet == null){
                System.out.println("到这里了吗");
                //说明在web.xml中资源没有找到 - 也就是动态资源没找到 但是有可能是静态资源
                servlet = HttpServer.defaultServlet;
            }


            //5.调用servlet.service (req,resp) 交给业务处理
            //自己写的逻辑是找servce 然后由于只支持get方法，看servlet的那个类有没有重写doGet


            servlet.service(request,response);
            System.out.println(response);


            //6.根据resp对象 中的数据，发送HTTP响应
            sendResponse(socket.getOutputStream(),request,response);



            socket.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }



    }


    /**
     * 这里做的就是把响应发出去
     *
     * 之前在生成request的时候 判断了cookie 里面有没有session-id 如果没有的话并且调用了getSession的时候就创建了一个session
     * 这个随机的UUID 需要返回给客户端 并且这个临时生成的session 是保存在request 中的
     * session是保存在我的tomcat服务器的request中、
     *
     */
    private void sendResponse(OutputStream outputStream,Request request, Response response) throws IOException {

        //保存session
        //借助cookie的方式保存session
        //1.种下cookie
        //2.保存成文件的方式

        HttpSession  session = request.session; //这里的session 可能是空的
        if(session != null){
            Cookie cookie = new Cookie("session-id",request.session.sessionId);
            response.addCookie(cookie);
            request.session.saveSession(); //持久化操作 保存在本地文件中
        }


        Writer writer = new OutputStreamWriter(outputStream,"UTF-8");
        PrintWriter printWriter = new PrintWriter(writer);



        printWriter.printf("HTTP/1.0 %s\r\n",response.status);

        //把java中cookie对象写入进去 到响应头中
        for (Cookie cookie : response.cookieList){
            response.setHeader("Set-Cookie",String.format("%s=%s",cookie.getName(),cookie.getValue()) );
        }

        //然后设置一系列的响应头
        for (Map.Entry<String,String> mapping: response.headers.entrySet()){
            String name = mapping.getKey();
            String value = mapping.getValue();

            //写入响应头
            printWriter.printf("%s:%s\r\n",name,value);
        }

        //这里是空行
        printWriter.printf("\r\n");
        //这一堆不知道在干什么 -- 头信息刷新 都刷新进去
        //把自己写好的各种响应体都先刷新到数据中

        //先把响应行和响应头刷新出去
        printWriter.flush();


        //调用flush 操作 确保数据设置到buffer中
        //其实也不用刷新那么多次的
//        response.bodyPrintWriter.flush();
//        response.bodyOutputStream.flush();



        //写入响应体
        byte[]bytes = response.bodyOutputStream.toByteArray();
        outputStream.write(bytes);
        outputStream.flush();






    }
}
