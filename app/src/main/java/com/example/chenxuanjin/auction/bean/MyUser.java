package com.example.chenxuanjin.auction.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by chenxuanjin on 2017/4/3.
 */

public class MyUser extends BmobUser{
    private Boolean sex;

    public boolean getSex(){
        return this.sex;
    }

    public void setSex(boolean sex){
        this.sex = sex;
    }
}
