package com.idisfkj.hightcopywx.injection.components;

import com.idisfkj.hightcopywx.find.widget.FindFragment;
import com.idisfkj.hightcopywx.injection.ActivityScope;
import com.idisfkj.hightcopywx.injection.modules.FindModules;

import dagger.Component;

/**
 * Created by fvelement on 2017/10/27.
 */
@Component(modules = {FindModules.class},dependencies = {AppComponent.class})
@ActivityScope
public interface FindComponent {
    void inject(FindFragment findFragment);
}
