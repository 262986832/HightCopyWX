package com.idisfkj.hightcopywx.chat.presenter.imp;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudy;
import com.idisfkj.hightcopywx.chat.model.imp.ChatModelStudyImp;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatPresenterStudy extends ChatPresenterBase implements ChatModelStudy.initListener {
    private ChatModelStudy mStudyModel;
    private ChatMessageInfo mChatMessageInfo;
    private String mChatRoomID;
    private String mRoleID;
    private int mTempWrongCount=0;

    public ChatPresenterStudy() {
        mChatModel = new ChatModelStudyImp();
        mStudyModel = (ChatModelStudyImp) mChatModel;
        mRoleID = SharedPreferencesManager.getString("RoleID", "");
        if (mRoleID.equals("baby"))
            mStudyModel.initData(this);

    }

    public void startStudy(String chatRoomId) {
        mChatRoomID = chatRoomId;
        mChatMessageInfo = mStudyModel.getStudyMessage(chatRoomId);
        speechSynthesizerService.play(mChatMessageInfo.getMessageTitle());
        mTempWrongCount=0;
        super.sendData(mChatMessageInfo);
        //mViewRef.get().onSpeechRecognize();
    }

    @Override
    public void sendData(ChatMessageInfo chatMessageInfo) {
        super.sendData(chatMessageInfo);
        if (mRoleID.equals("baby")) {
            if (!mStudyModel.isLast()) {
                if (mChatMessageInfo != null && chatMessageInfo.getMessageContent().toLowerCase().equals(mChatMessageInfo.getMessageTitle().toLowerCase())) {
                    mStudyModel.updateStateCorrect();
                    this.startStudy(mChatRoomID);
                } else {
                    mStudyModel.updateStateWrong();
                    if(mTempWrongCount<3){
                        speechSynthesizerService.play("try again.");
                        speechSynthesizerService.play(mChatMessageInfo.getMessageTitle());
                        //mViewRef.get().onSpeechRecognize();
                        ++mTempWrongCount;
                    }else {
                        this.startStudy(mChatRoomID);
                    }
                }
            }

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
