package com.example.sendwich;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendwich.Profile.Dictionary2;
import com.example.sendwich.Profile.ProfileAdapter;
import com.example.sendwich.Profile.StickerAdapter;
import com.example.sendwich.Profile.StickerItem;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<Dictionary2> mArrayList;
    private ProfileAdapter mAdapter;
    private int count = -1;

    private Button modify;
    private ImageView back;
    private Button sticker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

       RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recycler2);
        LinearLayoutManager mLinearLayoutManaget = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManaget);
        mLinearLayoutManaget.setOrientation(LinearLayoutManager.HORIZONTAL);

        modify = findViewById(R.id.modifybtn);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ModifyProfileActivity.class);
                startActivity(intent);
            }
        });

        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

/*
        mArrayList = new ArrayList<>();

        mAdapter = new ProfileAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManaget.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Dictionary2 data = new Dictionary2();
        mArrayList.add(data);
        mArrayList.add(data);
        mArrayList.add(data);
        mArrayList.add(data);
        mArrayList.add(data);

        mAdapter.notifyDataSetChanged();

        sticker = findViewById(R.id.stickerbtn);

        ListView listView = findViewById(R.id.gridsticker);
        StickerAdapter adapter = new StickerAdapter();

        adapter.addItem(new StickerItem("asdf", "asdf"));
        adapter.addItem(new StickerItem("asdf", "asdf"));
        adapter.addItem(new StickerItem("asdf", "asdf"));
        adapter.addItem(new StickerItem("asdf", "asdf"));
        adapter.addItem(new StickerItem("asdf", "asdf"));
        adapter.addItem(new StickerItem("asdf", "asdf"));
        adapter.addItem(new StickerItem("asdf", "asdf"));
        adapter.addItem(new StickerItem("asdf", "asdf"));
        ListView.setAdapter(adapter);

        sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

    }
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sticker, null);
        builder.setView(view);

        final GridView gridView = (GridView)view.findViewById(R.id.gridsticker);
        final AlertDialog dialog = builder.create();

        //SimpleAdapter simpleAdapter = new SimpleAdapter(ProfileActivity.this, StickerItem, R.layout.list_dialog_sticker_item);

        //gridView.setAdapter(simpleAdapter);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();*/
    }
}
