$(function () { //页面加载完成之后执行function里面逻辑
    //jquery . 使用$("")通过绑定id获取某一个页面元素 -- 绑定form表单
    $("#login_info").submit(function () {
        //ajax自己发起请求--异步的不会阻塞下面代码执行 不会等代码执行完就会往后走
        $.ajax({
            url:"../login",
            type:"post",//请求方法
           // contentType: "",//请求的数据类型默认表单格式 json需要指定
            data:$("#login_info").serialize(), //请求数据序列化表单数据
            datatype:"json",//响应的数据类型
            success:function (r) {//响应体json字符串会在jquery里面解析为方法参数
                if(r.success){
                    //前端页面url跳转到某个路径
                    //成功后就会去去显示文章列表的路径
                    window.location.href="../jsp/articleList.jsp";
                }else {
                    alert("错误码"+r.code +"\n错误消息"+r.message);
                }
            }
        })
        //统一不执行默认的表单提交 如果不写这个，就会变成表单提交
        return false;
    })
})