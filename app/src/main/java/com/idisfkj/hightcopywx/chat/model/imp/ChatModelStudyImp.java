package com.idisfkj.hightcopywx.chat.model.imp;

import android.database.Cursor;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudy;
import com.idisfkj.hightcopywx.beans.RespondStudy;
import com.idisfkj.hightcopywx.dao.WordDataHelper;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.CursorUtils;
import com.idisfkj.hightcopywx.util.GsonRequest;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by fvelement on 2017/9/13.
 */

public class ChatModelStudyImp extends ChatModelBase implements ChatModelStudy {
    private WordDataHelper mWordDataHelper;
    public Cursor mCursor;

    public ChatModelStudyImp() {
        mWordDataHelper = new WordDataHelper(App.getAppContext());
        mCursor = mWordDataHelper.query(App.ownMobile);

    }

    @Override
    public void initData(final initListener listener) {
        //ToastUtils.showShort("正在初始化");
        GsonRequest<RespondStudy> gsonRequest = new GsonRequest<RespondStudy>(Request.Method.POST,
                UrlUtils.getNowDayWordListApiUrl(), RespondStudy.class,
                new Response.Listener<RespondStudy>() {
                    @Override
                    public void onResponse(RespondStudy respondStudy) {
                        if (respondStudy.getCode() == 0) {
                            List<WordsEntity> listWords = respondStudy.getListWords();
                            if(listWords!=null && !listWords.isEmpty()){
                                insert(listWords);
                            }
                            //ToastUtils.showShort("开始学习");
                            listener.onInitSucceed();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("token", App.token);
                return header;
            }
        };
        VolleyUtils.addQueue(gsonRequest, "chatRequest");
    }

    private void insert(List<WordsEntity> wordsEntityList) {
        Iterator iterator = wordsEntityList.iterator();
        while (iterator.hasNext()) {
            mWordDataHelper.insert((WordsEntity) iterator.next());
        }
        mCursor = mWordDataHelper.query(App.ownMobile);
        //存储
        int page = SharedPreferencesManager.getInt("page", 1);
        SharedPreferencesManager.putInt("page", ++page).commit();
        String nowDate = CalendarUtils.getCurrentDay();
        SharedPreferencesManager.putString("getStudyDataDate", nowDate).commit();

    }


    @Override
    public ChatMessageInfo getStudyMessage(String chatRoomID) {
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        if (mCursor.moveToNext()) {
            chatMessageInfo.setStatus(App.MESSAGE_STATUS_SENDING);
            chatMessageInfo.setChatRoomID(chatRoomID);
            chatMessageInfo.setMessageType(App.MESSAGE_TYPE_CARD);
            chatMessageInfo.setMessageTitle(CursorUtils.formatString(mCursor, WordDataHelper.WordDataInfo.english));
            chatMessageInfo.setMessageContent(CursorUtils.formatString(mCursor, WordDataHelper.WordDataInfo.chinese));
            chatMessageInfo.setMessageImgUrl(CursorUtils.formatString(mCursor, WordDataHelper.WordDataInfo.imgurl));
            chatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
            chatMessageInfo.setSendMobile(chatRoomID);
            //chatMessageInfo.setSendName("贝贝");
            return chatMessageInfo;
        }
        return chatMessageInfo;

    }

    @Override
    public boolean isLast() {
        return mCursor.isLast();
    }

}
