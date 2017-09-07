package com.idisfkj.hightcopywx.wx.view;

import android.database.Cursor;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public interface ChatRoomsView {
    void onInitDataComplete( Cursor data);
    void onInitDataing();
}
