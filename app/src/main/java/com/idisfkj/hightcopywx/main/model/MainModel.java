package com.idisfkj.hightcopywx.main.model;

/**
 * Created by idisfkj on 16/4/29.
 * Email : idisfkj@qq.com.
 */
public interface  MainModel {
    void uploadHeadUrl(requestUploadHeadUrlListener listener, String headUrl);

    interface requestUploadHeadUrlListener {
        void onUploadHeadUrlSucceed();

        void onUploadHeadUrlError(String msg);
    }
}
