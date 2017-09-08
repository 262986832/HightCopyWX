package com.idisfkj.hightcopywx.chat.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;
import com.idisfkj.hightcopywx.chat.view.ChatView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatModelImp implements ChatModel {

    @Override
    public void initData(ChatMessageDataHelper helper, ChatView chatView) {

    }

    @Override
    public void requestData(final requestListener listener, ChatMessageInfo chatMessageInfo, final ChatMessageDataHelper helper) {
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
                listener.onError(volleyError.getMessage());
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

    @Override
    public void insertData(ChatMessageInfo info, ChatMessageDataHelper helper) {
        helper.insert(info);

    }


    @Override
    public void updateUnReadNum(Context context, String regId, String number, int unReadNum) {

    }

    @Override
    public void updateLasterContent(Context context, String regId, String number) {

    }


}
