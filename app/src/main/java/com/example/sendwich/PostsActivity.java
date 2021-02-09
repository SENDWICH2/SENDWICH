package com.example.sendwich;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sendwich.Posts.VPAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {

    ImageButton mPostbtn;
    ImageButton mbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        mPostbtn = findViewById(R.id.postbtn);
        mbtn = findViewById(R.id.btn);

        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tap);
        tab.setupWithViewPager(vp);

        mPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostsActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostsActivity.this, PostClickActivity.class);
                startActivity(intent);
            }
        });

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기
    }
}

