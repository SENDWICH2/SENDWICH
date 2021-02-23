package com.example.sendwich.Posts.Select;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

/*
 사진 띄우기 위한 Glide 모듈 추가
 */

@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registray){
        registray.append(StorageReference.class, InputStream.class,
                new FirebaseImageLoader.Factory());
    }
}
