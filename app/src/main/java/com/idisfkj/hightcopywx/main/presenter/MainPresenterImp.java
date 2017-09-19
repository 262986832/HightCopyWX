package com.idisfkj.hightcopywx.main.presenter;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.base.presenter.BasePresenter;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.UnReadNumber;
import com.idisfkj.hightcopywx.beans.eventbus.RestartLoader;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.main.model.MainModel;
import com.idisfkj.hightcopywx.main.model.MainModelImp;
import com.idisfkj.hightcopywx.main.view.MainView;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class MainPresenterImp extends BasePresenter<MainView> implements MainPresenter {

    private MainModel mMainModel;
    private ChatMessageDataHelper mChatMessageDataHelper;
    private ChatRoomsDataHelper mChatRoomsDataHelper;

    public MainPresenterImp() {
        mMainModel = new MainModelImp();
        mChatMessageDataHelper = new ChatMessageDataHelper(App.getAppContext());
        mChatRoomsDataHelper = new ChatRoomsDataHelper(App.getAppContext());
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.ll_wx:
                mViewRef.get().switchWX();
                break;
            case R.id.ll_address:
                mViewRef.get().switchAddressBook();
                break;
            case R.id.ll_find:
                mViewRef.get().switchFind();
                break;
            case R.id.ll_me:
                mViewRef.get().switchMe();
                break;
        }
        mViewRef.get().switchAlpha(id);
    }

    @Override
    public void switchActivity() {
        mViewRef.get().jumpChatActivity();
    }


    @Override
    public void receiveData(ChatMessageInfo chatMessageInfo) {
        RestartLoader restartLoader = new RestartLoader();
        String ownMobile = SharedPreferencesManager.getString("userPhone", "");
        if (ownMobile.equals(chatMessageInfo.getSendMobile())) {
            chatMessageInfo.setSendOrReciveFlag(App.SEND_FLAG);
        } else {
            chatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
        }
        chatMessageInfo.setStatus(App.MESSAGE_STATUS_SUCCESS);
        int i = mChatMessageDataHelper.updateStatus(App.MESSAGE_STATUS_SUCCESS, chatMessageInfo.getMessageID());
        if (i == 0) {
            mChatMessageDataHelper.insert(chatMessageInfo);
            //发送刷新数据通知
            EventBus.getDefault().post(restartLoader);
        }
        String chatRoomID = chatMessageInfo.getChatRoomID();
        int unReadNumber = SharedPreferencesManager.getInt("unReadNumber" + chatRoomID, 0);
        unReadNumber = unReadNumber + 1;
        SharedPreferencesManager.putInt("unReadNumber" + chatRoomID, unReadNumber).commit();
        mChatRoomsDataHelper.update(unReadNumber, chatRoomID);
        //更新总的未读
        setAllUnReadNumber();

    }

    private void setAllUnReadNumber() {
        int oldCount = SharedPreferencesManager.getInt("unAllReadNumber", 0);
        int unAllReadNumber = oldCount + 1;
        SharedPreferencesManager.putInt("unAllReadNumber", unAllReadNumber).commit();

        //设置main气泡
        UnReadNumber unread = new UnReadNumber();
        unread.setUnReadNumber(unAllReadNumber);
        EventBus.getDefault().post(unread);
    }

}
