package com.idisfkj.hightcopywx.chat.presenter.imp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudy;
import com.idisfkj.hightcopywx.chat.model.imp.ChatModelStudyImp;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatPresenterStudy extends ChatPresenterBase implements ChatModelStudy.initListener {
    private ChatModelStudy mStudyModel;
    private ChatMessageInfo mChatMessageInfo;
    private String mChatRoomID;
    private String mRoleID;
    private int mTempWrongCount=0;
    private MediaPlayer mediaPlayer;
    public ChatPresenterStudy() {
        mChatModel = new ChatModelStudyImp();
        mStudyModel = (ChatModelStudyImp) mChatModel;
        mRoleID = SharedPreferencesManager.getString("RoleID", "");
        if (mRoleID.equals("baby"))
            mStudyModel.initData(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

    public void startStudy(String chatRoomId) {
        mChatRoomID = chatRoomId;
        mChatMessageInfo = mStudyModel.getStudyMessage(chatRoomId);
        String voiceurl=mChatMessageInfo.getMessageVoiceUrl();
        if(StringUtils.isBlank(voiceurl)){
            mediaPlayerPlay();
            //speechSynthesizerService.play(mChatMessageInfo.getMessageTitle());
        }

        mTempWrongCount=0;
        super.sendData(mChatMessageInfo);
        //mViewRef.get().onSpeechRecognize();
    }
    private void mediaPlayerPlay(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.reset();
                try {
                    String vocieurl=App.BOOK_VOICE_URL+mChatMessageInfo.getMessageTitle()+".mp3";
                    mediaPlayer.setDataSource(vocieurl);
                    mediaPlayer.prepare();//prepare之后自动播放
                    mediaPlayer.start();
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            mediaPlayer.start();
//                        }
//                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public void sendData(ChatMessageInfo chatMessageInfo) {
        //判断识别单词是否为同音词
        mStudyModel.getWordIPA(chatMessageInfo.getMessageContent());
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
