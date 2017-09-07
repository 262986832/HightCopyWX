package com.idisfkj.hightcopywx.wx.presenter;

import android.database.Cursor;

import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public interface ChatRoomsPresent {
    void initData(ChatRoomsDataHelper helper,InitRoomsDataLinsener initRoomsDataLinsener);
    public interface InitRoomsDataLinsener{
        void onInitDataComplete( Cursor data);
        void onInitDataing();
    }
}