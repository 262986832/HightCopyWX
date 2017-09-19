package com.idisfkj.hightcopywx.chat.presenter.imp;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;

import com.idisfkj.hightcopywx.chat.presenter.ChatRoomsPresent;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.chat.model.ChatRoomsModel;
import com.idisfkj.hightcopywx.chat.model.imp.ChatRoomsModelImp;
import com.idisfkj.hightcopywx.chat.view.ChatRoomsView;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsPresentImp implements ChatRoomsPresent {

    private ChatRoomsModel mChatRoomsModel;
    private ChatRoomsView mChatRoomsView;
    private ChatRoomsDataHelper mChatRoomsDataHelper;

    public ChatRoomsPresentImp(ChatRoomsView chatRoomsView, Context context) {
        mChatRoomsView = chatRoomsView;
        mChatRoomsModel = new ChatRoomsModelImp();
        mChatRoomsDataHelper = new ChatRoomsDataHelper(context);
    }

    @Override
    public Loader<Cursor> creatLoader(String ownMobile,int page) {
        return  mChatRoomsDataHelper.getCursorLoader(ownMobile, page);
    }

    @Override
    public void initData() {
        mChatRoomsModel.initData(mChatRoomsDataHelper,mChatRoomsView);
    }

}
