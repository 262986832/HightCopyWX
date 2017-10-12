package com.idisfkj.hightcopywx.chat.model.imp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModel;
import com.idisfkj.hightcopywx.util.SpeechRecognizerService;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatModelBase implements ChatModel {

    public ChatModelBase() {

    }

    @Override
    public void requestData(final requestListener listener, ChatMessageInfo chatMessageInfo) {
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(chatMessageInfo);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST
                , UrlUtils.chatTopicUrl(jsonMessage, chatMessageInfo.getChatRoomID())
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println();
                //listener.onSucceed(mChatMessageInfo, helper);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("TAG", volleyError.getMessage());
                listener.onRequestError(volleyError.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + App.APP_SECRET_KEY);
                return header;
            }
        };
        VolleyUtils.addQueue(jsonObjectRequest, "chatRequest");
    }
    //保存语音识别
    @Override
    public void saveMessageVoice(final saveMessageVoiceListener listener, final String voiceName) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(SpeechRecognizerService.mVoicePath, voiceName, App.voiceUploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                if (info.isOK()) {

                }
            }
        }, null);
    }

}
