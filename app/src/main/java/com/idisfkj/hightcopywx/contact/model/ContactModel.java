package com.idisfkj.hightcopywx.contact.model;

import com.idisfkj.hightcopywx.beans.WordsEntity;

import java.util.List;

/**
 * Created by fvelement on 2017/10/10.
 */

public interface ContactModel {
    void getWordData(GetWordListener getWordListener, int page);
    interface GetWordListener{
        void onGetWordSuccess(List<WordsEntity> WordEntityList);
        void onGetWordFail();
    }
}
