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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class PhoneVerifyActivity extends AppCompatActivity {

    private EditText resetPhoneNum,resetCode,newPassword,newPswConfirm;
    private Button resetGetCode,resetNext;
    private Boolean runningThree = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        resetPhoneNum = (EditText)findViewById(R.id.reset_phone_num);
        resetCode = (EditText)findViewById(R.id.reset_code);
        resetGetCode = (Button)findViewById(R.id.reset_btn_get_code);

        newPassword = (EditText)findViewById(R.id.new_psw);
        newPswConfirm = (EditText)findViewById(R.id.psw_confirm);
        resetNext = (Button)findViewById(R.id.reset_btn_next);
        resetGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(runningThree){
                }else {
                    downTimer.start();
                }
                sendToGetCode(resetPhoneNum.getText().toString());
            }
        });
        resetNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEffectivePsw()){
                    BmobUser.resetPasswordBySMSCode(resetCode.getText().toString(), newPassword.getText().toString()
                            , new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null){
                                        if(BmobUser.getCurrentUser(MyUser.class) == null){
                                            Log.i("smile current:","null");
                                        }else{
                                            Log.i("smile current:",BmobUser.getCurrentUser().getUsername());
                                        }
                                        Intent intent = new Intent(PhoneVerifyActivity.this,SetNewPasswordActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(getApplication(),"错误信息："+e.getLocalizedMessage(),Toast.LENGTH_LONG);
                                    }
                                }
                            });
                }
            }
        });
    }

    private void sendToGetCode(String phoneNumber){
        BmobSMS.requestSMSCode(phoneNumber, "欢迎来到王者荣耀", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e == null){
                    Log.i("smile","验证码发送成功，短息id"+integer);
                }else {
                    Log.i("smile error:",e.getLocalizedMessage());
                }
            }
        });
    }

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000,1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            String second = (l/1000)+"";
            resetGetCode.setBackgroundResource(R.drawable.get_code_pressed);
            resetGetCode.setText(second+"秒");
            resetGetCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            runningThree = false;
            resetGetCode.setText(R.string.resend);
            resetGetCode.setBackgroundResource(R.drawable.shape_button);
            resetGetCode.setEnabled(true);
        }
    };

    private boolean isEffectivePsw(){
        String psw = newPassword.getText().toString();
        String pswConfirm = newPswConfirm.getText().toString();
        if( psw.equals("") || pswConfirm.equals("") ){
            Toast.makeText(getApplicationContext(),"密码或重复密码不能为空",Toast.LENGTH_LONG).show();
            return false;
        }else if( !(psw.equals(pswConfirm))){
            Toast.makeText(getApplicationContext(),"两次输入密码不相同",Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }


}
