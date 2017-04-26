package com.example.chenxuanjin.auction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chenxuanjin.auction.R;
import com.example.chenxuanjin.auction.bean.Goods;

import java.util.List;

/**
 * Created by chenxuanjin on 2017/4/25.
 */

public class GoodsListAdapter extends BaseAdapter {
    private Context context;
    private List<Goods> listItems;
    private ViewHolder mViewHolder;

    public GoodsListAdapter(Context context,List<Goods> listItems){
        this.context=context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        mViewHolder = new ViewHolder();
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.goods_item,null);
            mViewHolder.goodsItemTitle = (TextView)convertView.findViewById(R.id.goods_item_title);
            mViewHolder.goodsItemSeller = (TextView)convertView.findViewById(R.id.goods_item_seller);
            mViewHolder.goodsItemDetail = (TextView)convertView.findViewById(R.id.goods_item_detail);
            mViewHolder.goodsItemTime = (TextView)convertView.findViewById(R.id.goods_item_time);
            mViewHolder.goodsItemPrice = (TextView)convertView.findViewById(R.id.goods_item_price);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        mViewHolder.goodsItemTitle.setText(listItems.get(position).getGoodsName());
        mViewHolder.goodsItemSeller.setText(listItems.get(position).getSeller());
        mViewHolder.goodsItemDetail.setText(listItems.get(position).getDes());
        mViewHolder.goodsItemTime.setText(listItems.get(position).getUpdatedAt());
        mViewHolder.goodsItemPrice.setText(listItems.get(position).getPrice()+"");
        return convertView;
    }
    public static class ViewHolder{
        public TextView goodsItemTitle;
        public TextView goodsItemDetail;
        public TextView goodsItemSeller;
        public TextView goodsItemTime;
        public TextView goodsItemPrice;
    }
}
