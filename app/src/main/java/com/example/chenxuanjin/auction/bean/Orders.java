package com.example.chenxuanjin.auction.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by chenxuanjin on 2017/4/27.
 */

public class Orders extends BmobObject {

    private MyUser buyer;
    private String contact;
    private Goods goods;

    public MyUser getBuyer(){
        return buyer;
    }
    public void setBuyer(MyUser buyer){
        this.buyer = buyer;
    }

    public Goods getGoods(){
        return goods;
    }
    public void setGoods(Goods goods){
        this.goods = goods;
    }

    public String getContact(){
        return contact;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
}
