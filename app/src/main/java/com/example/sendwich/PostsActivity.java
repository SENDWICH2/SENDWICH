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

/*
 - 게시물 화면
 뷰페이저로 3개의 탭을 구현.
 선택은 일반 사용자가 올린 게시물만 보는 탭.
 통합은 비즈니스와 일반 사용자 모두 올린 게시물을 보는 탭.
 HOT 위치는 비즈니스 사용자가 올린 게시물만 보는 탭.

 그러나 아직 나누지 못했음. 로그인을 해서 일반인지 비즈니스인지 검사하고 분류하면 될 것.
 메인 화면에서 현재 내 위치 값을 받아와서 위에 EditText에 출력해야함. 그리고
 EditText 클릭 시 위치 바꿀 수 있도록 함.
 */

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

