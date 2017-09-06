package com.idisfkj.hightcopywx.ui.presenter;

import com.idisfkj.hightcopywx.ui.model.LoginModel;
import com.idisfkj.hightcopywx.ui.model.LoginModelImp;
import com.idisfkj.hightcopywx.ui.view.LoginView;
import com.idisfkj.hightcopywx.util.ToastUtils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by fvelement on 2017/9/6.
 */

public class LoginPresenterImp implements LoginPresenter,LoginModel.requestLoginListener {
    private LoginView mloginView;
    private LoginModel mloginModel;
    public LoginPresenterImp(LoginView loginView) {
        this.mloginView=loginView;
        mloginModel=new LoginModelImp();
    }

    @Override
    public void login(String mobile, String password, String clientid) {
        if(StringUtils.isBlank(mobile) || StringUtils.isBlank(password)){
            ToastUtils.showShort("用户名和密码不能为空");
            return;
        }
        mloginModel.requestLogin(this,mobile,password,clientid);
    }

    @Override
    public void onLoginSucceed() {
        mloginView.onLoginSuccess();
    }

    @Override
    public void onError(String msg) {
        mloginView.onLoginError(msg);
    }
}
