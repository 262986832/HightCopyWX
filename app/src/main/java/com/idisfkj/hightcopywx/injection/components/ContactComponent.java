package com.idisfkj.hightcopywx.injection.components;

import com.idisfkj.hightcopywx.contact.ContactFragment;
import com.idisfkj.hightcopywx.injection.ContactScope;
import com.idisfkj.hightcopywx.injection.modules.ContactModules;

import dagger.Component;

/**
 * Created by fvelement on 2017/10/27.
 */
@ContactScope
@Component(modules = {ContactModules.class},dependencies = {AppComponent.class})
public interface ContactComponent {
    void inject(ContactFragment contactFragment);
}
