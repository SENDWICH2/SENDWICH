package com.example.sendwich;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.sendwich.Profile.Dictionary2;
import com.example.sendwich.Profile.ProfileAdapter;
import com.example.sendwich.function.ImageLoadTask;
import com.example.sendwich.function.UserModel;
import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<Dictionary2> mArrayList;
    private ProfileAdapter mAdapter;
    private int count = -1;
    private TextView Userid, Useremail, Profilecat;
    private TextView Postnum, Follownum, Followingnum;
    private Button modify;
    private ImageView back, profileimg;
    private Button sticker;
    private DatabaseReference mDatabase;// ...
    private TextView userintrotext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //데이터베이스
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

       RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recycler2);
        LinearLayoutManager mLinearLayoutManaget = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManaget);
        mLinearLayoutManaget.setOrientation(LinearLayoutManager.HORIZONTAL);

        String uid = user.getUid(); //유저 정보 읽기
        readUser(uid);

        Userid = findViewById(R.id.Idprof);
        Useremail = findViewById(R.id.Emailprof);
        profileimg = findViewById(R.id.profileimage);
        Profilecat = findViewById(R.id.profilecategory);
        Postnum = findViewById(R.id.postnum);
        Follownum = findViewById(R.id.follownum);
        Followingnum = findViewById(R.id.followingnum);
        userintrotext = findViewById(R.id.userintroducetext);
        modify = findViewById(R.id.modifybtn);

        //수정하기
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ModifyProfileActivity.class);
                startActivity(intent);
                finish();

            }
        });

        //뒤로가기
        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //유저 정보 읽기
    private void readUser(String userid){
        mDatabase.child("users").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(UserModel.class) != null){

                    //데이터베이스 참조부
                    UserModel ID = dataSnapshot.getValue(UserModel.class);
                    Log.w("FireBaseData", "getData" + ID.toString());
                    Userid.setText(ID.getUserName()+"님");
                    Useremail.setText(ID.getusermail());
                    Profilecat.setText("관심 카테고리:"+ID.getCategory());
                    Postnum.setText(ID.getpost());
                    Follownum.setText(ID.getfollow());
                    Followingnum.setText(ID.getfollower());
                    userintrotext.setText(ID.getUserintroduce());

                    Glide.with(getApplicationContext())
                            .load(ID.getprofileImageUrl())
                            .apply(new RequestOptions()
                            .signature(new ObjectKey("signature string"))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            ).into((ImageView)findViewById(R.id.profileimage));



                } else {
                    Toast.makeText(ProfileActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
