package demo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 该类用来生成uuid 和session
 *
 * 其实这个类也可以看作是测试一个带session 的服务器是否可以正确处理
 */
public class GenerateDemoSession {

    private static  class  User implements Serializable{
        private String username;
        private String password;

        public User(String username, String password) {

            this.username = username;
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
    public static void main(String[] args) throws IOException {
        String sessionId = UUID.randomUUID().toString();
        System.out.println(sessionId);
        Map<String,Object> map = new HashMap<>();
        map.put("User1",new User("xqr","1010"));
        map.put("User2",new User("gf","1010"));


        // 然后把用于对象进行写入
        String sessionBath = "E:\\javacode\\模拟HTTP服务器\\sessions";
        String filename = String.format("%s.session",sessionId);

        String path = String.format("%s\\%s",sessionBath,filename);

        try(OutputStream outputStream = new FileOutputStream(path)){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(map);
            objectOutputStream.flush();
        }







    }
}
