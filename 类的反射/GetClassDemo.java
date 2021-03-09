package demo;

public class GetClassDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        //这是类名称
        String className = "com.qiruxie.webapps.dictionary.TranslateServlet";
        //如何根据String
        //利用反射的特性 通过类名称得到一个列
        //通过类的名称得到一个类
        //第一种
        Class<?> clazz = Class.forName(className);
        //forName的加载方式是默认按照当前代码所在类的加载器进行加载的
        //第一种的效果和下面的一样
        //v1-2
        ClassLoader classLoader = GetClassDemo.class.getClassLoader();
        Class<?> clazz2 = classLoader.loadClass(className);

        //第二种相对第一种会灵活的
       /* classLoader是一个类加载器 具象化就是一个classLoader对象
        java中的”所有类"都是通过类加载器加载出来的的，但并不要求，是同一个类加载器
        但是数组类是一个例外*/

//        ClassLoader classLoader ="";
//
//        Class<?> clazz3 = classLoader.loadClass(className);


//        Class<GetClassDemo> classClass = GetClassDemo.class;//获取它的类模板
//        System.out.println(classClass);
//        //是getClass的类加载器
//        ClassLoader classLoader = classClass.getClassLoader();//获取它的类加载器
//        System.out.println(classLoader); //输出它的类加载器

        classLoader.loadClass("");//使用该类的加载器加载其他类


        //数组类是一个例外
        int[] arr =new int[10];
        System.out.println(arr.getClass().getClassLoader());

    }
}
