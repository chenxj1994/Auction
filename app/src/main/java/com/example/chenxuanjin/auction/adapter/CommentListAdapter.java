package com.example.chenxuanjin.auction.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenxuanjin.auction.R;
import com.example.chenxuanjin.auction.bean.Comment;
import com.example.chenxuanjin.auction.utils.SetImageViewUtil;

import java.util.List;

/**
 * Created by chenxuanjin on 2017/5/5.
 */

public class CommentListAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> listItems;
    private ViewHolder mViewHolder;

    public CommentListAdapter(Context context,List<Comment> listItems){
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item,null);
            mViewHolder.commentItemHeader = (ImageView)convertView.findViewById(R.id.comment_item_header);
            mViewHolder.commentItemUser = (TextView) convertView.findViewById(R.id.comment_item_user);
            mViewHolder.commentItemContent = (TextView)convertView.findViewById(R.id.comment_item_content);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }
        if(listItems.get(position).getAuthor().getHead()!= null){
            String headerUrl = listItems.get(position).getAuthor().getHead().getUrl();
            SetImageViewUtil.setImageToImageView(mViewHolder.commentItemHeader,headerUrl);
        }
        mViewHolder.commentItemUser.setText(listItems.get(position).getAuthor().getUsername());
        mViewHolder.commentItemContent.setText(listItems.get(position).getContent());
        return convertView;
    }
    private static class ViewHolder{
        private ImageView commentItemHeader;
        private TextView commentItemUser;
        private TextView commentItemContent;
    }
}
