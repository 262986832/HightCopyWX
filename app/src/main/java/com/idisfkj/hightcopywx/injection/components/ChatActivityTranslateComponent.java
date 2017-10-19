package com.idisfkj.hightcopywx.injection.components;

import com.idisfkj.hightcopywx.chat.widget.ChatActivityTranslate;
import com.idisfkj.hightcopywx.injection.ActivityScope;
import com.idisfkj.hightcopywx.injection.modules.ChatActivityTranslateModule;

import dagger.Component;

/**
 * Created by fvelement on 2017/10/18.
 */
@ActivityScope
@Component(modules = {ChatActivityTranslateModule.class},dependencies = {AppComponent.class})
public interface ChatActivityTranslateComponent {
    void inject(ChatActivityTranslate chatActivityTranslate);

}
