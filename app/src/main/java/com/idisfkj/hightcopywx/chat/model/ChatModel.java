package com.idisfkj.hightcopywx.chat.model;

import android.content.CursorLoader;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public interface ChatModel {

    CursorLoader initData(String chatRoomID, int page);

    void requestData(requestListener listener, ChatMessageInfo chatMessageInfo);

    void insertData(ChatMessageInfo info);

    interface requestListener {

        void onSucceed(ChatMessageInfo chatMessageInfo);

        void onError(String errorMessage);
    }

}
