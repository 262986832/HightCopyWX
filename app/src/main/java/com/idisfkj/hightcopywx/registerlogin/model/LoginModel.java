package com.idisfkj.hightcopywx.registerlogin.model;

/**
 * Created by fvelement on 2017/9/6.
 */

public interface LoginModel {
    void initData(initListener initListener);
    interface initListener{
        void onInitSuccess();
        void onInitFail();
    }
    void requestLogin(requestLoginListener listener, String mobile, String password, String clientid);

    interface requestLoginListener {
        void onLoginSucceed();

        void onError(String msg);
    }
}
