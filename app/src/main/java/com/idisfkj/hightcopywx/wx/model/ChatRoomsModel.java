package com.idisfkj.hightcopywx.wx.model;

import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.wx.view.ChatRoomsView;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public interface ChatRoomsModel {
    void initData(ChatRoomsDataHelper helper, ChatRoomsView chatRoomsView);
}
