package org.example.util;

import org.example.model.DocInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 步骤一：从本地的API目录，遍历html文件
 * 每一个html文件构建一个正文索引(正文索引创建在本地)
 * 本地某个文件
 * 正文索引信息List<DocInfo>
 *  DocInfo  （id，tittle，content，url）
 * tomcamt 启动后加载到内存
 */
public class Parser {

    //API目录
    public static final String API_PATH = "E:\\BIT—比特\\jdk-8u261-docs-all\\docs\\api";

    //生成的目标文件 -- 构建的本地文件的正排索引  构建的这个文件和数据保存文件类似
    public static final String RAW_DATA = "E:\\raw_data.txt";

    //官方的API文档根路径--路径只到/api
    public static final String API_BATH_PATH= "https://docs.oracle.com/javase/8/docs/api";

    //将API_PATH下所有的html文件进行递归遍历
    public static void main(String[] args) throws IOException { // main 线程不处理，抛出后默认的做法就是打印堆栈然后重新结束
        //api本地路径下所有的html文件先找到
        List<File> htmls = listHtml(new File(API_PATH));
        FileWriter fw = new FileWriter(RAW_DATA);
        //BufferedWriter bfw = new BufferedWriter(fw);不用上面的原因就是我最后是想一行一行的写入（输出到）本地文件中，它这个API里面没有这个方法
        PrintWriter pw = new PrintWriter(fw,true); // 讲了一堆为什么用这个  autoFlush :true 表示自动刷新缓冲区的设置不再需要flush

        for(File html:htmls){
            //用来测试html是否正确获取
 /*           //System.out.println(html);
            System.out.println(html.getAbsolutePath());*/

            //一个html文件解析为一个DocInfo对象 -- 相当于进行输入流解析
            DocInfo doc = parseHtml(html);
            //System.out.println(doc); 是为了验证用的

            //保存到本地正排索引文件 -- 输出流 到本地文件（本地文件时刚开始没有的，是输出的）
            //输出到本地路径
            //每解析出来一个doc 就进行输出
            //格式一行为一个doc   title +\3+url+\3+content
            String uri = html.getAbsolutePath().substring(API_PATH.length());
            System.out.println("Parse: "+uri);// 这里是为了直到我的解析到哪一步了？
            pw.println(doc.getTitle()+"\3"+doc.getUrl()+"\3"+doc.getContent());


        }



    }

    private static DocInfo parseHtml(File html) {
        DocInfo doc = new DocInfo();
        //名称是不要html的 所以对html进行的文件名进行substring
        //或者html名称长度-5
        doc.setTitle(html.getName().substring(0,html.getName().length()-".html".length()));
        //先获取相对路径 -- 通过先获取绝对路径然后进行substring--只要api后面的部分
        String uri = html.getAbsolutePath().substring(API_PATH.length());
        doc.setUrl(API_BATH_PATH+uri); // 组成官方地址的绝对路径
        doc.setContent(parseContent(html)); // 通过写方法设置html的content


        //id 以输出的行号来设置再这里可以先不设置
        return doc; //


    }

    /**
     * 解析html内容
     *<标签>  内容</标签>
     * 只取内容，有多个标签进行拼接
     */

    private static String parseContent(File html) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr =  new FileReader(html);
            // BufferedReader bfr = new BufferedReader(); //暂时先不适用缓冲

            int i ;
            // 以一个个字符进行读取
            //read的返回值int 但是可以转成char
            boolean isContent = false;
            while( (i = fr.read() ) != -1){
                //将读取到的进行强制转换
                char c = (char) i;
                //根据读取的字符c 是否是‘<’ '>' 和 iscontent 决定是否拼接字符
                if(isContent){
                    //当前是正文
                    if(c == '<') { //表示当前标签的内容读取结束
                        //设置变量
                        isContent = false;
                        continue;
                    }else if(c == '\n'|| c == '\r') {//换行符进行处理
                        sb.append(" ");
                    }else {
                        //需要将字符串拼接
                        sb.append(c);
                    }
                }else {//如果不是正文
                    if(c == '>'){ //要遇到‘>’ 表示开始读取正文，设置iscontent
                        isContent = true;
                    }
                   // continue; 可写可不写
                }
            }
        } catch (IOException e) {
           throw new RuntimeException(e);//如果出错，出程序直接挂掉 因为你往上抛出，但是上层没有处理，那么就会程序程 // 而不是打印异常 不然有可能看不到
        }



        return sb.toString();

    }

    //递归遍历html文件
    private static List<File> listHtml(File dir){
        List<File> list = new ArrayList<>();
        File[] children = dir.listFiles(); // 获取子文件个子文件夹

        for(File child :children){
            if(child.isDirectory()){
                list.addAll(listHtml(child));//子文件夹：递归调用获取子文件夹内的html文件 将递归的所有结果添加到当前的返回值
            }else if(child.getName().endsWith(".html")){
                //文件是以html结束的
                list.add(child);
            }
        }
        return list;
    }




}
