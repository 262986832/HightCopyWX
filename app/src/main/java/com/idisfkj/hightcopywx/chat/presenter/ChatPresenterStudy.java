package com.idisfkj.hightcopywx.chat.presenter;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudyImp;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudy;
import com.idisfkj.hightcopywx.util.ToastUtils;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatPresenterStudy extends ChatPresenterBase implements ChatModelStudy.initListener {
    private ChatModelStudy mStudyModel;
    private ChatMessageInfo mChatMessageInfo;
    public ChatPresenterStudy() {
        mChatModel = new ChatModelStudyImp();
        mStudyModel =(ChatModelStudyImp)mChatModel ;
        mStudyModel.initData(this);
    }

    public void startStudy(String chatRoomId) {
        mChatMessageInfo=mStudyModel.getStudyMessage(chatRoomId);
        mChatMessageDataHelper.insert(mChatMessageInfo);
    }

    @Override
    public void sendData(ChatMessageInfo chatMessageInfo) {
        super.sendData(chatMessageInfo);
        if(chatMessageInfo.getMessageContent().equals(mChatMessageInfo.getMessageTitle())){
            ToastUtils.showShort("hh");
        }
    }
    @Override
    public void onInitSucceed() {
        mViewRef.get().onInitDataComplete();
    }

    @Override
    public void onInitError(String errorMessage) {

    }

}
