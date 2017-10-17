package com.idisfkj.hightcopywx.chat.widget;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterBase;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterTranslateZhToEn;

import butterknife.OnClick;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatActivityTranslateZhToEn extends ChatActivity {
    @Override
    protected ChatPresenterBase createPresenter() {
        return new ChatPresenterTranslateZhToEn();
    }
    @OnClick(R.id.voice_button)
    @Override
    public void onVoiceClick() {
        speechRecognizerService.setParam("zh_cn");
        super.onVoiceClick();
    }
}
