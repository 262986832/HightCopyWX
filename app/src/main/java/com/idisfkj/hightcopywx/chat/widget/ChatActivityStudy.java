package com.idisfkj.hightcopywx.chat.widget;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterBase;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterStudy;

import butterknife.OnClick;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatActivityStudy extends ChatActivity{
    @Override
    protected ChatPresenterBase createPresenter() {
        return new ChatPresenterStudy();
    }

    @Override
    public void onInitDataComplete() {
        ((ChatPresenterStudy)mPresenter).startStudy(mChatRoomID);
    }

    @OnClick(R.id.voice_button)
    @Override
    public void onVoiceClick() {
        speechRecognizerService.setParam("en_us");
        super.onVoiceClick();

    }
}
