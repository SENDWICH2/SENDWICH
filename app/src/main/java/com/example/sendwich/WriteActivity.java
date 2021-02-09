package com.example.sendwich;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendwich.write.Dictionary;
import com.example.sendwich.write.WriteAdapter;

import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity {


    private ArrayList<Dictionary> mArrayList;
    private WriteAdapter mAdapter;
    private int count = -1;

    private TextView delete;
    private Button addbtn;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        delete = (TextView)findViewById(R.id.deletebtn);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recycler1);
        LinearLayoutManager mLinearLayoutManaget = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManaget);
        mLinearLayoutManaget.setOrientation(LinearLayoutManager.HORIZONTAL);



        mArrayList = new ArrayList<>();

        mAdapter = new WriteAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManaget.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        addbtn = (Button)findViewById(R.id.addpicbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dictionary data = new Dictionary();
                mArrayList.add(data);

                mAdapter.notifyDataSetChanged();
            }
        });

        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }
}
