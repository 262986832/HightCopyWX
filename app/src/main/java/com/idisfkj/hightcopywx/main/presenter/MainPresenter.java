package com.idisfkj.hightcopywx.main.presenter;

import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public interface MainPresenter {
    void switchNavigation(int id);

    void switchActivity();

    void receiveData(ChatMessageInfo chatMessageInfo);

    void uploadHeadUrl(String headurl);
}
