package com.example.sendwich;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sendwich.Posts.VPAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tap);
        tab.setupWithViewPager(vp);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기
    }
}

