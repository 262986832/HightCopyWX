package com.idisfkj.hightcopywx.util;

import android.database.Cursor;

import java.text.ParseException;

/**
 * cursor数据获取工具类
 * Created by idisfkj on 16/4/30.
 * Email : idisfkj@qq.com.
 */
public class CursorUtils {
    public CursorUtils() {
    }

    public static String formatString(Cursor cursor, String columnName) {
        String result = cursor.getString(cursor.getColumnIndex(columnName));
        return result;
    }

    public static int formatInt(Cursor cursor, String columnName) {
        int result = cursor.getInt(cursor.getColumnIndex(columnName));
        return result;
    }

    public static boolean isShowSystem(Cursor cursor, String columnName) throws ParseException {
        if (cursor.isFirst())
            return false;
        String result = cursor.getString(cursor.getColumnIndex(columnName));
        cursor.moveToPrevious();
        String resultP = cursor.getString(cursor.getColumnIndex(columnName));
        long a=CalendarUtils.getLongDate(result) - CalendarUtils.getLongDate(resultP);
        cursor.moveToNext();
        if ( a< 60000)
            return true;
        else
            return false;
    }
}
