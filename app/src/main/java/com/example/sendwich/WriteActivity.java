package com.example.sendwich;

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


import com.example.sendwich.write.Dictionary;
import com.example.sendwich.write.WriteAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class WriteActivity extends AppCompatActivity {
    public static final int PICK_FROM_MULTI_ALBUM = 4;

    private static final String TAG = "DocSnippets";

    private DocumentReference docRef;

    private ArrayList<Dictionary> mArrayList;
    private WriteAdapter mAdapter;
    private int count = -1;

    List<Uri> imgListUri;

    Uri imagePath1;

    private TextView delete;
    private Button addbtn;
    private ImageView back;
    private Button upload;

    private EditText str;
    private String msg;

    private ImageView addimage;

    //ArrayList<Posting> arrayList = new ArrayList<Posting>();
    //PostAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        //arrayAdapter = new PostAdapter();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        delete = (TextView) findViewById(R.id.deletebtn);

        str = findViewById(R.id.writetext);
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
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dictionary data = new Dictionary();
                mArrayList.add(data);

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_FROM_MULTI_ALBUM);

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

        upload = findViewById(R.id.uploadbtn);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = str.getText().toString();

                if (str.getText().toString().length() == 0) { //공백이면
                    Toast.makeText(getApplicationContext(), "글을 작성해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle get = getIntent().getExtras();
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if(document.exists())
                                    //Posting Post = new Posting(msg);
                                    db.collection("posts").document().set(msg);
                            }
                        }
                    });
                }
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

                    if(clipData.getItemCount() > 9) {
                        Toast.makeText(WriteActivity.this, "사진은 9장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    } else if (clipData.getItemCount() == 1) {   //사진 한 장일 때
                        imagePath1 = data.getData();
                        File f1 = new File(String.valueOf(imagePath1));
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath1);
                            addimage.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {    //아홉 장 이내
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Log.i("", String.valueOf(clipData.getItemAt(i).getUri()));
                            imgListUri.add(clipData.getItemAt(i).getUri());
                        }
                        //imagePath1 = getFileStreamPath(imgListUri.get(0));

                    }

                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }
}
