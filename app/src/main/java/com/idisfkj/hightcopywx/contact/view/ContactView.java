package com.idisfkj.hightcopywx.contact.view;

import com.idisfkj.hightcopywx.beans.WordsEntity;

import java.util.List;

/**
 * Created by fvelement on 2017/10/10.
 */

public interface ContactView {
    void onGetWordsSuccess(List<WordsEntity> wordsEntityList);
}
