package com.example.chenxuanjin.auction.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by chenxuanjin on 2017/5/5.
 */

public class Comment extends BmobObject {
    private MyUser author;
    private Goods goods;
    private String content;

    public MyUser getAuthor(){
        return author;
    }

    public void setAuthor(MyUser author){
        this.author = author;
    }

    public Goods getGoods(){
        return goods;
    }

    public void setGoods(Goods goods){
        this.goods = goods;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
