package com.example.sendwich;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Button register;

    private EditText mIDview;
    private EditText mPasswordview;
    private EditText mPasswordcheckview;

    private String id = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mIDview = findViewById(R.id.id);
        mPasswordview = findViewById(R.id.password);
        mPasswordcheckview = findViewById(R.id.passwordcheck);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        register = findViewById(R.id.loginbtn);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    if (mIDview.getText().toString().isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "ID를 입력해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mPasswordview.getText().toString().isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Password를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mPasswordcheckview.getText().toString().isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Password check를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!mPasswordview.getText().toString().equals((mPasswordcheckview.getText().toString()))) {
                        Toast.makeText(RegisterActivity.this, "Password가 틀립니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                mAuth.createUserWithEmailAndPassword(mIDview.getText().toString(), mPasswordview.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        Toast.makeText(RegisterActivity.this, "이미 존재하는 ID 입니다.", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this,"다시 확인해주세요." ,Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Toast.makeText(RegisterActivity.this, "가입 성공" ,Toast.LENGTH_SHORT).show();


                                }
                            }
                        });
            }
        });
    }
}