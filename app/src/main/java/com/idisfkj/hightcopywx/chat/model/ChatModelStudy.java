package com.idisfkj.hightcopywx.chat.model;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by fvelement on 2017/9/14.
 */

public interface ChatModelStudy {
    ChatMessageInfo getStudyMessage(String chatRoomID);

    void initData(initListener listener);

    interface initListener{

        void onInitSucceed();

        void onInitError(String errorMessage);
    }

}
