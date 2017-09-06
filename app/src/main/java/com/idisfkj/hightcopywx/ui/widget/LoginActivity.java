package com.idisfkj.hightcopywx.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.ui.presenter.LoginPresenter;
import com.idisfkj.hightcopywx.ui.presenter.LoginPresenterImp;
import com.idisfkj.hightcopywx.ui.view.LoginView;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by fvelement on 2017/9/6.
 */

public class LoginActivity extends Activity implements LoginView {
    @InjectView(R.id.register_picture)
    ImageView registerPicture;
    @InjectView(R.id.userPhone_et)
    EditText userPhone_et;
    @InjectView(R.id.userPassword_et)
    EditText userPassword_et;

    private LoginPresenter mloginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.inject(this);
        init();
    }
    private void init() {
        mloginPresenter=new LoginPresenterImp(this);
    }
    @OnClick({R.id.login_bt})
    public void onClick(View view) {
        mloginPresenter.login(userPhone_et.getText().toString().trim(),userPassword_et.getText().toString().trim(),
                SharedPreferencesManager.getString("regId",""));
    }
    @OnClick({R.id.userRegister})
    public void onuserRegisterClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginSuccess() {
        MiPushClient.setUserAccount(this,userPhone_et.getText().toString().trim(),userPhone_et.getText().toString().trim());
        MiPushClient.subscribe(this,userPhone_et.getText().toString().trim(),userPhone_et.getText().toString().trim());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String error) {
        ToastUtils.showShort(error);
    }
}
