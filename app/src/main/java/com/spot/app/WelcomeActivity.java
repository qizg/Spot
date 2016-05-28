package com.spot.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.spot.app.activity.HomeActivity;
import com.spot.app.activity.LoginActivity;

import com.spot.app.config.Constant;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //TODO:...各种跳转

        SharedPreferences sp_Info = this.getSharedPreferences(Constant.SP_INFO,
                Context.MODE_PRIVATE);

        boolean isLogin = sp_Info.getBoolean(Constant.IS_LOGIN, false);

//        if (!isLogin)
//            startActivity(new Intent(this, LoginActivity.class));
//        else
            startActivity(new Intent(this, HomeActivity.class));

        finish();

    }
}
