package com.qiruxie.tomcat.http;

import com.qiruxie.standard.http.HttpSession;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Impl是接口的实现类
 * 该类是Session的实现类
 * 同时也是该类在和session的本地文件进行流的操作
 * 但是注意这里所有的操作是在内存里面的，需要把具体的操作写入文件中
 */

public class HttpSessionImpl implements HttpSession {


    private Map<String,Object> sessionData;
    private final String sessionId;


    @Override
    public String toString() {
        return "HttpSessionImpl{" +
                "sessionData=" + sessionData +
                ", sessionId='" + sessionId + '\'' +
                ", SESSION_BASE='" + SESSION_BASE + '\'' +
                '}';
    }

    //没有从cookie中拿到session-id时的构建对象
    public HttpSessionImpl(){
        sessionId  = UUID.randomUUID().toString();
        sessionData = new HashMap<>();
    }

    //从cookie中拿到session-id时的构建对象
    public HttpSessionImpl(String sessionId) throws IOException, ClassNotFoundException {
        this.sessionId = sessionId;
        //根据传入的sessionId 进行session对象的加载也就是读取session的存储啊一系列的东西
        sessionData = loadSessionDate(sessionId);
    }


    //规定文件名字session-id.session  文件反序列化之后的格式是map对象

    private final String SESSION_BASE = "E:\\javacode\\模拟HTTP服务器\\sessions";
    private Map<String, Object> loadSessionDate(String sessionId) throws IOException, ClassNotFoundException {

        String sessionFile = String.format("%s\\%s.session",SESSION_BASE,sessionId);

        File file = new File(sessionFile);
        if(file.exists()){
            //读取文件 反序列化为对象

            try(InputStream inputStream = new FileInputStream(file)){
                //ObjectInputStream 进行对象的读取
                try(ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)){
                    return (Map<String, Object>) objectInputStream.readObject();

                }
            }

        }else {
            //表示session不存在
            //真实的tomcat中可能是过期或者是session是伪造的
            return new HashMap<>();
        }
    }



    //如果用户需要将session 对象写入文件中那么调用set 操作只是保存在了SessionData 的内存中
    //需要持久化到文件中
    public void saveSession() throws IOException {

        //如果当前的sessionData 并没有放入数据（内存里面） 那么也就不需要持久化到本地的文件中
        if(sessionData == null) return;
        String sessionFileName = String.format("%s\\%s.session",SESSION_BASE,sessionId);

        //把sessionData 中的数据写入到对应的session文件中
        try(OutputStream outputStream = new FileOutputStream(sessionFileName)){
            try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
                objectOutputStream.writeObject(sessionData);
                objectOutputStream.flush();
            }
        }
    }

    @Override
    public Object getAttribute(String name) {
        return sessionData.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        sessionData.remove(name);
    }

    @Override
    public void setAttribute(String name,Object value) {
        sessionData.put(name,value);
    }
}
