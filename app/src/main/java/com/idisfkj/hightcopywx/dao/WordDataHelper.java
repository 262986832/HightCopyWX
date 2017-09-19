package com.idisfkj.hightcopywx.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.database.Column;
import com.idisfkj.hightcopywx.util.database.SQLiteTable;

import java.text.ParseException;

import static com.idisfkj.hightcopywx.dao.WordDataHelper.WordDataInfo.bookid;
import static com.idisfkj.hightcopywx.dao.WordDataHelper.WordDataInfo.firstcorrect;
import static com.idisfkj.hightcopywx.dao.WordDataHelper.WordDataInfo.firstfinishtime;
import static com.idisfkj.hightcopywx.dao.WordDataHelper.WordDataInfo.firstwrong;
import static com.idisfkj.hightcopywx.dao.WordDataHelper.WordDataInfo.ownmobile;
import static com.idisfkj.hightcopywx.dao.WordDataHelper.WordDataInfo.wordid;

/**
 * Created by fvelement on 2017/9/13.
 */

public class WordDataHelper extends BaseDataHelper {
    public WordDataHelper(Context mContext) {
        super(mContext);
    }
    @Override
    public Uri getContentUri() {
        return DataProvider.WORDS_CONTENT_URI;
    }

    private ContentValues getContentValues(WordsEntity info) {
        String userPhone= SharedPreferencesManager.getString("userPhone");
        ContentValues values = new ContentValues();
        values.put(ownmobile, userPhone);
        values.put(wordid, info.getWordid());
        values.put(bookid, info.getBookid());
        values.put(WordDataInfo.english, info.getEnglish());
        values.put(WordDataInfo.chinese, info.getChinese());
        values.put(WordDataInfo.imgurl, info.getImgurl());
        values.put(firstwrong, 0);
        values.put(firstcorrect, 0);
        try {
            values.put(firstfinishtime, CalendarUtils.getLongCurrentDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return values;
    }

    public static final class WordDataInfo implements BaseColumns {

        public WordDataInfo() {
        }

        public static final String TABLE_NAME = "words";
        public static final String ownmobile = "ownmobile";
        public static final String wordid = "wordid";
        public static final String bookid = "bookid";
        public static final String english = "english";
        public static final String chinese = "chinese";
        public static final String imgurl = "imgurl";
        public static final String firstwrong = "firstwrong";
        public static final String firstcorrect = "firstcorrect";
        public static final String firstfinishtime = "firstfinishtime";
        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ownmobile, Column.DataType.TEXT)
                .addColumn(wordid, Column.DataType.TEXT)
                .addColumn(bookid, Column.DataType.TEXT)
                .addColumn(english, Column.DataType.TEXT)
                .addColumn(chinese, Column.DataType.TEXT)
                .addColumn(imgurl, Column.DataType.TEXT)
                .addColumn(firstwrong, Column.DataType.INTEGER)
                .addColumn(firstcorrect, Column.DataType.INTEGER)
                .addColumn(firstfinishtime, Column.DataType.INTEGER);
    }



    public void insert(WordsEntity info) {
        ContentValues values = getContentValues(info);
        insert(values);
    }

    public Cursor query(String ownMobile) {
        StringBuilder stringBuilder=new StringBuilder(WordDataInfo.ownmobile);
        stringBuilder.append("=? and firstcorrect<3");
        Cursor cursor = query(null,  stringBuilder.toString()
                , new String[]{ownMobile}, WordDataInfo._ID + " ASC");
        return cursor;
    }



    public CursorLoader getCursorLoader(String ownmobile) {
        return getCursorLoader(null, ownmobile + "=?", new String[]{ownmobile}, WordDataInfo._ID + " ASC");
    }
}
