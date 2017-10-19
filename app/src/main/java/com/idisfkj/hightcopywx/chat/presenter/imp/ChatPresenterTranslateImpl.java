package com.idisfkj.hightcopywx.chat.presenter.imp;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModelTranslate;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterTranslate;
import com.idisfkj.hightcopywx.chat.widget.ChatActivityTranslate;
import com.idisfkj.hightcopywx.util.SpeechSynthesizerService;

/**
 * Created by fvelement on 2017/10/13.
 */

public class ChatPresenterTranslateImpl  implements ChatPresenterTranslate,ChatModelTranslate.TranslateListner {
    private ChatModelTranslate chatModelTranslate;
    private SpeechSynthesizerService speechSynthesizerService;
    private ChatActivityTranslate chatActivityTranslate;

    public ChatPresenterTranslateImpl(ChatModelTranslate chatModelTranslate, SpeechSynthesizerService speechSynthesizerService, ChatActivityTranslate chatActivityTranslate) {
        this.chatModelTranslate = chatModelTranslate;
        this.speechSynthesizerService = speechSynthesizerService;
        this.chatActivityTranslate = chatActivityTranslate;
    }

    @Override
    public void translate(int type,ChatMessageInfo chatMessageInfo) {
        chatModelTranslate.translate(this,type,chatMessageInfo);
    }

    @Override
    public void onTranslateComplete(ChatMessageInfo requestChatMessageInfo, ChatMessageInfo respondChatMessageInfo) {
        speechSynthesizerService.play(respondChatMessageInfo.getMessageContent());
        chatActivityTranslate.onTranslateComplete(requestChatMessageInfo,respondChatMessageInfo);
    }
}
