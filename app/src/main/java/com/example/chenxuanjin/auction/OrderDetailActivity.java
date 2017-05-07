package com.example.chenxuanjin.auction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenxuanjin.auction.bean.Goods;
import com.example.chenxuanjin.auction.bean.Orders;
import com.example.chenxuanjin.auction.utils.SetImageViewUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class OrderDetailActivity extends AppCompatActivity {

    private Orders orders;
    private TextView goodsName,goodsPrice,sellerName,orderId,orderTime,orderContact,buyerName;
    private ImageView goodsPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Bundle bundle = this.getIntent().getExtras();
        orders = (Orders) bundle.getSerializable("orders_data");
        initUI();
    }

    private void initUI(){
        goodsName = (TextView)findViewById(R.id.order_detail_name);
        goodsPrice = (TextView)findViewById(R.id.order_detail_price);
        sellerName = (TextView)findViewById(R.id.order_detail_seller_name);
        orderId = (TextView)findViewById(R.id.order_detail_id);
        orderTime = (TextView)findViewById(R.id.order_detail_time);
        goodsPic = (ImageView)findViewById(R.id.order_detail_photo);
        orderContact = (TextView)findViewById(R.id.order_detail_contact);
        buyerName = (TextView)findViewById(R.id.order_detail_buyer_name);
        goodsName.setText(orders.getGoods().getGoodsName());
        goodsPrice.setText("¥ "+orders.getGoods().getPrice()+"");
        orderId.setText(orders.getObjectId());
        orderTime.setText(orders.getCreatedAt());
        orderContact.setText(orders.getContact());
        sellerName.setText(orders.getGoods().getSeller().getUsername());
        buyerName.setText(orders.getBuyer().getUsername());
        if(orders.getGoods().getPic()!=null){
            SetImageViewUtil.setImageToImageView(goodsPic,orders.getGoods().getPic().getUrl());
        }
    }
}
