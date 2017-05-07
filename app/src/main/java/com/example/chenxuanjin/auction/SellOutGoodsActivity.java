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

public class SellOutGoodsActivity extends AppCompatActivity {

    private ListView mListView;
    private GoodsListAdapter mGoodsListAdapter;
    private List<Goods> listItems = new ArrayList<Goods>();
    private List<Orders> orderList = new ArrayList<Orders>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_out_goods);
        mListView = (ListView)findViewById(R.id.sell_out_list);
        query();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Orders orders = orderList.get(i);
                Intent intent = new Intent(SellOutGoodsActivity.this,OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orders_data",orders);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void query(){
        BmobQuery<Orders> query = new BmobQuery<Orders>();
        BmobQuery<Goods>  innerQuery = new BmobQuery<>();
        innerQuery.addWhereEqualTo("seller",BmobUser.getCurrentUser());
        query.addWhereMatchesQuery("goods","Goods",innerQuery);
        query.include("goods,buyer,goods.seller");
        query.findObjects(new FindListener<Orders>() {
            @Override
            public void done(List<Orders> list, BmobException e) {
                if(e==null){
                    orderList = list;
                    for(Orders orders : list){
                        listItems.add(orders.getGoods());
                    }
                    mGoodsListAdapter = new GoodsListAdapter(SellOutGoodsActivity.this,listItems);
                    mListView.setAdapter(mGoodsListAdapter);
                    mGoodsListAdapter.notifyDataSetChanged();
                }else {
                    Log.i("bmob","失败"+e.getMessage());
                }
            }
        });
    }
}
