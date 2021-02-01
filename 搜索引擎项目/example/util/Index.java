package org.example.util;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.example.model.DocInfo;
import org.example.model.Weight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 构建索引：
 * 正排索引：从本地文件正排索引数据文件中读取到java内存
 * 倒排索引：构建一个map结构 Map<String , List<信息> >
 * Map 键：关键词（分词来做的）
 * Map值-信息：
 *   1.docInfo对象引用或者是docInfo的id ，
 *   2 .权重（标题对应关键词数量*10 + 正文关键词数量*1）   10 和 1 是自己随意决定的--可以灵活调整
 *   3.关键词：是由需要保留待定
 */
public class Index {

    //正排索引 ctrl+shift+U大小写转换  是再java内存中运行的
    private static final List<DocInfo> FORWARD_INDEX = new ArrayList<>();
    //倒排索引
    private static final Map<String,List<Weight>> INVERTED_INDEX = new HashMap<>();



    /**
     *构建正排索引的内容
     * 从本地的raw-data.txt 读取并保存
     */
    public static  void buildForwardIndex(){
        try {
            FileReader fr = new FileReader(Parser.RAW_DATA);//从之前处理的文件中获取输入流的处理
            BufferedReader br = new BufferedReader(fr);
            int id = 0 ; //行号设置 为docInfo的id
            String line;
            //字符串等于null 表示结束
            //一行一行的读取 一行对应一个docInfo对象  类似数据库一行数据对应一个java对象
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) continue; //虽然字符串不等于空但是是一个空行保证空行不进行操作
                //对每一行进行操作
                DocInfo docInfo = new DocInfo();
                docInfo.setId(++id);
                String[] parts = line.split("\3"); // 每一行以此种方式切分

                //为什么又要set
                docInfo.setTitle(parts[0]);
                docInfo.setUrl(parts[1]);
                docInfo.setContent(parts[2]);
                //添加到正排索引
                FORWARD_INDEX.add(docInfo);
            }
        } catch (IOException e) {
           throw  new RuntimeException(e);
           //不要吃异常，初始化又异常让线程不捕获，往外抛让线程结束。
            //所以说初始化（启动tomcat）有问题就尽早暴露问题
        }
    }

    /**
     * 构建倒排索引，从java内存中正排索引的信息来构建
     * 倒排索引 依赖正排索引来构建
     */

    public static void buildInvertedIndex(){

        for(DocInfo doc: FORWARD_INDEX){
            //一个doc，分别对标题和正文进行分词，每一个分词产生一个weight对象，需要计算权重
            //用set数据结构进行去重不可以吗？
            //第一次出现的关键字要new 一个weight对象，之后出现的要获取之前的相同关键词对象，然后更新权重
            //doc 和分词 一对多  分词和weight 一对一

            //实现逻辑：构造一个hashMap  保存分词（键） 和weight对象（value）
            Map<String,Weight> cache = new HashMap<>();
            //遍历处理标题分词
            List<Term> titlesFenCis = ToAnalysis.parse(doc.getTitle()).getTerms();//根据标题分词的集合titles
            for(Term titlesFenCi:titlesFenCis){
                Weight w = cache.get(titlesFenCi.getName()); // 获取标题分词键对应的weight对象
                if(w == null){ //如果没有就初始化创建一个 并放入map中
                    w = new Weight();
                    w.setDoc(doc); //设置成当前的doc
                    w.setKeyWord(titlesFenCi.getName()); //在外面统一处理权重
                    cache.put(titlesFenCi.getName(),w);//这个也可以放在外面吧，就是处理权重后边--就是原来有还是会put
                }
                //处理权重
                w.setWeight(w.getWeight()+10);
            }

            //为什么我的权重都还是10？
            //遍历处理正文分词--逻辑和上面一样--两端逻辑可以抽象成方法
            List<Term> contentFenCis = ToAnalysis.parse(doc.getContent()).getTerms();
            for(Term contentFenCi:contentFenCis){
                Weight w = cache.get(contentFenCi.getName());
                if(w == null){
                    w = new Weight();
                    w.setDoc(doc);
                    w.setKeyWord(contentFenCi.getName());
                    cache.put(contentFenCi.getName(),w);
                }
                w.setWeight(w.getWeight()+1); //正文权重分词+1
            }

            //把临时保存的 map 对象 (cache)全部放到倒排索引放到 list中去

            for(Map.Entry<String,Weight> e : cache.entrySet()){
                String keyWord = e.getKey();
                Weight w = e.getValue();
                //更新博保存到倒排索引 Map<String,List<Weight>>  lis对应的是多个文档 同一个关键词保存在一个list里面
                //现在倒排索引中通过keyword获取已有的值 获取到的是一个list
                List<Weight> weights =   INVERTED_INDEX.get(keyWord);
                if(weights == null) { //如果拿不到对应的list 就新创建一个
                    weights = new ArrayList<>();
                    INVERTED_INDEX.put(keyWord,weights);
                }
                weights.add(w);  //倒排索引中就添加当前文档每个分词对应的Weight对象
            }
        }

    }


    //通过关键词（分词）在倒排中查找映射的文档（多个）  倒排拉链
    //为什么写这个方法因为你的 倒排是private
    public static List<Weight>  get(String keyWord){
        return INVERTED_INDEX.get(keyWord);
    }



    public static void main(String[] args) {

//        //测试正排索引的构建是否正确
       Index.buildForwardIndex(); //对已经构建好的正排索引进行打印查看
//        FORWARD_INDEX
//                .stream() // 这个可加可不加，因为list操作本身也有加强的foreach
//                .forEach(System.out::println);

        //根据正排索引构建倒排索引
        Index.buildInvertedIndex();
        //测试倒排
        for(Map.Entry<String,List<Weight> > e : INVERTED_INDEX.entrySet() ){
            String keyWord = e.getKey();
            List<Weight> weights = e.getValue();
            //不校验weight中的doc 对象，正派已经测试过了，不测试所有属性

            System.out.print(keyWord+":");
            weights.stream()
                    .map(w->{  //map操作把list中每一个weight对象转换为其他对象 通过映射
                        return "("+w.getDoc().getId()+"," +w.getWeight() +")";
                    })  //转换完成之后，会变为list<String>
                    // .collect(Collectors.toList());//直接返回List<String>
                    .forEach(System.out::print);
            System.out.println();
        }

    }

}
