package com.example.sendwich.Intro;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sendwich.R;

//오프닝 화면 띄우기
public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }
        };
        timerThread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
