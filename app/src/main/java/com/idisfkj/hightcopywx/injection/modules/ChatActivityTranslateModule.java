package com.idisfkj.hightcopywx.injection.modules;

import com.idisfkj.hightcopywx.chat.model.ChatModelTranslate;
import com.idisfkj.hightcopywx.chat.model.imp.ChatModelTranslateImpl;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterTranslate;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterTranslateImpl;
import com.idisfkj.hightcopywx.chat.widget.ChatActivityTranslate;
import com.idisfkj.hightcopywx.util.SpeechSynthesizerService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
    @Named("baidu")
    Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.fanyi.baidu.com/api/trans/vip/translate/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }
    @Provides
    ChatModelTranslate chatModelTranslate(@Named("baidu")Retrofit retrofit){
        return new ChatModelTranslateImpl(retrofit);
    }

    @Provides
    ChatPresenterTranslate presenterTranslate(ChatModelTranslate chatModelTranslate, SpeechSynthesizerService speechSynthesizerService, ChatActivityTranslate chatActivityTranslate){
        return new ChatPresenterTranslateImpl(chatModelTranslate,speechSynthesizerService,chatActivityTranslate);
    }


}
