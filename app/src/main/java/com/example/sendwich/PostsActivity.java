package com.example.sendwich;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sendwich.Posts.VPAdapter;
import com.google.android.material.tabs.TabLayout;

public class PostsActivity extends AppCompatActivity {

    ImageView mPostbtn;
    ImageView mbtn;
    EditText location;
    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        mPostbtn = findViewById(R.id.postbtn);
        mbtn = findViewById(R.id.profileimage);

        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tap);
        tab.setupWithViewPager(vp);

        Intent getIntent = getIntent();
        String loc = getIntent.getStringExtra("주소");
        Log.d(TAG, "주소 --> "+ loc);
        location = findViewById(R.id.place);
        location.setText(loc);

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
                finish();
            }
        });

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기
    }
}

