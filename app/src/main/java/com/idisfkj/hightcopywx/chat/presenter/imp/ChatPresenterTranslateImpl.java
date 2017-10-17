package com.idisfkj.hightcopywx.chat.presenter.imp;

import com.idisfkj.hightcopywx.base.presenter.BasePresenter;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModelTranslate;
import com.idisfkj.hightcopywx.chat.model.imp.ChatModelTranslateImpl;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterTranslate;
import com.idisfkj.hightcopywx.chat.view.ChatTranslateView;

/**
 * Created by fvelement on 2017/10/13.
 */

public class ChatPresenterTranslateImpl extends BasePresenter<ChatTranslateView> implements ChatPresenterTranslate,ChatModelTranslate.TranslateListner {
    private ChatModelTranslate chatModelTranslate;

    public ChatPresenterTranslateImpl() {
        chatModelTranslate=new ChatModelTranslateImpl();
    }

    @Override
    public void translate(int type,ChatMessageInfo chatMessageInfo) {
        chatModelTranslate.translate(this,type,chatMessageInfo);
    }

    @Override
    public void onTranslateComplete(ChatMessageInfo requestChatMessageInfo, ChatMessageInfo respondChatMessageInfo) {
        mViewRef.get().onTranslateComplete(requestChatMessageInfo,respondChatMessageInfo);
    }
}
