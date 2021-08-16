package com.qiruxie.tomcat;

import com.qiruxie.standard.Servlet;
import com.qiruxie.standard.ServletException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 该类用于保存每一个web应用的基本信息
 * web应用的名称  we应用的配置信息 以及为了得到配置信息而创建的的一个单例对象的类 ConfigReader 类
 */
public class Context {

    private final String name;
    private Config config;//用于保存本类的配置信息
    private final ConfigReader reader; //用于读取配置信息
    //如果在tomcat中部署了多个应用每一个Context 都是由自己的Context 的类加载器进行加载，互不干扰
    private ClassLoader webappClassLoader = Context.class.getClassLoader();



    //读取配置的对象在创建的时候就已经建立了
    public Context(String name,ConfigReader reader){
        this.name = name;
        this.reader = reader;
    }

    public String getName() {
        return name;
    }


    public void readConfigFile() throws FileNotFoundException {
        this.config = reader.read(name); //在这里的时候 对象就会进行构造
    }




    //用于进行类的加载工作
    List<Class<?>> servletClassList = new ArrayList<>();
    public void loadServletClass() throws ClassNotFoundException {
        //可能会有重复的类 使用set进行过滤
        //它的意思是可能不同的ServletName 映射到了同一个Servlet类
        Set<String> servletClassNames = new HashSet<>(config.servletNameToServletClassName.values());

        for(String servletClassName : servletClassNames){
            Class<?> servletClass = webappClassLoader.loadClass(servletClassName);
            servletClassList.add(servletClass);
        }
    }


    //为什么所有的类一定是Servlet的类 是实现了继承了
    List<Servlet> servletList = new ArrayList<>();
    public void instantiateServletObjects() throws IllegalAccessException, InstantiationException {
        for(Class<?> servletClass : servletClassList){
            Servlet servlet = (Servlet)servletClass.newInstance();//默认调用的是该类的无参构造方法
            servletList.add(servlet);
        }
    }

    public void initServletObjects() throws ServletException {

        for(Servlet servlet : servletList){
            servlet.init();
        }

    }

    public void destroyServlets() {
        for (Servlet servlet :servletList){
            servlet.destroy();
        }
    }


    /**
     * 这里写的是根据servletPath 找到对应的Servlet
     * @param servletPath
     * @return
     */
    public Servlet get(String servletPath) {
        String servletName = config.urlToServletName.get(servletPath);
        String servletClassName = config.servletNameToServletClassName.get(servletName);

        for (Servlet servlet : servletList) {
            //通过反射机制 得到Class 然后得到全文的名称
            String currentServletClassName = servlet.getClass().getCanonicalName();
            if (currentServletClassName.equals(servletClassName)) {
                return servlet;
            }
        }
        return  null;
    }
}
