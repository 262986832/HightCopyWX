package com.idisfkj.hightcopywx.main.model.imp;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.main.model.MainModel;
import com.idisfkj.hightcopywx.util.UrlUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by idisfkj on 16/4/29.
 * Email : idisfkj@qq.com.
 */
public class MainModelImp implements MainModel {

    @Override
    public void uploadHeadUrl(final requestUploadHeadUrlListener listener, String headUrl) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("headUrl", headUrl)
                .build();
        Request request = new Request.Builder()
                .url(UrlUtils.updateHeadUrl(headUrl))
                .addHeader("token", App.token)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO 失败的,在子线程中
                listener.onUploadHeadUrlError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //TODO 成功时,在子线程中。
                listener.onUploadHeadUrlSucceed();
            }
        });
    }
}
