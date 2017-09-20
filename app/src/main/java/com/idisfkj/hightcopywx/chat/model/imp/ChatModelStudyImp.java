package com.idisfkj.hightcopywx.chat.model.imp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.RespondStudy;
import com.idisfkj.hightcopywx.beans.WordsStudentEntity;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudy;
import com.idisfkj.hightcopywx.util.GsonRequest;
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
    private List<WordsStudentEntity> mWordsStudentEntityList=null;
    private boolean mIslast=false;
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
    private void insert(List<WordsStudentEntity> wordsStudentEntityList){
        this.mWordsStudentEntityList =wordsStudentEntityList;
    }



    @Override
    public ChatMessageInfo getStudyMessage(String chatRoomID) {
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        Iterator<WordsStudentEntity> iterator=mWordsStudentEntityList.iterator();
        if (iterator.hasNext()) {
            chatMessageInfo.setStatus(App.MESSAGE_STATUS_SENDING);
            chatMessageInfo.setChatRoomID(chatRoomID);
            chatMessageInfo.setMessageType(App.MESSAGE_TYPE_CARD);
            chatMessageInfo.setMessageTitle(iterator.next().getEnglish());
            chatMessageInfo.setMessageContent(iterator.next().getChinese());
            chatMessageInfo.setMessageImgUrl(iterator.next().getImgurl());
            chatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
            chatMessageInfo.setSendMobile(chatRoomID);
            //chatMessageInfo.setSendName("贝贝");
            return chatMessageInfo;
        }else {
            mIslast=true;
        }
        return chatMessageInfo;
    }

    @Override
    public boolean isLast() {
        return mIslast;
    }

}
