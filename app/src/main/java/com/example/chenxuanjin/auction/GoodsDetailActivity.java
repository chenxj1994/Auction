package com.example.chenxuanjin.auction;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenxuanjin.auction.adapter.CommentListAdapter;
import com.example.chenxuanjin.auction.bean.Comment;
import com.example.chenxuanjin.auction.bean.Goods;
import com.example.chenxuanjin.auction.bean.MyUser;
import com.example.chenxuanjin.auction.utils.ListViewShowUtils;
import com.example.chenxuanjin.auction.utils.SetImageViewUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class GoodsDetailActivity extends AppCompatActivity {
    private TextView goodsName,goodsPrice,goodsSeller,goodsDes;
    private ImageView goodsPhoto,backBtn,likes;
    private Goods goods = null;
    private Button orderBtn,sendCommentBtn;
    private LinearLayout layoutWord;
    private RelativeLayout layoutSendComment,goodsDetailBottom;
    private EditText commentMsg;
    private List<Comment> listItems = new ArrayList<Comment>();
    private CommentListAdapter mCommentListAdapter;
    private ListView mListView;
    private Boolean IsCollected = false;
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
        mListView = (ListView)findViewById(R.id.comment_list_view);
        orderBtn = (Button)findViewById(R.id.order_btn);
        layoutWord = (LinearLayout)findViewById(R.id.layout_words);
        likes = (ImageView) findViewById(R.id.likes);
        layoutSendComment = (RelativeLayout)findViewById(R.id.layout_send_comment);
        goodsDetailBottom = (RelativeLayout)findViewById(R.id.layout_goods_detail_bottom);
        backBtn = (ImageView)findViewById(R.id.back_btn);
        commentMsg = (EditText)findViewById(R.id.comment_msg);
        sendCommentBtn = (Button)findViewById(R.id.send_comment_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSendComment.setVisibility(View.GONE);
                goodsDetailBottom.setVisibility(View.VISIBLE);
            }
        });
        //留言监听
        layoutWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsDetailBottom.setVisibility(View.GONE);
                layoutSendComment.setVisibility(View.VISIBLE);
            }
        });
        //收藏监听
        initLikesUI();
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsCollected){
                    cancelCollection();
                }else {
                    setCollection();
                }
            }
        });

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
        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(commentMsg.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"内容不能为空",Toast.LENGTH_LONG).show();
                }else {
                    uploadComment();
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
        loadComment();
    }

    private void uploadComment(){
        final Comment comment = new Comment();
        String content = commentMsg.getText().toString();
        MyUser author = BmobUser.getCurrentUser(MyUser.class);
        comment.setGoods(goods);
        comment.setContent(content);
        comment.setAuthor(author);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Toast.makeText(getApplicationContext(),"评论成功",Toast.LENGTH_LONG).show();
                    commentMsg.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(commentMsg.getWindowToken(), 0) ;
                    layoutSendComment.setVisibility(View.GONE);
                    goodsDetailBottom.setVisibility(View.VISIBLE);
                    initUI();
                }
            }
        });
    }

    private void loadComment(){
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.addWhereEqualTo("goods",goods);
        query.include("author");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e == null){
                    Log.i("harden","查询成功");
                    listItems = list;
                    mCommentListAdapter = new CommentListAdapter(GoodsDetailActivity.this,listItems);
                    mListView.setAdapter(mCommentListAdapter);
                    mCommentListAdapter.notifyDataSetChanged();
                    ListViewShowUtils.setListViewHeightBasedOnChildren(mListView);
                }else {
                    Log.i("harden","查询失败");
                }
            }
        });
    }

    private void setCollection(){
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation = new BmobRelation();
        relation.add(user);
        goods.setLikes(relation);
        goods.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    initLikesUI();
                    Toast.makeText(getApplicationContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                }else {
                    Log.i("bmob","失败"+e.getMessage());
                }
            }
        });
    }

    private void cancelCollection(){
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation = new BmobRelation();
        relation.remove(user);
        goods.setLikes(relation);
        goods.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    initLikesUI();
                    Toast.makeText(getApplicationContext(),"取消收藏",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initLikesUI(){
        BmobQuery<MyUser> eq1 = new BmobQuery<MyUser>();
        eq1.addWhereRelatedTo("likes",new BmobPointer(goods));
        BmobQuery<MyUser> eq2 = new BmobQuery<MyUser>();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        eq2.addWhereEqualTo("objectId",user.getObjectId());
        List<BmobQuery<MyUser>> andQuerys = new ArrayList<BmobQuery<MyUser>>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.and(andQuerys);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if(e == null){
                    if(list.size()==0){
                        IsCollected = false;
                        likes.setImageResource(R.drawable.collection);
                    }else {
                        IsCollected = true;
                        likes.setImageResource(R.drawable.like);
                    }
                }else {
                    Log.i("Jharden","失败："+e.getMessage());
                }
            }
        });
    }
}
