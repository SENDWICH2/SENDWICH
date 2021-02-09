package com.example.sendwich.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sendwich.R;

import java.util.ArrayList;

public class StickerAdapter extends BaseAdapter {
    ArrayList<StickerItem> items = new ArrayList<StickerItem>();
    Context context;

    public void addItem(StickerItem item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        StickerItem stickerItem = items.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_dialog_sticker_item, parent,false);
        }

        TextView nameText = convertView.findViewById(R.id.stickername);
        //TextView imgText = convertView.findViewsWithText(R.id.stickerimg);

        nameText.setText(stickerItem.getName());

        return convertView;
    }
}
