package com.idisfkj.hightcopywx.registerlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.registerlogin.presenter.LoginPresenter;
import com.idisfkj.hightcopywx.registerlogin.presenter.imp.LoginPresenterImp;
import com.idisfkj.hightcopywx.registerlogin.view.LoginView;
import com.idisfkj.hightcopywx.registerlogin.widget.LoginActivity;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 欢迎界面
 * Created by idisfkj on 16/4/18.
 * Email : idisfkj@qq.com.
 */
public class WelcomeActivity extends Activity implements LoginView {
    private Intent intent;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        mLoginPresenter=new LoginPresenterImp(this);
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
        App.ownMobile = SharedPreferencesManager.getString("userPhone", "");
        mLoginPresenter.login(SharedPreferencesManager.getString("userPhone", ""),
                SharedPreferencesManager.getString("password", ""), SharedPreferencesManager.getString("regId", ""));
    }

    @Override
    public void onBackPressed() {

    }


    @Override
    public void onLoginSuccess() {
        intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String error) {
        ToastUtils.showShort("网络异常,请检查网络");
    }
}
