package com.example.sendwich;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sendwich.PostClick.PostClickAdapter;
import com.example.sendwich.PostClick.setListViewHeight;

import static com.example.sendwich.PostClick.setListViewHeight.setListViewHeightBasedOnChildren;

public class PostClickActivity extends AppCompatActivity {

    ImageView back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postclick);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        back = findViewById(R.id.backbtn);  //뒤로가기
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ListView listview;
        PostClickAdapter adapter;

        adapter = new PostClickAdapter();

        listview = (ListView)findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        adapter.addItem("1111","가나다라마바사아자카타파하");
        adapter.addItem("2222","가나다라마바사아자카타파하");
        adapter.addItem("3333","가나다라마바사아자카타파하");
        adapter.addItem("1111","가나다라마바사아자카타파하");
        adapter.addItem("1111","가나다라마바사아자카타파하");
        adapter.addItem("1111","가나다라마바사아자카타파하");
        adapter.addItem("1111","가나다라마바사아자카타파하");
        
        listview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listview);
    }
}
