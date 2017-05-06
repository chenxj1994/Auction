package com.example.chenxuanjin.auction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenxuanjin.auction.bean.Goods;
import com.example.chenxuanjin.auction.bean.MyUser;
import com.example.chenxuanjin.auction.bean.Orders;
import com.example.chenxuanjin.auction.utils.SetImageViewUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class OrderConfirmActivity extends AppCompatActivity {

    private Goods goods = null;
    private ImageView goodsPic;
    private TextView goodsName,goodsPrice,goodsActualPrice;
    private Button orderConfirmBtn;
    private EditText orderContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        Bundle bundle = this.getIntent().getExtras();
        goods = (Goods)bundle.getSerializable("goods");
        initUI();
    }

    private void initUI(){
        goodsPic = (ImageView)findViewById(R.id.order_goods_pic);
        goodsName = (TextView)findViewById(R.id.order_goods_name);
        goodsPrice = (TextView)findViewById(R.id.order_goods_price);
        goodsActualPrice = (TextView)findViewById(R.id.order_goods_actual_price);
        orderContact = (EditText)findViewById(R.id.order_contact);
        goodsName.setText(goods.getGoodsName());
        goodsPrice.setText("¥ "+goods.getPrice()+"");
        goodsActualPrice.setText("¥ "+goods.getPrice()+"");
        if(goods.getPic() != null){
            BmobFile bmobFile = goods.getPic();
            SetImageViewUtil.setImageToImageView(goodsPic,bmobFile.getUrl());
        }
        if(BmobUser.getCurrentUser(MyUser.class).getMobilePhoneNumber()!=null){
            orderContact.setText(BmobUser.getCurrentUser(MyUser.class).getMobilePhoneNumber());
        }
        orderConfirmBtn = (Button)findViewById(R.id.order_confirm_btn);
        orderConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderContact.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"请输入联系方式",Toast.LENGTH_LONG).show();
                }else {
                    uploadOrder();
                }
            }
        });
    }

    public void uploadOrder(){
        String contact = orderContact.getText().toString();
        MyUser buyer = BmobUser.getCurrentUser(MyUser.class);
        Goods orderGoods = goods;
        //下单后将货物的state状态置为false
        orderGoods.setValue("state",true);
        orderGoods.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {}
        });
        //
        Orders orders = new Orders();
        orders.setContact(contact);
        orders.setBuyer(buyer);
        orders.setGoods(orderGoods);
        orders.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Toast.makeText(getApplicationContext(),"下单成功",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"下单失败"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
