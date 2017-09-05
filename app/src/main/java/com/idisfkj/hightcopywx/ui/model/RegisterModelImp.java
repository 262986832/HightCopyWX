package com.idisfkj.hightcopywx.ui.model;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.RespondLogin;
import com.idisfkj.hightcopywx.beans.ResponsdServer;
import com.idisfkj.hightcopywx.util.GsonRequest;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class RegisterModelImp implements RegisterModel {
    @Override
    public void requestRegister(final requestRegisterListener listener, final String userName, final String mobile, final String passowrd) {

        GsonRequest<ResponsdServer> gsonRequest = new GsonRequest<ResponsdServer>
                (Request.Method.POST,
                        UrlUtils.getRegisterApiUrl(userName, mobile, passowrd),
                        ResponsdServer.class,
                        new Response.Listener<ResponsdServer>() {
                            @Override
                            public void onResponse(ResponsdServer responsdServer) {
                                if (responsdServer.getCode() == 0) {
                                    SharedPreferencesManager.putString("userName",userName).commit();
                                    SharedPreferencesManager.putString("userPhone",mobile).commit();
                                    SharedPreferencesManager.putString("password",passowrd).commit();
                                    listener.onRegisterSucceed();
                                } else {
                                    listener.onError(responsdServer.getMsg());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, error.getMessage(), error);
                            }
                        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + App.APP_SECRET_KEY);
                return header;
            }
        };
        VolleyUtils.addQueue(gsonRequest, "registerRequest");
    }

    @Override
    public void requestLogin(final requestLoginListener listener, final String mobile, String passowrd, String clientid) {
        GsonRequest<RespondLogin> gsonRequest = new GsonRequest<RespondLogin>
                (Request.Method.POST,
                        UrlUtils.getLoginApiUrl( mobile, passowrd,clientid),
                        RespondLogin.class,
                        new Response.Listener<RespondLogin>() {
                            @Override
                            public void onResponse(RespondLogin respondLogin) {
                                if (respondLogin.getCode() == 0) {
                                    SharedPreferencesManager.putString("token",respondLogin.getToken()).commit();
                                    listener.onLoginSucceed();
                                } else {
                                    listener.onError(respondLogin.getMsg());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, error.getMessage(), error);
                            }
                        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + App.APP_SECRET_KEY);
                return header;
            }
        };
        VolleyUtils.addQueue(gsonRequest, "loginRequest");
    }

}
