package com.idisfkj.hightcopywx.chat.presenter;

import android.database.Cursor;
import android.support.v4.content.Loader;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public interface ChatRoomsPresent {
    Loader<Cursor> creatLoader(String ownMobile,int page);
    void initData();
}
