package com.example.sendwich.Register;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.example.sendwich.MainActivity;
import com.example.sendwich.R;
import com.example.sendwich.function.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
//회원가입 액티비티
public class RegisterActivity extends AppCompatActivity {

    //카테고리
    private String[] mCategory = {"한식","영화","공원","빵집","병원","전자상가",
            "대형마트","시장","관광명소","핫플레이스","일식","양식"};        //카테고리 목록(나중에 userdata에 정리)
    private boolean[] mCategorySelected = new boolean[mCategory.length];  //카테고리 선택 여부 배열
    private TextView mTvCategory;
    private AlertDialog mCategorySelectDialog;
    private String categorysum="";               //카테고리 선택 데이터 저장

    //이미지 저장
    public static final int PICK_FROM_ALBUM = 1;
    private Uri imageUri;
    private String pathUri;
    private File tempFile;

    //데이터베이스
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;

    private EditText username, useremail, userpw;
    private Button signup;
    private ImageButton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("ProgressDialog running...");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        // Authentication, Database, Storage 초기화
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mTvCategory = (TextView) findViewById(R.id.tv_category);

        // 변수 할당
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        userpw = findViewById(R.id.userpassword);
        profile = findViewById(R.id.memberjoin_iv);
        signup = findViewById(R.id.Meberjoin_bt);



        //카테고리 다중 선택 다이얼로그
        mTvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategorySelectDialog.show();
            }
        });
        mCategorySelectDialog = new AlertDialog.Builder(RegisterActivity.this )
                .setMultiChoiceItems(mCategory, mCategorySelected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) { //선택된 부분을 배열로 하여 저장
                        mCategorySelected[i] = b;

                    }
                })
                .setTitle("title")
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {         //이를 바탕으로 string에 텍스트로 저장

                                for (int i = 0; i < mCategory.length ; i++)
                                {
                                    if (mCategorySelected[i])
                                    {
                                        categorysum = categorysum +". " + mCategory[i];
                                    }
                                }
                            }
                        })
                .setNegativeButton("취소",null)
                .create();




        signup.setOnClickListener(new View.OnClickListener() {  //signup함수 실행
            @Override
            public void onClick(View v) {
                progressDialog.show();
                signup();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() { //gotoAlbum함수 실행
            @Override
            public void onClick(View v) {
                gotoAlbum();
            }
        });
    }

    // 앨범 메소드
    private void gotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { // 코드가 틀릴경우
            Toast.makeText(RegisterActivity.this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Toast.makeText(RegisterActivity.this, "삭제 성공.", Toast.LENGTH_SHORT).show();
                        tempFile = null;
                    }
                }
            }
            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM: { // 코드 일치
                // Uri
                imageUri = data.getData();
                pathUri = getPath(data.getData());
                profile.setImageURI(imageUri); // 이미지 띄움
                break;
            }
        }
    }



    // uri 절대경로 가져오기
    public String getPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);
    }


    // 회원가입 로직
    private void signup() {
        String name = username.getText().toString();
        String email = useremail.getText().toString();
        String pw = userpw.getText().toString();


        // 프로필사진,이름,이메일,비밀번호 중 하나라도 비었으면 return
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(pw) || profile == null || pathUri==null) {
            Toast.makeText(RegisterActivity.this, "정보를 바르게 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Authentication에 email,pw 생성
            mAuth.createUserWithEmailAndPassword(email, pw)
                    .addOnCompleteListener
                            (RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) { // 회원가입 성공시
                                        // uid에 task, 선택된 사진을 file에 할당
                                        final String uid = task.getResult().getUser().getUid();
                                        final Uri file = Uri.fromFile(new File(pathUri)); // path

                                        // 스토리지에 방 생성 후 선택한 이미지 넣음
                                        StorageReference storageReference = mStorage.getReference()
                                                .child("usersprofileImages").child("uid/"+file.getLastPathSegment());
                                        storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                final Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                                                while (!imageUrl.isComplete()) ;

                                                //동시에 db에 회원정보 입력
                                                UserModel userModel = new UserModel();
                                                userModel.userintroduce = "정보를 입력해 주세요";
                                                userModel.follow = "0";
                                                userModel.follower = "0";
                                                userModel.postnum = "0";
                                                userModel.category = categorysum;
                                                userModel.userName = name;
                                                userModel.uid = uid;
                                                userModel.profileImageUrl = imageUrl.getResult().toString();
                                                userModel.useremail = email;
                                                mDatabase.getReference().child("users").child(uid)
                                                        .setValue(userModel);
                                            }
                                        });

                                        Toast.makeText(RegisterActivity.this, "회원가입 성공.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        ///UID 넘기기
                                        intent.putExtra("UID",uid);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        if (task.getException() != null) { // 회원가입 실패시
                                            Toast.makeText(RegisterActivity.this, "회원가입 실패.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}