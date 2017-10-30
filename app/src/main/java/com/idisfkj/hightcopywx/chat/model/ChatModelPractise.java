package com.idisfkj.hightcopywx.chat.model;

import com.idisfkj.hightcopywx.beans.WordsEntity;

import java.util.List;

/**
 * Created by fvelement on 2017/10/30.
 */

public interface ChatModelPractise {
    void getWordData(GetWordListener getWordListener, int page);
    interface GetWordListener{
        void onGetWordSuccess(List<WordsEntity> wordsEntityList);
        void onGetWordFail();
    }
}
