package com.idisfkj.hightcopywx.chat.view;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by fvelement on 2017/10/13.
 */

public interface ChatTranslateView {
    void onTranslateComplete(ChatMessageInfo requestChatMessageInfo,ChatMessageInfo respondChatMessageInfo);
}
