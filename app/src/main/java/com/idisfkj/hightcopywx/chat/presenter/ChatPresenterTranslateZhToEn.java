package com.idisfkj.hightcopywx.chat.presenter;

import com.idisfkj.hightcopywx.chat.model.ChatModelTranslateImp;
import com.idisfkj.hightcopywx.util.UrlUtils;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatPresenterTranslateZhToEn extends ChatPresenterBase {
    public ChatPresenterTranslateZhToEn() {
        mChatModel = new ChatModelTranslateImp(UrlUtils.ZHTOEN);
    }
}
