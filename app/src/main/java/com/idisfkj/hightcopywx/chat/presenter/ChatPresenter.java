package com.idisfkj.hightcopywx.chat.presenter;

import android.content.CursorLoader;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public interface ChatPresenter  {

    CursorLoader creatLoader(String ownMobile,int page);

    void sendData(ChatMessageInfo chatMessageInfo);

    void cleanUnReadNum(String chatRoomId);

    void startStudy(String chatRoomId);

    void updateLasterContent(String chatRoomId);

}
