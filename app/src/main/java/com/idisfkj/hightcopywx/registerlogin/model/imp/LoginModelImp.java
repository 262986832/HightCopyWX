package com.idisfkj.hightcopywx.registerlogin.model.imp;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.RespondLogin;
import com.idisfkj.hightcopywx.registerlogin.model.LoginModel;
import com.idisfkj.hightcopywx.util.GsonRequest;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

/**
 * Created by fvelement on 2017/9/6.
 */

public class LoginModelImp implements LoginModel {
    @Override
    public void initData(final initListener initListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("page", "1")
                .add("limit", "1")
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(UrlUtils.getEncourageList())
                .addHeader("token", App.token)
                .post(requestBody)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO 失败的,在子线程中

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String json = response.body().string();
                Gson gson=new Gson();

                LinkedTreeMap<?,?> yourMap = new LinkedTreeMap<Object, Object>();
                JsonObject jsonObject = gson.toJsonTree(yourMap).getAsJsonObject();
                gson.fromJson(json, JsonObject.class);
                System.out.print(jsonObject.get("page"));
            }

        });
    }


    @Override
    public void requestLogin(final requestLoginListener listener, final String mobile, final String password, String clientid) {
        GsonRequest<RespondLogin> gsonRequest = new GsonRequest<RespondLogin>
                (Request.Method.POST,
                        UrlUtils.getLoginApiUrl(mobile, password, clientid),
                        RespondLogin.class,
                        new Response.Listener<RespondLogin>() {
                            @Override
                            public void onResponse(RespondLogin respondLogin) {
                                if (respondLogin.getCode() == 0) {
                                    SharedPreferencesManager.putString("userPhone", mobile).commit();
                                    SharedPreferencesManager.putString("password", password).commit();
                                    //SharedPreferencesManager.putString("token",respondLogin.getToken()).commit();
                                    App.token = respondLogin.getToken();
                                    App.headUploadToken = respondLogin.getHeadUploadToken();
                                    App.voiceUploadToken = respondLogin.getVoiceUploadToken();
                                    //SharedPreferencesManager.putString("userName",respondLogin.getUserName()).commit();
                                    App.userName = respondLogin.getUserName();
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
                                listener.onError(error.getMessage());
                            }
                        }) {
        };
        VolleyUtils.addQueue(gsonRequest, "loginRequest");
    }
}
