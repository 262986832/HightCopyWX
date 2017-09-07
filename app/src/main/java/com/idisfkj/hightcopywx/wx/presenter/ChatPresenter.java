package com.idisfkj.hightcopywx.wx.presenter;

import android.content.CursorLoader;
import android.content.Intent;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public interface ChatPresenter {
    CursorLoader creatLoader(String ownMobile);
    void initData();

    void sendData(ChatMessageInfo chatMessageInfo);

    void receiveData(Intent intent);

    void cleanUnReadNum(String ownMobile,String chatRoomId);

    void updateLasterContent(String ownMobile,String chatRoomId);

}
