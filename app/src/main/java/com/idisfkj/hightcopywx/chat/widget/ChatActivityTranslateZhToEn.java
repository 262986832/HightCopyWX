package com.idisfkj.hightcopywx.chat.widget;

import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterBase;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterTranslateEnToZh;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatActivityTranslateZhToEn extends  ChatActivity{
    @Override
    protected ChatPresenterBase createPresenter() {
        return new ChatPresenterTranslateEnToZh();
    }
}
