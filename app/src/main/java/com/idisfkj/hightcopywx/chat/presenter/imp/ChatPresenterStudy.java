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
        super.sendData(mChatMessageInfo);
    }

    @Override
    public void sendData(ChatMessageInfo chatMessageInfo) {
        super.sendData(chatMessageInfo);
        if (mRoleID.equals("baby")) {
            if (!mStudyModel.isLast()) {
                if (mChatMessageInfo != null && chatMessageInfo.getMessageContent().equals(mChatMessageInfo.getMessageTitle())) {
                    mStudyModel.updateStateCorrect();
                } else {
                    mStudyModel.updateStateWrong();
                }
            }
            this.startStudy(mChatRoomID);
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
