package com.idisfkj.hightcopywx.injection.components;

import com.idisfkj.hightcopywx.chat.widget.ChatActivityPractise;
import com.idisfkj.hightcopywx.injection.ActivityScope;
import com.idisfkj.hightcopywx.injection.modules.PractiseModule;

import dagger.Component;

/**
 * Created by fvelement on 2017/10/30.
 */
@ActivityScope
@Component(modules = {PractiseModule.class},dependencies = {AppComponent.class})
public interface PractiseComponent {
    void inject(ChatActivityPractise chatActivityPractise);
}
