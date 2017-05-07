package com.example.chenxuanjin.auction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chenxuanjin.auction.adapter.GoodsListAdapter;
import com.example.chenxuanjin.auction.bean.Goods;
import com.example.chenxuanjin.auction.bean.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyCollectionActivity extends AppCompatActivity {

    private ListView mListView;
    private GoodsListAdapter mGoodsListAdapter;
    private List<Goods> listItems = new ArrayList<Goods>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        mListView = (ListView)findViewById(R.id.likes_list);
        query();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = listItems.get(i);
                Intent intent = new Intent(MyCollectionActivity.this, GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods_data",goods);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void query(){
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        BmobQuery<MyUser> innerQuery = new BmobQuery<MyUser>();
        String id = BmobUser.getCurrentUser().getObjectId();
        innerQuery.addWhereEqualTo("objectId",id);
        query.addWhereMatchesQuery("likes","_User",innerQuery);
        query.include("seller");
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if(e==null){
                    listItems = list;
                    mGoodsListAdapter = new GoodsListAdapter(MyCollectionActivity.this,listItems);
                    mListView.setAdapter(mGoodsListAdapter);
                    mGoodsListAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),"查询失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
