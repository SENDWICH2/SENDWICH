package com.example.sendwich.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendwich.R;


import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private ArrayList<Dictionary2> mList;

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        protected Button test;

        public ProfileViewHolder(View view) {
            super(view);
            this.test = (Button) view.findViewById(R.id.button2);
        }
    }

    public ProfileAdapter(ArrayList<Dictionary2> list) {
        this.mList = list;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_badge_item, viewGroup, false);

        ProfileViewHolder viewHolder = new ProfileViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder viewholder, int position) {

        // viewholder.test.setImageURI(mList.get(position).getTest());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
