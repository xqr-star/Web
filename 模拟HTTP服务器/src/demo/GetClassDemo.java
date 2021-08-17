package demo;


//复习反射
public class GetClassDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        String className = "com.qiruxie.webapps.dictionary.TranslateServer";

        //根据类的名称获取类信息 加载并得到Class 类信息

//        Class <?> clazz1 = Class.forName(className);
//        //使用forname 的写法等同与 使用当前所在类的类加载器加载需要加载的类 如下面的写法
//        //类加载器
//        //java中的大部分类都是类加载器加载出来的  比如int[] 类型是没有加载器的
//        // boot-stramp 根加载器- 扩展类加载器-应用程序加载器 (AppClassLoader) -自定义类加载器
//        ClassLoader classLoader = GetClassDemo.class.getClassLoader();
//        Class<?> clazz2 = classLoader.loadClass(className);




        ///
        //下面的写法更灵活一些
        //这样的写法就可以通过 某个类的class 获取Class 类然后获取类加载器 然后就可以用这个类加载器加载类了
        //而不是上面只能是当前类的类加载器
        //获取getClass 的class 类
        Class<GetClassDemo> clazz = GetClassDemo.class;
        //查看它的类加载器
        ClassLoader classLoader = clazz.getClassLoader();
        System.out.println(classLoader);

        //用这个类加载器加载别的类
//        classLoader.loadClass("");//使用某个类加载器进行类的加载工作 也就是类加载器的类型不同








    }


}
