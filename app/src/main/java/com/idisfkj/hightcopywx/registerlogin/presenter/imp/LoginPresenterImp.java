package com.idisfkj.hightcopywx.registerlogin.presenter.imp;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.registerlogin.model.LoginModel;
import com.idisfkj.hightcopywx.registerlogin.model.imp.LoginModelImp;
import com.idisfkj.hightcopywx.registerlogin.presenter.LoginPresenter;
import com.idisfkj.hightcopywx.registerlogin.view.LoginView;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by fvelement on 2017/9/6.
 */

public class LoginPresenterImp implements LoginPresenter,LoginModel.requestLoginListener ,LoginModel.initListener{
    private LoginView mloginView;
    private LoginModel mloginModel;
    public LoginPresenterImp(LoginView loginView) {
        this.mloginView=loginView;
        mloginModel=new LoginModelImp();
    }

    @Override
    public void login(String mobile, String password, String roleID) {
        if(StringUtils.isBlank(mobile) || StringUtils.isBlank(password)){
            ToastUtils.showShort("用户名和密码不能为空");
            return;
        }
        String clientid=MiPushClient.getRegId(App.getAppContext());
        mloginModel.requestLogin(this,mobile,password,clientid);
    }

    @Override
    public void onLoginSucceed() {
        mloginModel.initData(this);
        mloginView.onLoginSuccess();
    }

    @Override
    public void onError(String msg) {
        mloginView.onLoginError(msg);
    }

    @Override
    public void onInitSuccess() {

    }

    @Override
    public void onInitFail() {

    }
}
