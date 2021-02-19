package com.example.sendwich.Posts.Combine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sendwich.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CombineAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final String TAG = "DocSnippets";
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        public TextView tv_userid;
        public ImageView tv_thumbnail;
    }

    public CombineAdapter(Context context, ArrayList list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.list_posts_item, parent, false);
        }
        final Post2 post2 = (Post2) list.get(position);
        viewHolder = new ViewHolder();
        viewHolder.tv_userid = (TextView) convertView.findViewById(R.id.userid);
        viewHolder.tv_thumbnail = (ImageView)convertView.findViewById(R.id.picture);

        StorageReference sotrageRef = storage.getReferenceFromUrl("gs://flugmediaworks-dba3f.appspot.com").child("photo/" + post2.getThumbnailName() + "_1.png");

        Glide.with(context)
                .load(sotrageRef)
                .apply(new RequestOptions())
                .into(viewHolder.tv_thumbnail);

        viewHolder.tv_userid.setText(post2.getUserID());
        viewHolder.tv_userid.setTag(post2.getUserID());

        return convertView;
    }

}