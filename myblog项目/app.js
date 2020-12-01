$(function (){
    $("#login_info").submit(function () {

        //ajax 自己发请i去
        $.ajax({
            url:"../login",//请求的服务路径
            type:"post",//请求的方法
            //contentType:"", 请求的数据列下

            data:$("#login_info").serialize(), //请求数据：使用序列化表单的数据
            datatype:"json", // 响应的数据类型，，使用json需要手动指定

            success:function(r){ // 响应体json 字符串会解析为方法参数
                if(r.success){
                    //前端页面直接跳转到某个路径
                    window.location.href="../jsp/articleList.jsp";
                }else{
                    //弹出提示框
                    alert("错误码"+r.code+"\n"+r.message);
                }
            }

        })

        return false;//异步请求 -- 不是顺序执行代码块
    })
})