package com.idisfkj.hightcopywx.chat.model.imp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.RespondStudy;
import com.idisfkj.hightcopywx.beans.WordsStudentEntity;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudy;
import com.idisfkj.hightcopywx.util.GsonRequest;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by fvelement on 2017/9/13.
 */

public class ChatModelStudyImp extends ChatModelBase implements ChatModelStudy {
    private List<WordsStudentEntity> mWordsStudentEntityList = null;
    private boolean mIslast = false;
    private int allStudyCount = 1;
    private int mListIndex = 0;
    String jsonMessage;
    public ChatModelStudyImp() {
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
                            insert(respondStudy.getListWords());
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

    private void insert(List<WordsStudentEntity> wordsStudentEntityList) {
        this.mWordsStudentEntityList = wordsStudentEntityList;
    }


    @Override
    public ChatMessageInfo getStudyMessage(String chatRoomID) {
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        int allcount = mWordsStudentEntityList.size();
        if (mListIndex < allcount) {
            chatMessageInfo.setStatus(App.MESSAGE_STATUS_SENDING);
            chatMessageInfo.setChatRoomID(chatRoomID);
            chatMessageInfo.setMessageType(App.MESSAGE_TYPE_CARD);
            chatMessageInfo.setMessageTitle(mWordsStudentEntityList.get(mListIndex).getEnglish());
            chatMessageInfo.setMessageContent(mWordsStudentEntityList.get(mListIndex).getChinese());
            chatMessageInfo.setMessageImgUrl(mWordsStudentEntityList.get(mListIndex).getImgurl());
            chatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
            chatMessageInfo.setSendMobile(chatRoomID);
            //chatMessageInfo.setSendName("贝贝");
            ++mListIndex;
            return chatMessageInfo;
        } else {
            ++allStudyCount;
            if (allStudyCount <= 3) {
                mListIndex = 0;
            } else {
                mIslast = true;
            }
        }
        return chatMessageInfo;
    }
    @Override
    public void updateStateWrong(){
        int previousIndex=mListIndex-1;
        mWordsStudentEntityList.get(previousIndex).setFirstwrongcount(1);
        post();
    }
    @Override
    public void updateStateCorrect(){
        int previousIndex=mListIndex-1;
        mWordsStudentEntityList.get(previousIndex).setFirstcorrectcount(1);
        post();
    }
    private void post(){
        int previousIndex=mListIndex-1;
        Gson gson = new Gson();
        jsonMessage = gson.toJson(mWordsStudentEntityList.get(previousIndex));
        GsonRequest<RespondStudy> gsonRequest = new GsonRequest<RespondStudy>(Request.Method.POST,
                UrlUtils.updateNowDayWordStateApiUrl(jsonMessage), RespondStudy.class,
                new Response.Listener<RespondStudy>() {
                    @Override
                    public void onResponse(RespondStudy respondStudy) {
                        if (respondStudy.getCode() == 0) {

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
                header.put("Content-Type", "application/json");
                header.put("token", App.token);
                return header;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonMessage.getBytes();
            }
        };
        VolleyUtils.addQueue(gsonRequest, "chatRequest");
    }

    @Override
    public boolean isLast() {
        return mIslast;
    }

}
