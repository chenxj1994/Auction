package com.example.chenxuanjin.auction.bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by chenxuanjin on 2017/4/15.
 */

public class Goods extends BmobObject{
    private String goodsName;
    private String des;
    private String type;
    private Boolean state;
    private String seller;
    private BmobFile pic;
    private Float Price;


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

    public String getSeller(){
        return seller;
    }

    public void setSeller(String seller){
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

}
