package com.qiruxie.webapps.dictionary;

import com.qiruxie.standard.http.HttpServlet;
import com.qiruxie.standard.http.HttpServletRequest;
import com.qiruxie.standard.http.HttpServletResponse;

import java.io.*;

public class HelloServlet extends HttpServlet {


    //是不是静态资源和html 没有关系

     public String docBath = "E:\\javacode\\模拟HTTP服务器\\sessions";
     private String requestURI = "\\hello.html";
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        String filePath = docBath + requestURI;
        //判断该文件是否存在 暂时先不考虑目录的情况
        File file = new File(filePath);
        if (file.exists()) {
            //读取文件内容，并写入响应
            //OutputStream outputStream = socket.getOutputStream();

            OutputStream outputStream = resp.getOutputStream();
            Writer writer = new OutputStreamWriter(outputStream);
            PrintWriter printWriter = new PrintWriter(writer);


            //写响应体 读取文本内容 把这个文件的内容全部处理到 tcp的outputStream中去
            InputStream resourceInputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            while (true) {
                //循环的从原始的文件中读取一部分的内容
                int len = resourceInputStream.read(buffer);
                if (len == -1) { //代表已经原始文件已经读取完毕了
                    break;
                }
                //把每一次接受的缓冲不断的写入到tcp的缓冲区中
                outputStream.write(buffer, 0, len);
            }
        }
    }
}
