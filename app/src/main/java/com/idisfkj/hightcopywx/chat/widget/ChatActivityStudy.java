package com.idisfkj.hightcopywx.chat.widget;

import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterBase;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterStudy;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatActivityStudy extends ChatActivity{
    private ChatPresenterStudy mChatPresenterStudyImp;
    @Override
    protected ChatPresenterBase createPresenter() {
        return new ChatPresenterStudy();
    }

    @Override
    public void onInitDataComplete() {
        mChatPresenterStudyImp =(ChatPresenterStudy)mPresenter;
        mChatPresenterStudyImp.startStudy(mChatRoomID);
    }
}
