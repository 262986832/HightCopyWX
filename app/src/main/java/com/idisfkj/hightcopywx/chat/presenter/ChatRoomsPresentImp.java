package com.idisfkj.hightcopywx.chat.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;

import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.chat.model.ChatRoomsModel;
import com.idisfkj.hightcopywx.chat.model.ChatRoomsModelImp;
import com.idisfkj.hightcopywx.chat.view.ChatRoomsView;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsPresentImp implements ChatRoomsPresent {

    private ChatRoomsModel mChatRoomsModel;
    private ChatRoomsView mChatRoomsView;
    private ChatRoomsDataHelper mHelper;

    public ChatRoomsPresentImp(ChatRoomsView chatRoomsView, Context context) {
        mChatRoomsView = chatRoomsView;
        mChatRoomsModel = new ChatRoomsModelImp();
        mHelper = new ChatRoomsDataHelper(context);
    }

    @Override
    public Loader<Cursor> creatLoader(String ownMobile) {
        return  mHelper.getCursorLoader(ownMobile);
    }

    @Override
    public void initData() {
        mChatRoomsModel.initData(mHelper,mChatRoomsView);
    }

}
