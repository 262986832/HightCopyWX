package com.idisfkj.hightcopywx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.util.database.Column;
import com.idisfkj.hightcopywx.util.database.SQLiteTable;

import java.util.ArrayList;

/**
 * Created by idisfkj on 16/4/30.
 * Email : idisfkj@qq.com.
 */
public class ChatMessageDataHelper extends BaseDataHelper {
    public ChatMessageDataHelper(Context mContext) {
        super(mContext);
    }

    @Override
    public Uri getContentUri() {
        return DataProvider.CHAT_MESSAGES_CONTENT_URI;
    }

    public ContentValues getContentValues(ChatMessageInfo info) {
        ContentValues values = new ContentValues();
        values.put(ChatMessageDataInfo.messageID, info.getMessageID());
        values.put(ChatMessageDataInfo.ownMobile, info.getOwnMobile());
        values.put(ChatMessageDataInfo.chatRoomID, info.getChatRoomID());
        values.put(ChatMessageDataInfo.sendOrReciveFlag, info.getSendOrReciveFlag());
        values.put(ChatMessageDataInfo.messageType, info.getMessageType());
        values.put(ChatMessageDataInfo.status, info.getStatus());
        values.put(ChatMessageDataInfo.messageTitle, info.getMessageTitle());
        values.put(ChatMessageDataInfo.messageContent, info.getMessageContent());
        values.put(ChatMessageDataInfo.messageImgUrl, info.getMessageImgUrl());
        values.put(ChatMessageDataInfo.messageVoiceUrl, info.getMessageVoiceUrl());
        values.put(ChatMessageDataInfo.time, info.getTime());
        values.put(ChatMessageDataInfo.sendMobile, info.getSendMobile());
        return values;
    }

    public static final class ChatMessageDataInfo implements BaseColumns {
        public static final String TABLE_NAME = "chatMessages";
        public static final String messageID = "messageID";
        public static final String ownMobile = "ownMobile";
        public static final String chatRoomID = "chatRoomID";
        public static final String sendOrReciveFlag = "sendOrReciveFlag";
        public static final String messageType = "messageType";
        public static final String status = "status";
        public static final String messageTitle = "messageTitle";
        public static final String messageContent = "messageContent";
        public static final String messageImgUrl = "messageImgUrl";
        public static final String messageVoiceUrl = "messageVoiceUrl";
        public static final String time = "time";
        public static final String sendMobile = "sendMobile";
        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(messageID, Column.DataType.TEXT)
                .addColumn(ownMobile, Column.DataType.TEXT)
                .addColumn(chatRoomID, Column.DataType.TEXT)
                .addColumn(sendOrReciveFlag, Column.DataType.INTEGER)
                .addColumn(messageType, Column.DataType.INTEGER)
                .addColumn(status, Column.DataType.INTEGER)
                .addColumn(messageTitle, Column.DataType.TEXT)
                .addColumn(messageContent, Column.DataType.TEXT)
                .addColumn(messageImgUrl, Column.DataType.TEXT)
                .addColumn(messageVoiceUrl, Column.DataType.TEXT)
                .addColumn(time, Column.DataType.TEXT)
                .addColumn(sendMobile, Column.DataType.TEXT);
    }

    public Cursor query(String chatRoomId) {
        Cursor cursor = query(null
                , ChatMessageDataInfo.chatRoomID + "=?"
                , new String[]{chatRoomId}
                , ChatMessageDataInfo._ID + " DESC");
        return cursor;
    }

    public void insert(ChatMessageInfo info) {

        ContentValues values = getContentValues(info);
        insert(values);
    }

    public int updateStatus(int status, String messageID) {
        ContentValues values = new ContentValues();
        values.put(ChatMessageDataInfo.status, status);
        int row = update(values, ChatMessageDataInfo.messageID + "=?"
                , new String[]{messageID});
        return row;
    }

    public void bulkInser(ArrayList<ChatMessageInfo> list) {
        ArrayList<ContentValues> valuesList = new ArrayList<>();
        for (ChatMessageInfo info : list) {
            ContentValues itemValues = getContentValues(info);
            valuesList.add(itemValues);
        }
        ContentValues[] valuesArray = new ContentValues[valuesList.size()];
        bulkInsert(valuesList.toArray(valuesArray));
    }

    public CursorLoader getCursorLoader(String chatRoomId,int page) {
        int allCount=this.query(chatRoomId).getCount();
        int showCount=page*15;
        int first=allCount-showCount;
        return getCursorLoader(null, ChatMessageDataInfo.chatRoomID + "=?", new String[]{chatRoomId}
                , ChatMessageDataInfo._ID + " asc limit  "+first+","+showCount );
    }
}
