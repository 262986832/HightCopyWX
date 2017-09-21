package com.idisfkj.hightcopywx.registerlogin.model;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public interface RegisterModel {
    void requestRegister(requestRegisterListener listener, String userName, String mobile, String passowrd);

    public interface requestRegisterListener {
        void onRegisterSucceed();

        void onError(String msg);
    }

}
