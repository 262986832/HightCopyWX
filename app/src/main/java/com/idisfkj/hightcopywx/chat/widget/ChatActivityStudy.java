package com.idisfkj.hightcopywx.chat.widget;

import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterBase;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterStudy;

/**
 * Created by fvelement on 2017/9/15.
 */

public class ChatActivityStudy extends ChatActivity{
    @Override
    protected ChatPresenterBase createPresenter() {
        return new ChatPresenterStudy(mChatRoomID);
    }

    @Override
    public void onInitDataComplete() {
        ((ChatPresenterStudy)mPresenter).startStudy(mChatRoomID);
    }
}
