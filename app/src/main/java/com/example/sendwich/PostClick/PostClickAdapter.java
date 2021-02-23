package com.example.sendwich.PostClick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sendwich.R;

import java.util.ArrayList;

/*
 PostClickActivity에서 댓글 리스트뷰 어댑터
 */

public class PostClickAdapter extends BaseAdapter {
    private ArrayList<PostClickItem> postClickItemList = new ArrayList<PostClickItem>();

    public PostClickAdapter() {

    }

    @Override
    public int getCount() {
        return postClickItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int post = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_postclick_item, parent, false);
        }

        TextView idView = (TextView)convertView.findViewById(R.id.id);
        TextView textView = (TextView)convertView.findViewById(R.id.message);

        ImageView heart = (ImageView)convertView.findViewById(R.id.heart);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(heart != null) {
                    heart.setSelected(!heart.isSelected());
                }
            }
        });

        PostClickItem postClickItem = postClickItemList.get(position);

        idView.setText(postClickItem.getId());
        textView.setText(postClickItem.getMessage());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return postClickItemList.get(position);
    }

    public void addItem(String id, String message) {
        PostClickItem item = new PostClickItem();

        item.setId(id);
        item.setMessage(message);

        postClickItemList.add(item);
    }
}
