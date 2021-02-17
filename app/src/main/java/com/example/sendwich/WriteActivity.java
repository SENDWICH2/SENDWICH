package com.example.sendwich;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sendwich.Posts.Posting;
import com.example.sendwich.write.Dictionary;
import com.example.sendwich.write.WriteAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteActivity extends AppCompatActivity {
    public static final int PICK_FROM_MULTI_ALBUM = 4;

    private static final String TAG = "DocSnippets";

    private int count = -1;


    Uri imagePath1;

    private ImageView delete;
    private Button addbtn;
    private ImageView back;
    private Button upload;
    private ImageView image;

    private EditText edit;
    private String msg;

    private ImageView addimage;

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
        mLinearLayoutManaget.setOrientation(LinearLayoutManager.HORIZONTAL);

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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM월 dd일");
        Date time = new Date();
        String time1 = format1.format(time);

        upload = findViewById(R.id.uploadbtn);
        upload.setOnClickListener(new View.OnClickListener() {  //게시물 업로드 하기 버튼
            @Override
            public void onClick(View v) {
                msg = edit.getText().toString();

                if (edit.getText().toString().length() == 0) { //공백이면
                    Toast.makeText(getApplicationContext(), "글을 작성해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Posting Post = new Posting("tmddus2123", "이승연", msg, time1,"사진 이름", 3);
                    databaseReference.child("posts").child("").push().setValue(Post);
                    //databaseReference.child("posts").child("문서번호를 정하자").push().setValue(Post);
                    //databaseReference.child("posts").child("방금 생성한 문서 번호 넣기").child("PicNum").setValue();
                    finish();
                }
            }
        });
        image = findViewById(R.id.prephoto);
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

                    if (clipData.getItemCount() > 9) {
                        Toast.makeText(WriteActivity.this, "사진은 9장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    } else if (clipData.getItemCount() == 1) {   //사진 한 장일 때
                        imagePath1 = data.getData();
                        File f1 = new File(String.valueOf(imagePath1));
                        Log.d(TAG, "uri => " + String.valueOf(f1));
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath1);
                            uploadFile();
                            image.setImageBitmap(bitmap); //--> 사진 보이기
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
    private void uploadFile() {
        if (imagePath1 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss"); // 사진 이름 변경해서 저장
            Date now = new Date();
            String filename = formatter.format(now)+ "_" + count + "아이디" +".png";

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
