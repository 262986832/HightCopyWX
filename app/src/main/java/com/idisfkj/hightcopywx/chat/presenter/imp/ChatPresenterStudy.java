package com.idisfkj.hightcopywx.chat.presenter.imp;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.eventbus.PlaySound;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudy;
import com.idisfkj.hightcopywx.chat.model.imp.ChatModelStudyImp;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatPresenterStudy extends ChatPresenterBase implements ChatModelStudy.initListener,ChatModelStudy.IsSameListener {
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
        String voiceurl=mChatMessageInfo.getMessageVoiceUrl();
        if(StringUtils.isBlank(voiceurl)){
            //发送给聊天处理线程
            PlaySound playSound=new PlaySound();
            playSound.setSoundName(App.BOOK_VOICE_URL+mChatMessageInfo.getMessageTitle()+".mp3");
            EventBus.getDefault().post(playSound);
            //speechSynthesizerService.play(mChatMessageInfo.getMessageTitle());
        }

        mTempWrongCount=0;
        super.sendData(mChatMessageInfo);
        //mViewRef.get().onSpeechRecognize();
    }



    @Override
    public void sendData(ChatMessageInfo chatMessageInfo) {
        //判断识别单词是否为同音词
        mStudyModel.isSame(this,mChatMessageInfo.getMessageTitle(),chatMessageInfo.getMessageTitle());
        super.sendData(chatMessageInfo);

    }

    @Override
    public void onInitSucceed() {
        mViewRef.get().onInitDataComplete();
    }

    @Override
    public void onInitError(String errorMessage) {

    }

    @Override
    public void onisSameComplete(boolean isSame) {
        if (mRoleID.equals("baby")) {
            if (!mStudyModel.isLast()) {
                if (isSame) {
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
}
