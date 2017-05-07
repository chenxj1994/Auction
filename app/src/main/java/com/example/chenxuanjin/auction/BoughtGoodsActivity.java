package com.example.chenxuanjin.auction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chenxuanjin.auction.adapter.GoodsListAdapter;
import com.example.chenxuanjin.auction.bean.Goods;
import com.example.chenxuanjin.auction.bean.MyUser;
import com.example.chenxuanjin.auction.bean.Orders;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BoughtGoodsActivity extends AppCompatActivity {

    private ListView mListView;
    private GoodsListAdapter mGoodsListAdapter;
    private List<Goods> listItems = new ArrayList<Goods>();
    private List<Orders> orderList = new ArrayList<Orders>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought_goods);
        mListView = (ListView)findViewById(R.id.bought_list);
        query();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Orders orders = orderList.get(i);
                Intent intent = new Intent(BoughtGoodsActivity.this,OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orders_data",orders);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void query(){
        BmobQuery<Orders> query = new BmobQuery<Orders>();
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        query.addWhereEqualTo("buyer",myUser);
        query.include("goods,buyer,goods.seller");
        query.findObjects(new FindListener<Orders>() {
            @Override
            public void done(List<Orders> list, BmobException e) {
                if(e==null){
                    orderList = list;
                    for(Orders orders : list){
                        listItems.add(orders.getGoods());
                    }
                    mGoodsListAdapter = new GoodsListAdapter(BoughtGoodsActivity.this,listItems);
                    mListView.setAdapter(mGoodsListAdapter);
                    mGoodsListAdapter.notifyDataSetChanged();
                }else {
                    Log.i("bmob","失败"+e.getMessage());
                }
            }
        });
    }
}
