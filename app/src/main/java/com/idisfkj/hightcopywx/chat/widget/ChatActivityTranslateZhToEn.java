package com.idisfkj.hightcopywx.chat.widget;

import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterBase;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterTranslateZhToEn;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatActivityTranslateZhToEn extends ChatActivity {
    @Override
    protected ChatPresenterBase createPresenter() {
        return new ChatPresenterTranslateZhToEn();
    }
}
