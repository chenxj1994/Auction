package com.example.chenxuanjin.auction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenxuanjin.auction.bean.MyUser;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        TextView goto_registerBtn = (TextView) findViewById(R.id.goto_registerBtn);
        TextView passwordForget = (TextView)findViewById(R.id.password_forget);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest(username.getText().toString(),password.getText().toString());
            }
        });
        goto_registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        passwordForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,PhoneVerifyActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginRequest(String username, String password){
        BmobUser.loginByAccount(username, password, new LogInListener<MyUser>() {
            @Override
            public void done (MyUser user, BmobException e){
                if(user!=null){
                    Log.i("smile","用户登陆成功");
                    Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"登陆失败："+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void sendSMSCode(String phoneNumber){
        BmobSMS.requestSMSCode(phoneNumber,"陈小锦",new QueryListener<Integer>(){

            @Override
            public void done(Integer smsId,BmobException ex){
                if(ex==null){
                    Log.i("harden","短信id"+smsId);
                }
            }
        });
    }
}
