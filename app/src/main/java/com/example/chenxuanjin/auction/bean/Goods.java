package com.example.chenxuanjin.auction.bean;


import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by chenxuanjin on 2017/4/15.
 */

public class Goods extends BmobObject implements Serializable{
    private static final long serialVersionUID = 1L;

    private String goodsName;
    private String des;
    private String type;
    private Boolean state;
    private MyUser seller;
    private BmobFile pic;
    private Float Price;
    private BmobRelation likes;


    public String getGoodsName(){
        return goodsName;
    }

    public void setGoodsName(String  goodsName){
        this.goodsName = goodsName;
    }

    public String getDes(){
        return des;
    }

    public void setDes(String  des){
        this.des = des;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type= type;
    }

    public Boolean getState(){
        return state;
    }

    public void setState(Boolean state){
        this.state = state;
    }

    public MyUser getSeller(){
        return seller;
    }

    public void setSeller(MyUser seller){
        this.seller = seller;
    }

    public BmobFile getPic(){
        return pic;
    }

    public void setPic(BmobFile pic){
        this.pic = pic;
    }

    public Float getPrice(){
        return Price;
    }

    public void setPrice(Float Price){
        this.Price = Price;
    }

    public void setLikes(BmobRelation likes){
        this.likes = likes;
    }

    public BmobRelation getLikes(){
        return likes;
    }
}
