package com.example.sendwich;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sendwich.Regester.InfoActivity;
import com.example.sendwich.function.boolid;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private boolean saveLoginData;  //로그인 저장 여부

    //로그인 정보, 입력창
    private String id;
    private String pwd;
    private EditText idText, pwdText;

    private CheckBox checkBox;
    private Button loginBtn;

    private SharedPreferences appData;    //다이얼로그

    private Button join;
    private SignInButton googleloginbtn;  //구글 버튼 (미완성)

    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=123;
    private FirebaseAuth mAuth = null;
    private String TAG="mainTag";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar ab = getSupportActionBar();
        ab.hide();  //액션바 숨기기

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleloginbtn = findViewById(R.id.SignIn_Button);
        join = (Button) findViewById(R.id.registerbtn);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        loginBtn = (Button) findViewById(R.id.loginbtn);
        idText = (EditText) findViewById(R.id.Email);
        pwdText = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();   //파이어베이스 접근 권한

        appData = getSharedPreferences("appData", MODE_PRIVATE);    //로딩 다이얼로그
        load();

        if (saveLoginData) {
            idText.setText(id);
            pwdText.setText(pwd);
            checkBox.setChecked(saveLoginData);
        }


        //구글 로그인
        googleloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        //일반 로그인
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = idText.getText().toString().trim();
                String pwd = pwdText.getText().toString().trim();
                String email1 = idText.getText().toString();
                String pwd1 = pwdText.getText().toString();

                if (boolid.isNull(email1)&& boolid.isNull(pwd1)) {
                    mAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        save();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else
                                        {
                                        Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                        }
                                }
                            });
                }
                else
                    {
                    Toast.makeText(LoginActivity.this, "아이디 비밀번호를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        ///회원가입하러가기
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }



    //구글 로그인 인증부
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    // [END on_start_check_user]

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getApplicationContext(), "Google sign in Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_LONG).show();

                            // updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", idText.getText().toString().trim());
        editor.putString("PWD", pwdText.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }

}