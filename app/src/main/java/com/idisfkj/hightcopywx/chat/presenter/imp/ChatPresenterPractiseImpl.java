package com.idisfkj.hightcopywx.chat.presenter.imp;

import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.chat.model.ChatModelPractise;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterPractise;
import com.idisfkj.hightcopywx.chat.view.ChatViewPractise;

import java.util.List;

/**
 * Created by fvelement on 2017/10/30.
 */

public class ChatPresenterPractiseImpl implements ChatPresenterPractise,ChatModelPractise.GetWordListener {
    private ChatModelPractise mChatModelPractise;
    private ChatViewPractise mChatViewPractise;

    public ChatPresenterPractiseImpl(ChatModelPractise mChatModelPractise, ChatViewPractise mChatViewPractise) {
        this.mChatModelPractise = mChatModelPractise;
        this.mChatViewPractise = mChatViewPractise;
    }

    @Override
    public void getWordsData(int page) {
        mChatModelPractise.getWordData(this,page);
    }

    @Override
    public void onGetWordSuccess(List<WordsEntity> wordsEntityList) {
        mChatViewPractise.onGetWordsSuccess(wordsEntityList);
    }

    @Override
    public void onGetWordFail() {

    }
}
