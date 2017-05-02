package com.example.chenxuanjin.auction;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenxuanjin.auction.bean.Goods;
import com.example.chenxuanjin.auction.bean.MyUser;
import com.example.chenxuanjin.auction.utils.SetImageViewUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class GoodsDetailActivity extends AppCompatActivity {
    private TextView goodsName,goodsPrice,goodsSeller,goodsDes;
    private ImageView goodsPhoto;
    private Goods goods = null;
    private Button orderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        Bundle bundle = this.getIntent().getExtras();
        goods = (Goods)bundle.getSerializable("goods_data");
        initUI();
    }

    private void initUI(){
        goodsPhoto = (ImageView)findViewById(R.id.detail_goods_photo);
        goodsName = (TextView)findViewById(R.id.detail_goods_name);
        goodsPrice =(TextView)findViewById(R.id.detail_goods_price);
        goodsSeller = (TextView)findViewById(R.id.detail_goods_seller);
        goodsDes = (TextView)findViewById(R.id.detail_goods_describe);
        orderBtn = (Button)findViewById(R.id.order_btn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BmobUser.getCurrentUser(MyUser.class)!=null){
                        Intent intent = new Intent(GoodsDetailActivity.this,OrderConfirmActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("goods",goods);
                        intent.putExtras(bundle);
                        startActivity(intent);
                }else{
                    Intent intent = new Intent(GoodsDetailActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        goodsName.setText(goods.getGoodsName());
        goodsPrice.setText(goods.getPrice()+"");
        goodsSeller.setText(goods.getSeller());
        goodsDes.setText(goods.getDes());
        if (goods != null){
            if(goods.getPic() != null){
                BmobFile bmobFile = goods.getPic();
                SetImageViewUtil.setImageToImageView(goodsPhoto,bmobFile.getUrl());
                }
            }

    }
}
