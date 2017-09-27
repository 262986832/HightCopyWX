package com.idisfkj.hightcopywx.chat.model.imp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.BaiduFanyiResponse;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModel;
import com.idisfkj.hightcopywx.util.GsonRequest;
import com.idisfkj.hightcopywx.util.SpeechSynthesizerService;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by fvelement on 2017/9/4.
 */

public class ChatModelTranslateImp implements ChatModel {
    private int type;
    protected SpeechSynthesizerService speechSynthesizerService;

    public ChatModelTranslateImp(int type) {
        this.type = type;
        speechSynthesizerService = new SpeechSynthesizerService(App.getAppContext());
    }

    @Override
    public void requestData(final requestListener listener, final ChatMessageInfo chatMessageInfo) {

        GsonRequest<BaiduFanyiResponse> gsonRequest = new GsonRequest<BaiduFanyiResponse>(Request.Method.POST,
                UrlUtils.getBaiduTranslateApiUrl(chatMessageInfo.getMessageContent(), type), BaiduFanyiResponse.class,
                new Response.Listener<BaiduFanyiResponse>() {
                    @Override
                    public void onResponse(BaiduFanyiResponse baiduFanyiResponse) {
                        if (baiduFanyiResponse.getTrans_result() != null && baiduFanyiResponse.getTrans_result().size() > 0) {
                            String result = (String) baiduFanyiResponse.getTrans_result().get(0).getDst();
                            play(result);
                            ChatMessageInfo mChatMessageInfo = new ChatMessageInfo();
                            mChatMessageInfo.setStatus(App.MESSAGE_STATUS_SUCCESS);
                            mChatMessageInfo.setChatRoomID(chatMessageInfo.getChatRoomID());
                            mChatMessageInfo.setMessageContent(result);
                            mChatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
                            mChatMessageInfo.setSendMobile(chatMessageInfo.getSendMobile());
                            mChatMessageInfo.setSendName("贝贝翻译");
                            listener.onRequestSucceed(chatMessageInfo, mChatMessageInfo);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                listener.onRequestError(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + App.APP_SECRET_KEY);
                return header;
            }
        };
        VolleyUtils.addQueue(gsonRequest, "chatRequest");
    }

    @Override
    public void saveMessageVoice(saveMessageVoiceListener listener, String voiceName) {

    }


    private void play(String string) {
        speechSynthesizerService.play(string);
    }


}
