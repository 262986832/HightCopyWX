package com.idisfkj.hightcopywx.injection.modules;

import com.idisfkj.hightcopywx.chat.model.ChatModelPractise;
import com.idisfkj.hightcopywx.chat.model.imp.ChatModelPractiseImpl;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterPractise;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterPractiseImpl;
import com.idisfkj.hightcopywx.chat.view.ChatViewPractise;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by fvelement on 2017/10/30.
 */
@Module
public class PractiseModule {
    private ChatViewPractise mChatViewPractise;
    public PractiseModule(ChatViewPractise chatViewPractise) {
        this.mChatViewPractise = chatViewPractise;
    }
    @Provides
    public ChatModelPractise chatModelPractise(Retrofit retrofit){
        return new ChatModelPractiseImpl(retrofit);
    }
    @Provides
    public ChatPresenterPractise chatPresenterPractise(ChatModelPractise chatModelPractise){
        return new ChatPresenterPractiseImpl(chatModelPractise,mChatViewPractise);
    }
}
