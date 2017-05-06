package com.example.chenxuanjin.auction.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by chenxuanjin on 2017/5/3.
 */

public class Collection extends BmobObject{
    private MyUser user;
    private Goods goods;

    public MyUser getUser(){
        return user;
    }
    public void setUser(MyUser user){
        this.user = user;
    }

    public Goods getGoods(){
        return goods;
    }
    public void setGoods(Goods goods){
        this.goods = goods;
    }
}
