package com.example.chenxuanjin.auction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.chenxuanjin.auction.adapter.GoodsListAdapter;
import com.example.chenxuanjin.auction.bean.Goods;
import com.example.chenxuanjin.auction.bean.MyUser;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_out_goods);
        mListView = (ListView)findViewById(R.id.sell_out_list);
        query();
    }

    private void query(){
        BmobQuery<Goods> eq1 = new BmobQuery<Goods>();
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        String user = myUser.getUsername();
        eq1.addWhereEqualTo("seller",user);
        BmobQuery<Goods> eq2 = new BmobQuery<Goods>();
        eq2.addWhereEqualTo("state",true);
        List<BmobQuery<Goods>> andQuerys = new ArrayList<BmobQuery<Goods>>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.and(andQuerys);
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                listItems = list;
                mGoodsListAdapter = new GoodsListAdapter(SellOutGoodsActivity.this,listItems);
                mListView.setAdapter(mGoodsListAdapter);
                mGoodsListAdapter.notifyDataSetChanged();
            }
        });
    }
}
