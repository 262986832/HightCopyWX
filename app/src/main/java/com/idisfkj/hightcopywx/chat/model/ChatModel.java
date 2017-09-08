package com.idisfkj.hightcopywx.chat.model;

import android.content.Context;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.chat.view.ChatView;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public interface ChatModel {
    void initData(ChatMessageDataHelper helper, ChatView chatView);


    void requestData(requestListener listener, ChatMessageInfo chatMessageInfo, ChatMessageDataHelper helper);

    void insertData(ChatMessageInfo info, ChatMessageDataHelper helper);



    void updateUnReadNum(Context context, String regId, String number, int unReadNum);

    void updateLasterContent(Context context, String regId, String number);

    public interface requestListener {
        void onSucceed(ChatMessageInfo chatMessageInfo, ChatMessageDataHelper helper);

        void onError(String errorMessage);
    }

    public interface cursorListener {
        void onSucceed(String regId, String number, String userName, int unReadNum);
    }
}
