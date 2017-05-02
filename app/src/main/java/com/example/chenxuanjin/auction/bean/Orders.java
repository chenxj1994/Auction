package com.example.chenxuanjin.auction.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by chenxuanjin on 2017/4/27.
 */

public class Orders extends BmobObject {

    private String buyerName;
    private String contact;
    private Goods goods;

    public String getBuyerName(){
        return buyerName;
    }
    public void setBuyerName(String buyerName){
        this.buyerName = buyerName;
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
