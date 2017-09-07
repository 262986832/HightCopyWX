package com.idisfkj.hightcopywx.wx.presenter;

import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.wx.model.ChatRoomsModel;
import com.idisfkj.hightcopywx.wx.model.ChatRoomsModelImp;
import com.idisfkj.hightcopywx.wx.view.WXView;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsPresentImp implements ChatRoomsPresent {

    private ChatRoomsModel mChatRoomsModel;
    private WXView mWXView;

    public ChatRoomsPresentImp(WXView wxView) {
        mWXView = wxView;
        mChatRoomsModel = new ChatRoomsModelImp();
    }

    @Override
    public void initData(ChatRoomsDataHelper helper) {
        mChatRoomsModel.initData(helper);
    }

}
