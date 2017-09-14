package com.idisfkj.hightcopywx.chat.model;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.dao.WordDataHelper;
import com.idisfkj.hightcopywx.util.GsonRequest;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by fvelement on 2017/9/13.
 */

public class ChatModelStudyImp extends ChatModelImp {
    private WordDataHelper mWordDataHelper;
    public void initStudyData(){
        ToastUtils.showShort("正在初始化学习单词。。。");
        GsonRequest<RespondStudy> gsonRequest = new GsonRequest<RespondStudy>(Request.Method.POST,
                UrlUtils.getNowDayWordListApiUrl(), RespondStudy.class,
                new Response.Listener<RespondStudy>() {
                    @Override
                    public void onResponse(RespondStudy respondStudy) {
                        if (respondStudy.getCode()==0) {
                            List<WordsEntity> listWords=respondStudy.getListWords();
                            insert(listWords.get(0));
                            ToastUtils.showShort("初始化完成，开始学习");
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
                header.put("token",  App.token);
                return header;
            }
        };
        VolleyUtils.addQueue(gsonRequest, "chatRequest");
    }
    private void insert(WordsEntity wordsEntity){
        mWordDataHelper.insert(wordsEntity);
    }
    public ChatModelStudyImp() {
        mWordDataHelper=new WordDataHelper(App.getAppContext());

    }

    @Override
    public void requestData(final requestListener listener, final ChatMessageInfo chatMessageInfo) {


    }
}
