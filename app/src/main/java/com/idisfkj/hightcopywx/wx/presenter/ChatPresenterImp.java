package com.idisfkj.hightcopywx.wx.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.wx.model.ChatModel;
import com.idisfkj.hightcopywx.wx.model.ChatModelImp;
import com.idisfkj.hightcopywx.wx.model.TranslateChatModelImp;
import com.idisfkj.hightcopywx.wx.view.ChatView;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatPresenterImp implements ChatPresenter, ChatModel.requestListener, ChatModel.cursorListener {
    private ChatView mChatView;
    private ChatModel mChatModel;

    public ChatPresenterImp(ChatView chatView, int chatType) {
        mChatView = chatView;
        if (chatType == App.CHAT_TYPE_CHINESETOENGLISH)
            mChatModel = new TranslateChatModelImp(UrlUtils.ZHTOEN);
        else if (chatType == App.CHAT_TYPE_ENGLISHTOCHINESE)
            mChatModel = new TranslateChatModelImp(UrlUtils.ENTOGH);
        else if (chatType == App.CHAT_TYPE_ENGLISH_STUDY)
            mChatModel = new ChatModelImp();
        else
            mChatModel=new ChatModelImp();
    }

    @Override
    public void sendData(ChatMessageInfo chatMessageInfo, ChatMessageDataHelper helper) {
        mChatModel.insertData(chatMessageInfo, helper);
        mChatModel.requestData(this, chatMessageInfo, helper);
    }

    @Override
    public void receiveData(Intent intent, ChatMessageDataHelper helper) {
        Bundle bundle = intent.getExtras();
        ChatMessageInfo info = (ChatMessageInfo) bundle.getSerializable("chatMessageInfo");
        mChatModel.insertData(info, helper);
    }

    @Override
    public void initData(ChatMessageDataHelper helper, String mRegId, String mNumber, String userName) {
        mChatModel.initData(helper, mRegId, mNumber, userName);
    }

    @Override
    public void loadData(Context context, int _id) {
        mChatModel.getUserInfo(context, this, _id);
    }

    @Override
    public void cleanUnReadNum(Context context, String regId, String number, int unReadNum) {
        mChatModel.updateUnReadNum(context, regId, number, unReadNum);
    }

    @Override
    public void updateLasterContent(Context context, String regId, String number) {
        mChatModel.updateLasterContent(context, regId, number);
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
        mChatView.loadUserInfo(regId, number, userName, unReadNum);
    }
}
