package com.idisfkj.hightcopywx.wx.model;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.WXItemInfo;
import com.idisfkj.hightcopywx.dao.WXDataHelper;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class WXModelImp implements WXModel {
    private List<WXItemInfo> mList;
    private WXItemInfo wxItemInfo;

    @Override
    public void initData(WXDataHelper helper) {
        mList = new ArrayList<>();
        wxItemInfo = new WXItemInfo();
        if (!SharedPreferencesManager.getString("regId").equals(App.DEVELOPER_ID)) {
            wxItemInfo.setTitle(App.DEVELOPER_NAME);
            wxItemInfo.setContent(App.DEVELOPER_MESSAGE);
            wxItemInfo.setTime(CalendarUtils.getCurrentDate());
            wxItemInfo.setRegId(App.DEVELOPER_ID);
            wxItemInfo.setMobile(SharedPreferencesManager.getString("userPhone"));
            wxItemInfo.setChatType(App.CHAT_TYPE_ENGLISH_STUDY);
            wxItemInfo.setChattomobile(App.DEVELOPER_NUMBER);

            helper.insert(wxItemInfo);
            wxItemInfo.setTitle("汉英翻译");
            wxItemInfo.setContent("您说的中文，将被翻译成英文。");
            wxItemInfo.setTime(CalendarUtils.getCurrentDate());
            wxItemInfo.setRegId("Chinese to english");
            wxItemInfo.setMobile(SharedPreferencesManager.getString("userPhone"));
            wxItemInfo.setChatType(App.CHAT_TYPE_CHINESETOENGLISH);
            wxItemInfo.setChattomobile("001");
            helper.insert(wxItemInfo);

            wxItemInfo.setTitle("英汉翻译");
            wxItemInfo.setContent("您说英文，将被翻译成中文。");
            wxItemInfo.setTime(CalendarUtils.getCurrentDate());
            wxItemInfo.setRegId("English to chinese");
            wxItemInfo.setMobile(SharedPreferencesManager.getString("userPhone"));
            wxItemInfo.setChatType(App.CHAT_TYPE_ENGLISHTOCHINESE);
            wxItemInfo.setChattomobile("002");
            helper.insert(wxItemInfo);
        }
    }
}
