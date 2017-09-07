package com.idisfkj.hightcopywx.wx.model;

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
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatModelImp implements ChatModel {

    private ChatMessageInfo mChatMessageInfo;

    @Override
    public void requestData(final requestListener listener, ChatMessageInfo chatMessageInfo, final ChatMessageDataHelper helper) {
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(chatMessageInfo);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlUtils.chatTopicUrl(jsonMessage, chatMessageInfo.getChatRoomID())
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                listener.onSucceed(mChatMessageInfo, helper);
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
    public void initData(ChatMessageDataHelper helper, String mRegId, String mNumber, String userName) {
        if (mRegId.equals(App.DEVELOPER_ID)) {
//            ChatMessageInfo info = new  new ChatMessageInfo("hello", 1, CalendarUtils.getCurrentDate(),
//                    chatToMobile, chatToMobile);
//            helper.insert(info);
        }
        helper = null;
    }

    @Override
    public void getUserInfo(Context context, cursorListener listener, int _id) {
        ChatRoomsDataHelper wxHelper = new ChatRoomsDataHelper(context);
//        Cursor cursor = wxHelper.query(_id);
//        if (cursor.moveToFirst()) {
//            String mRegId = CursorUtils.formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.REGID);
//            String mNumber = CursorUtils.formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.MOBILE);
//            String userName = CursorUtils.formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.TITLE);
//            int unReadNum = CursorUtils.formatInt(cursor, ChatRoomsDataHelper.WXItemDataInfo.UNREAD_NUM);
//            listener.onSucceed(mRegId, mNumber, userName, unReadNum);
//        }
//        cursor.close();
        wxHelper = null;
    }

    @Override
    public void updateUnReadNum(Context context, String regId, String number, int unReadNum) {
        SharedPreferencesManager.putInt("unReadNum", SharedPreferencesManager.getInt("unReadNum") - unReadNum).commit();
        ChatRoomsDataHelper chatRoomsDataHelper = new ChatRoomsDataHelper(context);
        chatRoomsDataHelper.update(0, regId);
        chatRoomsDataHelper = null;
    }

    @Override
    public void updateLasterContent(Context context, String regId, String number) {
        ChatMessageDataHelper chatHelper = new ChatMessageDataHelper(context);
//        ChatRoomsDataHelper wxHelper = new ChatRoomsDataHelper(context);
//        Cursor cursor = chatHelper.query(number, regId);
//        String lasterContent = null;
//        String time = null;
//        if (cursor.moveToFirst()) {
//            lasterContent = CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.MESSAGE);
//            time = CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.TIME);
//        }
//        cursor.close();
//        wxHelper.update(lasterContent, time, regId, number);
//        chatHelper = null;
//        wxHelper = null;
    }


}
