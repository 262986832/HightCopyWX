package com.idisfkj.hightcopywx.injection.modules;

import com.idisfkj.hightcopywx.contact.model.ContactModel;
import com.idisfkj.hightcopywx.contact.model.impl.ContactModelImpl;
import com.idisfkj.hightcopywx.contact.presenter.ContactPresenter;
import com.idisfkj.hightcopywx.contact.presenter.impl.ContactPresenterImpl;
import com.idisfkj.hightcopywx.contact.view.ContactView;
import com.idisfkj.hightcopywx.injection.ContactScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by fvelement on 2017/10/27.
 */
@Module
public class ContactModules {
    private ContactView contactView;
    public ContactModules(ContactView contactView) {
        this.contactView = contactView;
    }
    @Provides
    @ContactScope
    public ContactModel contactModel(Retrofit retrofit){
        return new ContactModelImpl(retrofit);
    }
    @Provides
    @ContactScope
    public ContactPresenter contactPresenter(ContactModel contactModel){
        return new ContactPresenterImpl(contactView,contactModel);
    }

}
