package com.idisfkj.hightcopywx.chat.model.imp;

import android.database.Cursor;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatRoomItemInfo;
import com.idisfkj.hightcopywx.chat.model.ChatRoomsModel;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.chat.view.ChatRoomsView;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsModelImp implements ChatRoomsModel {
    @Override
    public void initData(ChatRoomsDataHelper mHelper, ChatRoomsView chatRoomsView) {
        chatRoomsView.onInitDataBegin();

        String ownMoible=SharedPreferencesManager.getString("userPhone");
        String chatRoomIDPropose=ownMoible+"001";
        Cursor cursor=mHelper.query(chatRoomIDPropose);
        int count=cursor.getCount();

        ChatRoomItemInfo chatRoomItemInfo = new ChatRoomItemInfo();
        if (count==0) {
            String chatRoomIDStudyEnglish=ownMoible+"004";
            chatRoomItemInfo.setTitle("英语学习");
            chatRoomItemInfo.setContent("让我们来开心的学习英语吧。");
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID(chatRoomIDStudyEnglish);
            chatRoomItemInfo.setOwnMobile(ownMoible);
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_ENGLISH_STUDY);
            mHelper.insert(chatRoomItemInfo);
            MiPushClient.subscribe(App.getAppContext(),chatRoomIDStudyEnglish,ownMoible);

            String chatRoomIDChineseToEnglish=ownMoible+"002";
            chatRoomItemInfo.setTitle("汉英翻译");
            chatRoomItemInfo.setContent("您说的中文，将被翻译成英文。");
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID(chatRoomIDChineseToEnglish);
            chatRoomItemInfo.setOwnMobile(ownMoible);
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_CHINESETOENGLISH);
            mHelper.insert(chatRoomItemInfo);
            MiPushClient.subscribe(App.getAppContext(),chatRoomIDChineseToEnglish,ownMoible);

            String chatRoomIDEnglishToChinese=ownMoible+"003";
            chatRoomItemInfo.setTitle("英汉翻译");
            chatRoomItemInfo.setContent("您说英文，将被翻译成中文。");
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID(chatRoomIDEnglishToChinese);
            chatRoomItemInfo.setOwnMobile(ownMoible);
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_ENGLISHTOCHINESE);
            mHelper.insert(chatRoomItemInfo);
            MiPushClient.subscribe(App.getAppContext(),chatRoomIDEnglishToChinese,ownMoible);

            chatRoomItemInfo.setTitle(App.COMPLAIN_PROPOSE);
            chatRoomItemInfo.setContent(App.COMPLAIN_PROPOSE_MESSAGE);
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID(chatRoomIDPropose);
            chatRoomItemInfo.setOwnMobile(ownMoible);
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_CHAT);
            mHelper.insert(chatRoomItemInfo);
            MiPushClient.subscribe(App.getAppContext(),chatRoomIDPropose,ownMoible);
        }
        chatRoomsView.onInitDataEnd(cursor);
    }

}
