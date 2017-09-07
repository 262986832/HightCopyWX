package com.idisfkj.hightcopywx.wx.presenter;

import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.wx.model.ChatRoomsModel;
import com.idisfkj.hightcopywx.wx.model.ChatRoomsModelImp;
import com.idisfkj.hightcopywx.wx.view.ChatRoomsView;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsPresentImp implements ChatRoomsPresent {

    private ChatRoomsModel mChatRoomsModel;
    private ChatRoomsView mChatRoomsView;

    public ChatRoomsPresentImp(ChatRoomsView chatRoomsView) {
        mChatRoomsView = chatRoomsView;
        mChatRoomsModel = new ChatRoomsModelImp();
    }

    @Override
    public void initData(ChatRoomsDataHelper helper,InitRoomsDataLinsener initRoomsDataLinsener) {
        initRoomsDataLinsener.onInitDataing();
        mChatRoomsModel.initData(helper,initRoomsDataLinsener);

    }

}
