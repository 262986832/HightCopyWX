package com.idisfkj.hightcopywx.registerlogin.model;

/**
 * Created by fvelement on 2017/9/6.
 */

public interface LoginModel {
    void requestLogin(requestLoginListener listener, String mobile, String password,String clientid);
    public interface requestLoginListener {
        void onLoginSucceed();

        void onError(String msg);
    }
}
