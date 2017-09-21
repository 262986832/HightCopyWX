package com.idisfkj.hightcopywx.registerlogin.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.registerlogin.model.RoleAdapter;
import com.idisfkj.hightcopywx.registerlogin.presenter.LoginPresenter;
import com.idisfkj.hightcopywx.registerlogin.presenter.LoginPresenterImp;
import com.idisfkj.hightcopywx.registerlogin.view.LoginView;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;
import java.util.Map;

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
    @InjectView(R.id.spinnerRole)
    Spinner spinnerRole;

    private String mRoleID;

    private LoginPresenter mloginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        mloginPresenter = new LoginPresenterImp(this);

        List<Map<String, Object>> listMaps = RoleAdapter.getListMap();
        SimpleAdapter simpleAdapter = new SimpleAdapter
                (LoginActivity.this,
                        listMaps,
                        R.layout.login_role_item,
                        new String[]{"roleImg", "roleName"},
                        new int[]{R.id.imageview, R.id.textview});
        spinnerRole.setAdapter(simpleAdapter);
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String roleName = ((Map<String, Object>) spinnerRole.getItemAtPosition(position))
                        .get("roleName").toString(); //
                mRoleID = ((Map<String, Object>) spinnerRole.getItemAtPosition(position))
                        .get("roleID").toString(); //
                SharedPreferencesManager.putString("RoleID",mRoleID).commit();
                setTitle(roleName);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.login_bt})
    public void onClick(View view) {
        mloginPresenter.login(userPhone_et.getText().toString().trim(), userPassword_et.getText().toString().trim(), mRoleID);
    }

    @OnClick({R.id.userRegister})
    public void onuserRegisterClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginSuccess() {
        App.ownMobile = userPhone_et.getText().toString().trim();
        SharedPreferencesManager.putString("roleID",mRoleID);
        //发送消息，关闭其它客户端
        MiPushClient.setUserAccount(this, userPhone_et.getText().toString().trim(), userPhone_et.getText().toString().trim());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String error) {
        ToastUtils.showShort(error);
    }
}
