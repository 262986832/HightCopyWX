package com.idisfkj.hightcopywx.injection.modules;

import com.idisfkj.hightcopywx.chat.model.ChatModelTranslate;
import com.idisfkj.hightcopywx.chat.model.imp.ChatModelTranslateImpl;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterTranslate;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterTranslateImpl;
import com.idisfkj.hightcopywx.chat.widget.ChatActivityTranslate;
import com.idisfkj.hightcopywx.util.SpeechSynthesizerService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fvelement on 2017/10/18.
 */
@Module
public class ChatActivityTranslateModule {
    private ChatActivityTranslate chatActivityTranslate;

    public ChatActivityTranslateModule(ChatActivityTranslate chatActivityTranslate) {
        this.chatActivityTranslate = chatActivityTranslate;
    }
    @Provides
    ChatActivityTranslate chatActivityTranslate(){
        return this.chatActivityTranslate;
    }
    @Provides
    ChatModelTranslate chatModelTranslate(){
        return new ChatModelTranslateImpl();
    }

    @Provides
    ChatPresenterTranslate presenterTranslate(ChatModelTranslate chatModelTranslate, SpeechSynthesizerService speechSynthesizerService, ChatActivityTranslate chatActivityTranslate){
        return new ChatPresenterTranslateImpl(chatModelTranslate,speechSynthesizerService,chatActivityTranslate);
    }
}
