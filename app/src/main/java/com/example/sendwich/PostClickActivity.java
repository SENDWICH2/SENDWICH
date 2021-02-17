package com.example.sendwich;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sendwich.PostClick.PostClickAdapter;
import com.example.sendwich.PostClick.setListViewHeight;

import static com.example.sendwich.PostClick.setListViewHeight.setListViewHeightBasedOnChildren;

public class PostClickActivity extends AppCompatActivity {

    ImageView back;
    ImageView msg;
    EditText write;
    String writestr;

    ImageView heart;

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

        heart = (ImageView)findViewById(R.id.emptyheart);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(heart != null) {
                    heart.setSelected(!heart.isSelected());
                }
            }
        });

        write = findViewById(R.id.writetext);

        msg = findViewById(R.id.writebtn);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writestr = write.getText().toString();
                if(writestr.length() == 0) {// 공백이면
                    Toast.makeText(getApplicationContext(), "빈칸", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.addItem("asdf", writestr);
                    listview.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(listview);
                }
                InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(write.getWindowToken(), 0);
                write.setText(null);
            }
        });
        listview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listview);
    }
}
