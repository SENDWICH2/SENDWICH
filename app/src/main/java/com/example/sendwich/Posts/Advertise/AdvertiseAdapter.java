package com.example.sendwich.Posts.Advertise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendwich.R;

import java.util.ArrayList;
import java.util.List;

public class AdvertiseAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();

    }
    class ViewHolder {
        public TextView tv_userid;
    }

    public AdvertiseAdapter(Context context, ArrayList list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.list_posts_item, parent, false);
        }
        final Post post= (Post) list.get(position);
        viewHolder = new ViewHolder();
        viewHolder.tv_userid = (TextView)convertView.findViewById(R.id.userid);

        viewHolder.tv_userid.setText(post.getUserID());
        viewHolder.tv_userid.setTag(post.getUserID());

        return convertView;
    }
}