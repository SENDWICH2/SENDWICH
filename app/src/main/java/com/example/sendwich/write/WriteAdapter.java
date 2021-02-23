package com.example.sendwich.write;

/*
 - 게시물 작성하기에 여러장 사진 리스트뷰로 보여주는 어댑터
 */

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendwich.R;

import java.util.ArrayList;

public class WriteAdapter extends RecyclerView.Adapter<WriteAdapter.WriteViewHolder> {
    private ArrayList<Dictionary> mList;

    public class WriteViewHolder extends RecyclerView.ViewHolder {
        protected ImageView test;

        public WriteViewHolder(View view) {
            super(view);
            this.test = (ImageView) view.findViewById(R.id.prephoto);
        }
    }

    public WriteAdapter(ArrayList<Dictionary> list) {
        this.mList = list;
    }

    @Override
    public WriteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_write_item, viewGroup, false);

        WriteViewHolder viewHolder = new WriteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WriteViewHolder viewholder, int position) {

        viewholder.test.setImageURI(mList.get(position).getUri());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}