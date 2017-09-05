package com.idisfkj.hightcopywx.ui.model;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public interface RegisterModel {
    void requestRegister(requestRegisterListener listener, String userName, String mobile, String passowrd);
    void requestLogin(requestLoginListener listener, String mobile, String passowrd,String clientid);
    public interface requestRegisterListener {
        void onRegisterSucceed();

        void onError(String msg);
    }
    public interface requestLoginListener {
        void onLoginSucceed();

        void onError(String msg);
    }
}
