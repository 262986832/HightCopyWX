package com.idisfkj.hightcopywx.chat.widget;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.eventbus.PlaySound;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterBase;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterStudy;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.OnClick;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatActivityStudy extends ChatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected ChatPresenterBase createPresenter() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return new ChatPresenterStudy();

    }

    @Override
    public void onInitDataComplete() {
        ((ChatPresenterStudy) mPresenter).startStudy(mChatRoomID);
    }

    @OnClick(R.id.voice_button)
    @Override
    public void onVoiceClick() {
        speechRecognizerService.setParam("en_us");
        super.onVoiceClick();
    }

    @Override
    public void onSpeechRecognize() {
        onVoiceClick();
    }

    //收到数据更新消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMessage(PlaySound playSound) {
        mediaPlayerPlay(playSound.getSoundName());
    }

    private void mediaPlayerPlay(final String soundName) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(soundName);
            mediaPlayer.prepare();//prepare之后自动播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
