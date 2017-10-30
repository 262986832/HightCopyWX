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
            String chatRoomIDPratise=ownMoible+"005";
            chatRoomItemInfo.setTitle("英语练习");
            chatRoomItemInfo.setContent("英语练习");
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID(chatRoomIDPratise);
            chatRoomItemInfo.setOwnMobile(ownMoible);
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_PRACTISE);
            mHelper.insert(chatRoomItemInfo);
            MiPushClient.subscribe(App.getAppContext(),chatRoomIDPratise,ownMoible);

            String chatRoomIDStudyEnglish=ownMoible+"004";
            chatRoomItemInfo.setTitle("英语考试");
            chatRoomItemInfo.setContent("英语考试。");
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID(chatRoomIDStudyEnglish);
            chatRoomItemInfo.setOwnMobile(ownMoible);
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_ENGLISH_STUDY);
            mHelper.insert(chatRoomItemInfo);
            MiPushClient.subscribe(App.getAppContext(),chatRoomIDStudyEnglish,ownMoible);

            String chatRoomIDTranslate=ownMoible+"004";
            chatRoomItemInfo.setTitle("汉英互译");
            chatRoomItemInfo.setContent("汉英实时互译");
            chatRoomItemInfo.setTime(CalendarUtils.getCurrentDate());
            chatRoomItemInfo.setChatRoomID(chatRoomIDTranslate);
            chatRoomItemInfo.setOwnMobile(ownMoible);
            chatRoomItemInfo.setChatType(App.CHAT_TYPE_TRANSLATE_ENZH);
            mHelper.insert(chatRoomItemInfo);
            MiPushClient.subscribe(App.getAppContext(),chatRoomIDTranslate,ownMoible);



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
