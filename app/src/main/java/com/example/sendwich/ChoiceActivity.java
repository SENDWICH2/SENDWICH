package com.example.sendwich;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sendwich.Regester.InfoActivity;

//미완성, 비즈니스 or 일반회원 구별 필요
public class ChoiceActivity extends AppCompatActivity {

    private Button business,commonuser;

    //온크리에이트 시작
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        business=(Button)findViewById(R.id.businessuser);
        commonuser=(Button)findViewById(R.id.commonuser);

        commonuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });


    }
}