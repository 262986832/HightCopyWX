package com.idisfkj.hightcopywx.chat.presenter;

import android.content.Context;
import android.content.CursorLoader;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModel;
import com.idisfkj.hightcopywx.chat.model.ChatModelImp;
import com.idisfkj.hightcopywx.chat.model.ChatModelTranslateImp;
import com.idisfkj.hightcopywx.chat.view.ChatView;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatPresenterImp implements ChatPresenter, ChatModel.requestListener, ChatModel.cursorListener {
    private ChatView mChatView;
    private ChatModel mChatModel;
    private ChatMessageDataHelper mHelper;

    public ChatPresenterImp(ChatView chatView, int chatType,Context context) {
        mChatView = chatView;
        mHelper = new ChatMessageDataHelper(context);
        if (chatType == App.CHAT_TYPE_CHINESETOENGLISH)
            mChatModel = new ChatModelTranslateImp(UrlUtils.ZHTOEN);
        else if (chatType == App.CHAT_TYPE_ENGLISHTOCHINESE)
            mChatModel = new ChatModelTranslateImp(UrlUtils.ENTOGH);
        else if (chatType == App.CHAT_TYPE_ENGLISH_STUDY)
            mChatModel = new ChatModelImp();
        else
            mChatModel=new ChatModelImp();
    }


    @Override
    public CursorLoader creatLoader(String charRoomid) {
        return  mHelper.getCursorLoader(charRoomid);
    }

    @Override
    public void initData() {

    }

    @Override
    public void sendData(ChatMessageInfo chatMessageInfo) {
        mChatModel.insertData(chatMessageInfo, mHelper);
        mChatModel.requestData(this, chatMessageInfo, mHelper);
    }

    @Override
    public void receiveData(ChatMessageInfo chatMessageInfo) {
        String ownMobile= SharedPreferencesManager.getString("userPhone","");
        if (ownMobile.equals(chatMessageInfo.getSendMobile())){
            chatMessageInfo.setSendOrReciveFlag(App.SEND_FLAG);
            mHelper.updateStatus(App.MESSAGE_STATUS_SUCCESS,chatMessageInfo.getMessageID());
        }else{
            chatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
            mChatModel.insertData(chatMessageInfo, mHelper);
        }

    }

    @Override
    public void cleanUnReadNum(String ownMobile, String chatRoomId) {

    }

    @Override
    public void updateLasterContent(String ownMobile, String chatRoomId) {

    }


    @Override
    public void onSucceed(ChatMessageInfo chatMessageInfo, ChatMessageDataHelper helper) {
        mChatModel.insertData(chatMessageInfo, helper);
    }

    @Override
    public void onError(String errorMessage) {
        ToastUtils.showShort("网络异常,请检查网络");
    }

    @Override
    public void onSucceed(String regId, String number, String userName, int unReadNum) {

    }
}
