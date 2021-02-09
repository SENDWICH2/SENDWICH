package com.example.sendwich;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ModifyProfileActivity extends AppCompatActivity {

    ImageView back, check;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        back = findViewById(R.id.backbtn);
        check = findViewById(R.id.checkbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파이어베이스에 수정내용 저장하기
                finish();
            }
        });
    }
}
