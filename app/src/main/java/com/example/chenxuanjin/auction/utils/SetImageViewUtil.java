package com.example.chenxuanjin.auction.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by chenxuanjin on 2017/4/28.
 */

public class SetImageViewUtil {
    public static void setImageToImageView(final ImageView imageView , final String imgURL){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e("HAHAHA", "设置图片成功");
                super.handleMessage(msg);
                Bitmap bitmap = (Bitmap)msg.obj;
                imageView.setImageBitmap(bitmap);
            }
        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.getBitmap(imgURL);//这是我封装的获取Bitmap的工具
                Message msg = new Message();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }).start();
    }
}