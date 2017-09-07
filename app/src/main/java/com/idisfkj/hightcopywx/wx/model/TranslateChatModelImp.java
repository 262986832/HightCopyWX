package com.idisfkj.hightcopywx.wx.model;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.BaiduFanyiResponse;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.util.GsonRequest;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by fvelement on 2017/9/4.
 */

public class TranslateChatModelImp extends ChatModelImp {
    private int type;

    public TranslateChatModelImp(int type) {
        this.type = type;
    }

    @Override
    public void requestData(final requestListener listener, ChatMessageInfo chatMessageInfo, final ChatMessageDataHelper helper) {

        GsonRequest<BaiduFanyiResponse> gsonRequest = new GsonRequest<BaiduFanyiResponse>(Request.Method.POST,
                UrlUtils.getBaiduTranslateApiUrl(chatMessageInfo.getMessageContent(),type), BaiduFanyiResponse.class,
                new Response.Listener<BaiduFanyiResponse>() {
                    @Override
                    public void onResponse(BaiduFanyiResponse baiduFanyiResponse) {
                        if (baiduFanyiResponse.getTrans_result()!=null && baiduFanyiResponse.getTrans_result().size()>0){
                            String result=(String) baiduFanyiResponse.getTrans_result().get(0).getDst();
//                            ChatMessageInfo mChatMessageInfo = new ChatMessageInfo(result, 0, CalendarUtils.getCurrentDate(),
//                                    number, regId, SharedPreferencesManager.getString("userPhone", ""));
//                            listener.onSucceed(mChatMessageInfo, helper);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage(), error);
                    }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + App.APP_SECRET_KEY);
                return header;
            }
        };
        VolleyUtils.addQueue(gsonRequest, "chatRequest");
    }

}
