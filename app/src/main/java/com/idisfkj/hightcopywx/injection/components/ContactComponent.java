package com.idisfkj.hightcopywx.injection.components;

import com.idisfkj.hightcopywx.contact.ContactFragment;
import com.idisfkj.hightcopywx.injection.ActivityScope;
import com.idisfkj.hightcopywx.injection.modules.ContactModules;

import dagger.Component;

/**
 * Created by fvelement on 2017/10/27.
 */
@Component(modules = {ContactModules.class},dependencies = {AppComponent.class})
@ActivityScope
public interface ContactComponent {
    void inject(ContactFragment contactFragment);
}
