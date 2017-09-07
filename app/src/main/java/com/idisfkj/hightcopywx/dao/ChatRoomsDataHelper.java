package com.idisfkj.hightcopywx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.idisfkj.hightcopywx.beans.ChatRoomItemInfo;
import com.idisfkj.hightcopywx.util.database.Column;
import com.idisfkj.hightcopywx.util.database.SQLiteTable;

/**
 * Created by idisfkj on 16/4/29.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsDataHelper extends BaseDataHelper {

    public ChatRoomsDataHelper(Context mContext) {
        super(mContext);
    }

    @Override
    public Uri getContentUri() {
        return DataProvider.WXS_CONTENT_URI;
    }

    public static final class WXItemDataInfo implements BaseColumns {
        public WXItemDataInfo() {
        }

        public static final String TABLE_NAME = "chatRooms";
        public static final String ownMobile = "ownMobile";
        public static final String chatRoomID = "chatRoomID";
        public static final String imgUrl = "imgUrl";
        public static final String title = "title";
        public static final String content = "content";
        public static final String time = "time";
        public static final String unReadNumber = "unReadNumber";
        public static final String chatType="chatType";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ownMobile, Column.DataType.TEXT)
                .addColumn(chatRoomID, Column.DataType.TEXT)
                .addColumn(imgUrl, Column.DataType.TEXT)
                .addColumn(title, Column.DataType.TEXT)
                .addColumn(content, Column.DataType.TEXT)
                .addColumn(time, Column.DataType.TEXT)
                .addColumn(unReadNumber, Column.DataType.INTEGER)
                .addColumn(chatType, Column.DataType.INTEGER);
    }

    public ContentValues getContentValues(ChatRoomItemInfo itemInfo) {
        ContentValues values = new ContentValues();
        values.put(WXItemDataInfo.ownMobile, itemInfo.getOwnMobile());
        values.put(WXItemDataInfo.chatRoomID, itemInfo.getChatRoomID());
        values.put(WXItemDataInfo.imgUrl, itemInfo.getImgUrl());
        values.put(WXItemDataInfo.title, itemInfo.getTitle());
        values.put(WXItemDataInfo.content, itemInfo.getContent());
        values.put(WXItemDataInfo.time, itemInfo.getTime());
        values.put(WXItemDataInfo.unReadNumber, itemInfo.getUnReadNumber());
        values.put(WXItemDataInfo.chatType, itemInfo.getChatType());
        return values;
    }

    public Cursor query(String chatRoomid) {
        Cursor cursor = query(null, WXItemDataInfo.chatRoomID + "=?"
                , new String[]{chatRoomid}, WXItemDataInfo._ID + " ASC");
        return cursor;
    }


    public void insert(ChatRoomItemInfo itemInfo) {
        ContentValues values = getContentValues(itemInfo);
        insert(values);
    }

    public int update(String content, String time, String chatRoomid) {
        ContentValues values = new ContentValues();
        values.put(WXItemDataInfo.content, content);
        values.put(WXItemDataInfo.time, time);
        int row = update(values, WXItemDataInfo.chatRoomID + "=?"
                , new String[]{chatRoomid});
        return row;
    }

    public int update(int unReadNum, String chatRoomid) {
        ContentValues values = new ContentValues();
        values.put(WXItemDataInfo.unReadNumber, unReadNum);
        int row = update(values, WXItemDataInfo.chatRoomID + "=?"
                , new String[]{chatRoomid});
        return row;
    }

    public int delet(String chatRoomid) {
        synchronized (DataProvider.DBLock) {
            int row = delet(WXItemDataInfo.chatRoomID + "=?"
                    , new String[]{chatRoomid});
            return row;
        }
    }

    public android.support.v4.content.CursorLoader getCursorLoader(String ownMobile) {
        return getV4CursorLoader(null, WXItemDataInfo.ownMobile + "=?"
                , new String[]{ownMobile}, WXItemDataInfo._ID + " ASC");
    }
}
