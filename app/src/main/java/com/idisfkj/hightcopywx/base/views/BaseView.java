package com.idisfkj.hightcopywx.base.views;

import android.database.Cursor;

/**
 * Created by fvelement on 2017/9/7.
 */

public interface BaseView {
    void onInitDataEnd( Cursor data);
    void onInitDataBegin();
}
