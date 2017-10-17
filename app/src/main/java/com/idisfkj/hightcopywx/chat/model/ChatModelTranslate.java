package com.idisfkj.hightcopywx.chat.model;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by fvelement on 2017/10/16.
 */

public interface ChatModelTranslate {
    void translate(TranslateListner translateListner, int type, ChatMessageInfo chatMessageInfo);
    interface TranslateListner{
        void onTranslateComplete(ChatMessageInfo requestChatMessageInfo,ChatMessageInfo respondChatMessageInfo);
    }
}
