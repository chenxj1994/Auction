package com.example.chenxuanjin.auction;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chenxuanjin.auction.bean.MyUser;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends AppCompatActivity {
    private String username,password,phoneNumber;
    private Boolean runningThree = false;
    private Button getCode;
    private EditText phone_number, identify_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone_number = (EditText)findViewById(R.id.phone_number);
        identify_code = (EditText)findViewById(R.id.identify_code);
        Button regis_btn = (Button)findViewById(R.id.registerBtn);
        getCode = (Button)findViewById(R.id.get_code);
        regis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = ((EditText)findViewById(R.id.reg_username)).getText().toString();
                password = ((EditText)findViewById(R.id.reg_password)).getText().toString();
                phoneNumber = phone_number.getText().toString();
                Log.i("harden2",username+" "+password);
                BmobSMS.verifySmsCode(phoneNumber, identify_code.getText().toString(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            registerRequest(username,password,phoneNumber);
                        }else {
                            Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(runningThree){
                }else {
                    downTimer.start();
                }
                sendToGetCode(phone_number.getText().toString());
            }
        });
    }

    public void registerRequest(String username,String password,String phomeNumber){
        final MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.setMobilePhoneNumber(phomeNumber);
        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e){
                if(e==null){
                    Toast.makeText(getApplicationContext(),"注册成功："+s.toString()
                            ,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"注册失败："+e.getLocalizedMessage()
                            ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 一键登录注册功能扩展版
     * @param username
     * @param password
     * @param phoneNumber
     */
    public void signOrLogin(String username,String password,String phoneNumber){
        final MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.setMobilePhoneNumber(phoneNumber);
        myUser.signOrLogin(identify_code.getText().toString(), new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e){
                if(e==null){
                    Toast.makeText(getApplicationContext(),"注册成功："+s.toString()
                            ,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"注册失败："+e.getLocalizedMessage()
                            ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendToGetCode(String phoneNumber){
        BmobSMS.requestSMSCode(phoneNumber, "欢迎来到王者荣耀", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                Log.i("smile","验证码发送成功");
            }
        });
    }
    private CountDownTimer downTimer = new CountDownTimer(60 * 1000,1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            String second = (l/1000)+"";
            getCode.setBackgroundResource(R.drawable.get_code_pressed);
            getCode.setText(second+"秒");
            getCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            runningThree = false;
            getCode.setText(R.string.resend);
            getCode.setBackgroundResource(R.drawable.shape_button);
            getCode.setEnabled(true);
        }
    };
}
