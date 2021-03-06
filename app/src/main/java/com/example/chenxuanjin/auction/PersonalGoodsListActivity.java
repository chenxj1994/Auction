package com.example.chenxuanjin.auction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class PersonalGoodsListActivity extends AppCompatActivity {

    private ListView mListView;
    private GoodsListAdapter mGoodsListAdapter;
    private List<Goods> listItems = new ArrayList<Goods>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_goods_list);
        mListView = (ListView)findViewById(R.id.my_goods_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = listItems.get(i);
                Intent intent = new Intent(PersonalGoodsListActivity.this, GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods_data",goods);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        query();
    }

    private void query(){
        BmobQuery<Goods> eq1 = new BmobQuery<Goods>();
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        eq1.addWhereEqualTo("seller",myUser);
        BmobQuery<Goods> eq2 = new BmobQuery<Goods>();
        eq2.addWhereEqualTo("state",false);
        List<BmobQuery<Goods>> andQuerys = new ArrayList<BmobQuery<Goods>>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.and(andQuerys);
        query.include("seller");
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                listItems = list;
                mGoodsListAdapter = new GoodsListAdapter(PersonalGoodsListActivity.this,listItems);
                mListView.setAdapter(mGoodsListAdapter);
                mGoodsListAdapter.notifyDataSetChanged();
            }
        });
    }
}
