package com.example.sendwich.PostClick;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.sendwich.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageSliderAdapter extends PagerAdapter {
    int[] images={};
    Context context;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final String TAG = "DocSnippets";

    ImageSliderAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_postclick, container, false);
        ImageView imageView1 = (ImageView)view.findViewById(R.id.viewpager);

        //StorageReference storageRef = storage.getReferenceFromUrl("gs://flugmediaworks-dba3f.appspot.com").child("photo/")
        //문서번호를 받아와야 함,,,

        imageView1.setImageResource(images[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }

}
