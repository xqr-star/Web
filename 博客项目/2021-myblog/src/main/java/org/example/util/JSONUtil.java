package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JSONUtil {


    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * JSON 序列化 将java对象序列化为json对象--其实本质上就是一种数据格式
     *
     * @param o java对象
     * @return  jason字符串
     */
    public static String serialize(Object o){
        try {
            return MAPPER.writeValueAsString(o); //就是调用这个对象的方法把它写成字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("json 序列化失败"+o);
        }
    }



    //这里的参数不懂

    /**
     * 反序列化操作 将输入流字符串对象反序列化为java对象
     * @param is 输入流
     * @param clazz 指定要反序列化的类型
     * @param <T>
     * @return 反序列化的对象
     */
    public static <T> T deserialize(InputStream is,Class<T> clazz) {

        try {
            return MAPPER.readValue(is,clazz);//就是调用这个对象的方法把它写成字符串
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("json 反序列化失败"+clazz.getName());
        }
    }


}
