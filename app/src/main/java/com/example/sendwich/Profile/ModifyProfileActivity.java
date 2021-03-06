package com.example.sendwich.Profile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.sendwich.R;
import com.example.sendwich.function.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import static com.example.sendwich.Register.RegisterActivity.PICK_FROM_ALBUM;

public class ModifyProfileActivity extends AppCompatActivity {    //회원가입 액티비티와 유사함
    private String[] mCategory = {"한식","영화","공원","빵집","병원","전자상가","대형마트"
            ,"시장","관광명소","핫플레이스","일식","양식"}; //카테고리 (후에 userdata 병합)
    private boolean[] mCategorySelected = new boolean[mCategory.length]; //선택된 카테고리 배열에 저장
    private TextView mTvCategory;       //카테고리 선택 버튼
    private int selectedsum=0;          //선택한 카테고리 개수(FOR문필요)
    private AlertDialog mCategorySelectDialog; //다이얼로그 출력 변수
    private String categorysum="";     //최종 저장할 카테고리 변수
    private Uri file;                 //프로필 URI
    private String pathUri;            //URI 경로
    private TextView Username, Useremail, Userintroduce, Userid; //이름, 이메일, 자기소개
    private ImageView back,  check;    //뒤로가기, 확인 버튼
    private ImageView profileimg;       //프로필 이미지

    //데이터베이스
    private FirebaseStorage mStorage;
    private FirebaseDatabase mDatabase;
    private DatabaseReference rDatabase;
    private Uri imageUri;
    private File tempFile;
    private String Urlforme;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("ProgressDialog running...");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        //데이터베이스
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();    //현재 유저 승인
        mStorage = FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        rDatabase = FirebaseDatabase.getInstance().getReference();


        mTvCategory = (TextView) findViewById(R.id.tv_category);
        profileimg = findViewById(R.id.profileimage);
        back = findViewById(R.id.backbtn);
        check = findViewById(R.id.checkbtn);
        Username = findViewById(R.id.usernamechange);
        Useremail = findViewById(R.id.useremailchange);
        Userintroduce = findViewById(R.id.userintroducetext);
        mTvCategory = (TextView) findViewById(R.id.tv_category);
        mTvCategory.setOnClickListener(new View.OnClickListener() {
            @Override

            //카테고리 버튼 눌렀을때
            public void onClick(View view) {
                mCategorySelectDialog.show();
            }
        });
        mCategorySelectDialog = new AlertDialog.Builder(ModifyProfileActivity.this )
                .setMultiChoiceItems(mCategory, mCategorySelected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        mCategorySelected[i] = b;

                    }
                })
                .setTitle("title")
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for (int i = 0; i < mCategory.length ; i++)//for문을 통해 체크한 항목 추출
                                {
                                    if (mCategorySelected[i])
                                    {
                                        categorysum = categorysum +". " + mCategory[i];
                                        selectedsum+=1;
                                    }
                                }


                            }
                        })
                .setNegativeButton("취소",null)
                .create();



        String uid = user.getUid();    //유저 데이터 불러오기
        readUser(uid);

        //뒤로가기
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //회원정보 변경 확인
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택된 사진을 file에 할당
                progressDialog.show();
                //프로필 사진을 변경하는 경우
                if(pathUri!=null) {
                    file = Uri.fromFile(new File(pathUri)); // path
                    // 스토리지에 방생성 후 선택한 이미지 넣음
                    StorageReference storageReference = mStorage.getReference()
                            .child("usersprofileImages").child("uid/" + file.getLastPathSegment());

                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            final Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                            while (!imageUrl.isComplete()) ;

                            UserModel userModel = new UserModel();
                            userModel.userintroduce = Userintroduce.getText().toString();
                            userModel.userName = Username.getText().toString();
                            userModel.profileImageUrl = imageUrl.getResult().toString();


                            // database에 저장
                            mDatabase.getReference().child("users").child(uid).child("userintroduce")
                                    .setValue(userModel.userintroduce);
                            mDatabase.getReference().child("users").child(uid).child("userName")
                                    .setValue(userModel.userName);
                            mDatabase.getReference().child("users").child(uid).child("profileImageUrl")
                                    .setValue(userModel.profileImageUrl);
                            if(selectedsum!=0) {
                                mDatabase.getReference().child("users").child(uid).child("category")
                                        .setValue(userModel.category = categorysum);
                            }
                        }
                    });
                    Intent intent = new Intent(ModifyProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                //프로필 사진을 변경하지 않는 경우(조금더 깔끔한 정리 필요)
                else
                {
                    UserModel userModel = new UserModel();
                    userModel.userintroduce = Userintroduce.getText().toString();
                    userModel.userName = Username.getText().toString();

                    // database에 저장
                    mDatabase.getReference().child("users").child(uid).child("userintroduce")
                            .setValue(userModel.userintroduce);
                    mDatabase.getReference().child("users").child(uid).child("userName")
                            .setValue(userModel.userName);

                    if(selectedsum!=0) {
                        mDatabase.getReference().child("users").child(uid).child("category")
                                .setValue(userModel.category = categorysum);
                    }

                    Intent intent = new Intent(ModifyProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        profileimg.setOnClickListener(new View.OnClickListener() {
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

    // uri 절대경로 가져오기
    public String getPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) { // 코드가 틀릴경우
            Toast.makeText(ModifyProfileActivity.this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Toast.makeText(ModifyProfileActivity.this, "삭제 성공.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ModifyProfileActivity.this, "PICK_FROM_ALBUM photoUri : " + imageUri, Toast.LENGTH_SHORT).show();
                profileimg.setImageURI(imageUri); // 이미지 띄움
                break;
            }
        }
    }



    private void readUser(String userid){ //유저 데이터 가져오기 - 수정가능한상태로 출력
        rDatabase.child("users").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(UserModel.class) != null){

                    //데이터베이스 참조부
                    UserModel ID = dataSnapshot.getValue(UserModel.class);
                    Username.setText(ID.getUserName());
                    Useremail.setText(ID.getusermail());
                    Userintroduce.setText(ID.getUserintroduce());
                    Urlforme = ID.getprofileImageUrl();
                    Glide.with(getApplicationContext())
                            .load(ID.getprofileImageUrl())
                            .apply(new RequestOptions()
                                    .signature(new ObjectKey("signature string"))
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                            ).into((ImageView)findViewById(R.id.profileimage));



                } else {
                    Toast.makeText(ModifyProfileActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
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
