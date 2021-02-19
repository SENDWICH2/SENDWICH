package com.example.sendwich;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.sendwich.PostClick.PostClickAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sendwich.PostClick.setListViewHeight.setListViewHeightBasedOnChildren;

public class PostClickActivity extends AppCompatActivity {

    private String name, time, text, picname, like;

    TextView mid, mtxt;

    ImageView back;
    ImageView msg;
    EditText write;
    String writestr;
    ImageView sharebtn;

    ImageView heart;

    private String keyname;

    private static final String TAG = "DocSnippets";

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postclick);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        Intent getintent = getIntent();
        keyname = getintent.getStringExtra("게시물키값");
        Log.d(TAG, "게시물키값 => " + keyname);

        mid = findViewById(R.id.myid);
        mtxt = findViewById(R.id.ttext);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    String key = datas.getKey();
                    if(key.equals(keyname)) { //같은 키만
                        mid.setText(datas.child("id").getValue(String.class));
                        mtxt.setText(datas.child("text").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        heart = (ImageView) findViewById(R.id.emptyheart);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heart != null) {
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
                if (writestr.length() == 0) {// 공백이면
                    Toast.makeText(getApplicationContext(), "빈칸", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.addItem("asdf", writestr);
                    listview.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(listview);
                }
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(write.getWindowToken(), 0);
                write.setText(null);
            }
        });
        listview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listview);

        sharebtn = findViewById(R.id.share);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("Text/*");

                intent.putExtra(Intent.EXTRA_STREAM,Uri.parse("gs://flugmediaworks-dba3f.appspot.com/photo/20210203_0449_1.png"));
                Intent chooser = Intent.createChooser(intent, "게시물 공유하기 테스트");
                startActivity(chooser);
            }
        });
    }
    public class User_post{
        private String userID;
        private String userNAME;
        private String userLIKE;
        private String userTIME;
        private String userPIN;
        private String userTEXT;
    }
}
