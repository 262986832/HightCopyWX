package com.idisfkj.hightcopywx.wx.model;

import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.wx.presenter.ChatRoomsPresent;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public interface ChatRoomsModel {
    void initData(ChatRoomsDataHelper helper,ChatRoomsPresent.InitRoomsDataLinsener initRoomsDataLinsener);
}
