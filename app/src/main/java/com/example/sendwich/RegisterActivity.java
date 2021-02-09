package com.example.sendwich;
import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText email_join;
    private EditText pwd_join;
    private EditText pwdcon_join, username;
    private Button btn;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email_join = (EditText) findViewById(R.id.Email);
        pwd_join = (EditText) findViewById(R.id.password);
        pwdcon_join = (EditText) findViewById(R.id.passwordcheck);
        btn = (Button) findViewById(R.id.registercompletebtn);
        username = (EditText) findViewById(R.id.Name);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                String email = email_join.getText().toString().trim();
                String name = username.getText().toString().trim();
                String pwd = pwd_join.getText().toString().trim();
                String emailv = email_join.getText().toString();
                String pwdv = pwd_join.getText().toString();
                String pwdcon = pwdcon_join.getText().toString();
                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("가입중입니다...");
                if(pwdv.equals(pwdcon)) {
                    if(boolid.isNull(emailv)==false||boolid.isNull(pwdv)==false)
                    {
                        Toast.makeText(RegisterActivity.this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        Log.d(TAG,"여기부터데이터삽입");
                        Log.d(TAG,emailv);

                        firebaseAuth.createUserWithEmailAndPassword(emailv, pwdv)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(RegisterActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                });
                    }

                }
                else{
                    Toast.makeText(RegisterActivity.this, "비밀번호를 일치시켜주세요", Toast.LENGTH_SHORT).show();
                    return;

                }


            }
        });


    }
}