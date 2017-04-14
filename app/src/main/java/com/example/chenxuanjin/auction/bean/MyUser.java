package com.example.chenxuanjin.auction.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by chenxuanjin on 2017/4/3.
 */

public class MyUser extends BmobUser{
    private Boolean sex;
    private BmobFile head;

    public boolean getSex(){
        return sex;
    }

    public void setSex(boolean sex){
        this.sex = sex;
    }

    public BmobFile getHead(){
        return head;
    }

    public void setHead(BmobFile head){
        this.head = head;
    }
}
