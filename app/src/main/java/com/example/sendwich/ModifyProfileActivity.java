package com.example.sendwich;

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
import com.example.sendwich.Profile.Dictionary2;
import com.example.sendwich.Profile.ProfileAdapter;
import com.example.sendwich.Regester.InfoActivity;
import com.example.sendwich.function.ImageLoadTask;
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
import java.util.ArrayList;

import static com.example.sendwich.Regester.InfoActivity.PICK_FROM_ALBUM;

public class ModifyProfileActivity extends AppCompatActivity {
    private String[] mCategory = {"한식","영화","공원","빵집","병원","전자상가","대형마트","시장","관광명소","핫플레이스","일식","양식"};
    private boolean[] mCategorySelected = new boolean[mCategory.length];
    private TextView mTvCategory;
    private AlertDialog mCategorySelectDialog;
    private String categorysum="";
    private  Uri file;
    private String pathUri;
    private ArrayList<Dictionary2> mArrayList;
    private ProfileAdapter mAdapter;
    private TextView Username, Useremail, Userintroduce, Userid;
    private TextView Postnum, Follownum, Followingnum;
    private Button modify;
    private ImageView back,  check;
    private ImageView profileimg;
    private Button sticker;
    private FirebaseStorage mStorage;
    private FirebaseDatabase mDatabase;
    private DatabaseReference rDatabase;
    private Uri imageUri;
    private File tempFile;
    private String Urlforme;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);





        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mStorage = FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        rDatabase = FirebaseDatabase.getInstance().getReference();
        mTvCategory = (TextView) findViewById(R.id.tv_category);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        profileimg = findViewById(R.id.profileimage);
        back = findViewById(R.id.backbtn);
        check = findViewById(R.id.checkbtn);
        Username = findViewById(R.id.usernamechange);
        Useremail = findViewById(R.id.useremailchange);
        Userintroduce = findViewById(R.id.userintroducetext);
        modify = findViewById(R.id.modifybtn);

        mTvCategory = (TextView) findViewById(R.id.tv_category);
        mTvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
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





        String uid = user.getUid();

        readUser(uid);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 선택된 사진을 file에 할당
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
                            userModel.category = categorysum;

                            // database에 저장
                            mDatabase.getReference().child("users").child(uid).child("userintroduce")
                                    .setValue(userModel.userintroduce);
                            mDatabase.getReference().child("users").child(uid).child("userName")
                                    .setValue(userModel.userName);
                            mDatabase.getReference().child("users").child(uid).child("profileImageUrl")
                                    .setValue(userModel.profileImageUrl);

                            Toast.makeText(ModifyProfileActivity .this,"프로필이 저장되었습니다.",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else
                {
                    Toast.makeText(ModifyProfileActivity .this,"프로필 사진을 변경해주세요.",Toast.LENGTH_SHORT).show();

                }
                //파이어베이스에 수정내용 저장하기
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



    private void readUser(String userid){
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
