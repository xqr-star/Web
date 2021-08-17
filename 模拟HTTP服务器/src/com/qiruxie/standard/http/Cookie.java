package com.qiruxie.standard.http;

public class Cookie {
    private String name;
    private String value;

    public Cookie(String name,String value){
        this.name = name;
        this.value = value;
    }


    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    //注意set只可以谁知value 因为name 因该是已经设置好的一套规范
    public void SetValue(String value){
        this.value = value;
    }
}
