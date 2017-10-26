package com.idisfkj.hightcopywx.contact.presenter.impl;

import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.contact.model.ContactModel;
import com.idisfkj.hightcopywx.contact.presenter.ContactPresenter;

import java.util.List;

/**
 * Created by fvelement on 2017/10/19.
 */

public class ContactPresenterImpl implements ContactPresenter,ContactModel.GetWordListener {
    @Override
    public void getWordsData(int page) {

    }

    @Override
    public void onGetWordSuccess(List<WordsEntity> WordEntityList) {

    }

    @Override
    public void onGetWordFail() {

    }
}
