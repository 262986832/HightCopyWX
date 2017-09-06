package com.idisfkj.hightcopywx.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.ui.model.LoginModel;
import com.idisfkj.hightcopywx.ui.model.LoginModelImp;
import com.idisfkj.hightcopywx.ui.model.RegisterModel;
import com.idisfkj.hightcopywx.ui.model.RegisterModelImp;
import com.idisfkj.hightcopywx.ui.widget.LoginActivity;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎界面
 * Created by idisfkj on 16/4/18.
 * Email : idisfkj@qq.com.
 */
public class WelcomeActivity extends Activity implements LoginModel.requestLoginListener {
    private Intent intent;
    private RegisterModel mRegisterModel;
    private LoginModel mloginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        mRegisterModel = new RegisterModelImp();
        mloginModel = new LoginModelImp();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (SharedPreferencesManager.getString("password", "") == "" &&
                        SharedPreferencesManager.getString("userPhone", "") == "") {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    startLogin();
                }

            }
        }, 300);
    }

    private void startLogin() {
        mloginModel.requestLogin(this, SharedPreferencesManager.getString("userPhone", ""),
                SharedPreferencesManager.getString("password", ""), SharedPreferencesManager.getString("regId", ""));
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onLoginSucceed() {
        intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String msg) {

    }
}
