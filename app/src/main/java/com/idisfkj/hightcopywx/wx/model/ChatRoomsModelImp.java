package com.idisfkj.hightcopywx.wx.model;

import android.database.Cursor;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatRoomItemInfo;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.wx.presenter.ChatRoomsPresent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsModelImp implements ChatRoomsModel {
    private List<ChatRoomItemInfo> mList;
    private ChatRoomItemInfo chatRoomItemInfo;

    @Override
    public void initData(ChatRoomsDataHelper mHelper,ChatRoomsPresent.InitRoomsDataLinsener initRoomsDataLinsener) {
        mList = new ArrayList<>();
        Cursor cursor=mHelper.query("EnglishToChinese");
        int count=cursor.getCount();
        //String chartRoomid=CursorUtils.formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.chatRoomID);
        ChatRoomItemInfo chatRoomItemInfo = new ChatRoomItemInfo();
        if (count==0) {
            chatRoomItemInfo.setTitle(App.DEVELOPER_NAME);
            chatRoomItemInfo.setContent(App.DEVELOPER_MESSAGE);
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID(App.DEVELOPER_ID);
            chatRoomItemInfo.setOwnMobile(SharedPreferencesManager.getString("userPhone"));
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_ENGLISH_STUDY);

            mHelper.insert(chatRoomItemInfo);
            chatRoomItemInfo.setTitle("汉英翻译");
            chatRoomItemInfo.setContent("您说的中文，将被翻译成英文。");
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID("ChineseToEnglish");
            chatRoomItemInfo.setOwnMobile(SharedPreferencesManager.getString("userPhone"));
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_CHINESETOENGLISH);
            mHelper.insert(chatRoomItemInfo);

            chatRoomItemInfo.setTitle("英汉翻译");
            chatRoomItemInfo.setContent("您说英文，将被翻译成中文。");
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID("EnglishToChinese");
            chatRoomItemInfo.setOwnMobile(SharedPreferencesManager.getString("userPhone"));
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_ENGLISHTOCHINESE);
            mHelper.insert(chatRoomItemInfo);
        }
        initRoomsDataLinsener.onInitDataComplete(cursor);
    }

}
