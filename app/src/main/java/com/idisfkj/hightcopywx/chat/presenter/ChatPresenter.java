package com.idisfkj.hightcopywx.chat.presenter;

import android.content.CursorLoader;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public interface ChatPresenter {
    CursorLoader creatLoader(String ownMobile);
    void initData();

    void sendData(ChatMessageInfo chatMessageInfo);

    void receiveData(ChatMessageInfo chatMessageInfo);

    void cleanUnReadNum(String chatRoomId);

    void updateLasterContent(String chatRoomId);

}
