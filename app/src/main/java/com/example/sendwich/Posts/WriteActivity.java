package com.example.sendwich.Posts;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sendwich.R;
import com.example.sendwich.WritePhoto.WritePhotoActivity;
import com.example.sendwich.write.Dictionary;
import com.example.sendwich.write.WriteAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 - 게시물 작성하는 화면
 글과 사진을 적어야 게시물 등록이 가능.
 아직 로그인을 안해서 아이디, 이름, UID 등 받아와야 함.
 사진은 등록하면 작게 미리보기 화면까지 구현했으나 X표시 delete 기능은 아직 미구현.

 사진은 최대 9장까지 가능
 사진 이름 저장하는 방법을 바꿔야 할 듯함.
 */

public class WriteActivity extends AppCompatActivity {
    public static final int PICK_FROM_MULTI_ALBUM = 4;

    private static final String TAG = "DocSnippets";

    private int count = 0;

    private String time1;
    private int number;


    Uri imagePath1;

    private ImageView delete;   //사진 삭제 아직 미구현
    private Button addbtn;  // 사진 + 추가하기 버튼
    private ImageView back; //뒤로가기 버튼
    private Button upload;  //업로드하기 버튼
    private ImageView image;    //선택한 사진 보여주는 이미지뷰
    private Button canvas;  //대표사진 만들기 버튼

    private EditText edit;  // 사용자가 입력한 텍스트 받아오고
    private String msg; //저장하기
    private ArrayList<Dictionary> mArrayList;
    private WriteAdapter mAdapter;

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://flugmediaworks-dba3f.appspot.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        delete = findViewById(R.id.deletebtn);

        edit = findViewById(R.id.writetext);
        Bundle get = getIntent().getExtras();

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
        LinearLayoutManager mLinearLayoutManaget = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManaget);
        mLinearLayoutManaget.setOrientation(LinearLayoutManager.HORIZONTAL); //가로 리스트뷰

        mArrayList = new ArrayList<>();

        mAdapter = new WriteAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManaget.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        addbtn = (Button) findViewById(R.id.addpicbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {  //사진 추가 버튼
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_FROM_MULTI_ALBUM);

            }
        });

        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() { //뒤로가기 버튼
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        upload = findViewById(R.id.uploadbtn);
        upload.setOnClickListener(new View.OnClickListener() {  //게시물 업로드 하기 버튼
            @Override
            public void onClick(View v) {
                msg = edit.getText().toString();

                if (edit.getText().toString().length() == 0) { //공백이면
                    Toast.makeText(getApplicationContext(), "글을 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else if (number == 0) { //사진을 안올렸다면
                    Toast.makeText(getApplicationContext(), "사진을 한 장 이상 업로드 해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Posting Post = new Posting("tmddus2123", "이승연", msg, time1, number, 3);
                    databaseReference.child("posts").child("").push().setValue(Post);

                    databaseReference = FirebaseDatabase.getInstance().getReference("posts");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot datas : dataSnapshot.getChildren()) {
                                long i = datas.getChildrenCount();
                                Log.d(TAG, String.valueOf(i));

                                }
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    finish();
                    finish();
                    Intent intent = new Intent(WriteActivity.this, PostsActivity.class);
                    startActivity(intent);
                }
            }
        });
        image = findViewById(R.id.prephoto);

        canvas = findViewById(R.id.make);
        canvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this, WritePhotoActivity.class);
                startActivity(intent);


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == PICK_FROM_MULTI_ALBUM) {
            if (data == null) {

            } else {
                if (data.getClipData() == null) {
                    Toast.makeText(this, "다중선택이 불가한 기기입니다.", Toast.LENGTH_LONG).show();
                } else {
                    ClipData clipData = data.getClipData();
                    Log.i("clipdata", String.valueOf(clipData.getItemCount()));
                    number = clipData.getItemCount();

                    if (clipData.getItemCount() > 9) {
                        Toast.makeText(WriteActivity.this, "사진은 9장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    } else if (clipData.getItemCount() == 1) {   //사진 한 장일 때
                        imagePath1 = data.getData();
                        File f1 = new File(String.valueOf(imagePath1));
                        Log.d(TAG, "uri => " + String.valueOf(f1));
                        try {
                            count++;
                            Dictionary data1 = new Dictionary(clipData.getItemAt(0).getUri());
                            mArrayList.add(data1);
                            mAdapter.notifyDataSetChanged();
                            uploadFile();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {    //아홉 장 이내
                        Log.d(TAG,"사진 갯수 => " + clipData.getItemCount());
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            imagePath1 = clipData.getItemAt(i).getUri();
                            count++;
                            Dictionary data1 = new Dictionary(clipData.getItemAt(i).getUri());
                            mArrayList.add(data1);
                            mAdapter.notifyDataSetChanged();
                            uploadFile();

                        }
                        Log.d(TAG, "전체 이미지 경로 => " + clipData);
                        count = 0;
                    }
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void uploadFile() { //사진 업로드 함수
        if (imagePath1 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss"); // 사진 이름 변경해서 저장
            Date now = new Date();
            time1 = formatter.format(now);
            String filename = time1 + "_" + count + ".png";

            StorageReference storageRef = storage.getReferenceFromUrl("gs://flugmediaworks-dba3f.appspot.com").child("photo/" + filename);

            storageRef.putFile(imagePath1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

}
