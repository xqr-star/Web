package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

/**
 * 做的事情是序列化和反序列化
 * 实例方法 静态方法
 */
public class JSONUtil {

    private static  final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * jason 序列化：
     * 把java 对象序列化为json 字符串
     * o : java 对象
     * String ：ason 字符串
     */
    public static  String serialize(Object o){ // try catach 和 方法签名的异常有什么区别
        try {
            return  MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //不加这句话 会显示没有返回值
            throw new RuntimeException("jason 序列化失败"+o);
        }
    }

    /**
     * 反序列化 ; 只能使用io流进行操作
     * @param <T> 为什么用泛型 我想要明确知道返回的对象类型
     *           is 输入流
     *           clazz 指定要反序列化的类型
     *
     * @return   反序列化的对象
     */
    public static <T> T deserialize(InputStream is,Class<T> clazz){
        try {
            return  MAPPER.readValue(is,clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("jason 反序列化失败"+clazz.getName());

        }
    }

}
