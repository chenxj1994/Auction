package com.example.chenxuanjin.auction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.chenxuanjin.auction.bean.MyUser;
import com.example.chenxuanjin.auction.fragment.HomeFragment;
import com.example.chenxuanjin.auction.fragment.MeFragment;
import com.example.chenxuanjin.auction.fragment.PublishFragment;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;


public class MainActivity extends AppCompatActivity {

    private Fragment homeFragment ,meFragment ,publishFragment;
    private FragmentTransaction transaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        //BottomNavigation监听器
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = getFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(homeFragment == null){
                        homeFragment = new HomeFragment();
                    }
                    transaction.replace(R.id.content,homeFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    if(publishFragment == null){
                        publishFragment = new PublishFragment();
                    }
                    transaction.replace(R.id.content,publishFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    if(BmobUser.getCurrentUser(MyUser.class)!=null){
                        if(meFragment == null){
                            meFragment = new MeFragment();
                        }
                        transaction.replace(R.id.content,meFragment);
                        transaction.commit();
                    }else{
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefaultFragment();
        Bmob.initialize(this, "01e9a6c469cc886e33012d875d7804b4");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * 设置默认的Fragment
     */
    private void setDefaultFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction= fm.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.content,homeFragment);
        transaction.commit();
    }
}
