package com.example.chenxuanjin.auction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.bmob.v3.BmobUser;

public class SetupActivity extends AppCompatActivity {

    private Button signOutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        signOutBtn = (Button)findViewById(R.id.btn_sign_out);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();
                Intent intent = new Intent(SetupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
