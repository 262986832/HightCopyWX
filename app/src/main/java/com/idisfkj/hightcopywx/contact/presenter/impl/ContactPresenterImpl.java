package com.idisfkj.hightcopywx.contact.presenter.impl;

import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.contact.model.ContactModel;
import com.idisfkj.hightcopywx.contact.presenter.ContactPresenter;
import com.idisfkj.hightcopywx.contact.view.ContactView;

import java.util.List;

/**
 * Created by fvelement on 2017/10/19.
 */

public class ContactPresenterImpl implements ContactPresenter,ContactModel.GetWordListener {
    private ContactModel mContactModel;
    private ContactView mContactView;
    public ContactPresenterImpl(ContactView mContactView, ContactModel mContactModel) {
        this.mContactView = mContactView;
        this.mContactModel=mContactModel;
    }

    @Override
    public void getWordsData(int page) {
        mContactModel.getWordData(this,page);
    }

    @Override
    public void onGetWordSuccess(List<WordsEntity> wordsEntityList) {
        mContactView.onGetWordsSuccess(wordsEntityList);
    }

    @Override
    public void onGetWordFail() {

    }
}
