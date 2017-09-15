package com.idisfkj.hightcopywx.chat.model;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public interface ChatModel {

    void requestData(requestListener listener, ChatMessageInfo chatMessageInfo);

    interface requestListener {

        void onRequestSucceed(ChatMessageInfo requestChatMessageInfo,ChatMessageInfo respondChatMessageInfo);

        void onRequestError(String errorMessage);
    }

}
