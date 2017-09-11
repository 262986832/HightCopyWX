package com.idisfkj.hightcopywx.main.presenter;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.UnReadNumber;
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
public class MainPresenterImp implements MainPresenter {

    private MainView mMianViw;
    private MainModel mMainModel;
    private ChatMessageDataHelper mChatMessageDataHelper;
    private ChatRoomsDataHelper mChatRoomsDataHelper;

    public MainPresenterImp(MainView mainView) {
        mMianViw = mainView;
        mMainModel = new MainModelImp();
        mChatMessageDataHelper = new ChatMessageDataHelper(App.getAppContext());
        mChatRoomsDataHelper =new ChatRoomsDataHelper(App.getAppContext());
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.ll_wx:
                mMianViw.switchWX();
                break;
            case R.id.ll_address:
                mMianViw.switchAddressBook();
                break;
            case R.id.ll_find:
                mMianViw.switchFind();
                break;
            case R.id.ll_me:
                mMianViw.switchMe();
                break;
        }
        mMianViw.switchAlpha(id);
    }

    @Override
    public void switchActivity() {
        mMianViw.jumpChatActivity();
    }


    @Override
    public void receiveData(ChatMessageInfo chatMessageInfo) {
        String ownMobile= SharedPreferencesManager.getString("userPhone","");
        if (ownMobile.equals(chatMessageInfo.getSendMobile())){
            chatMessageInfo.setSendOrReciveFlag(App.SEND_FLAG);
            int i=mChatMessageDataHelper.updateStatus(App.MESSAGE_STATUS_SUCCESS,chatMessageInfo.getMessageID());
            if(i==0){
                chatMessageInfo.setStatus(App.MESSAGE_STATUS_SUCCESS);
                mChatMessageDataHelper.insert(chatMessageInfo);
            }
        }else{
            chatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
            chatMessageInfo.setStatus(App.MESSAGE_STATUS_SUCCESS);
            mChatMessageDataHelper.insert(chatMessageInfo);
        }
        String chatRoomID=chatMessageInfo.getChatRoomID();
        int unReadNumber=SharedPreferencesManager.getInt("unReadNumber"+chatRoomID,0);
        unReadNumber=unReadNumber+1;
        SharedPreferencesManager.putInt("unReadNumber"+chatRoomID,unReadNumber).commit();
        mChatRoomsDataHelper.update(unReadNumber,chatRoomID);
        //更新总的未读
        setAllUnReadNumber();

    }
    private void setAllUnReadNumber(){
        int oldCount=SharedPreferencesManager.getInt("unAllReadNumber",0);
        int unAllReadNumber=oldCount+1;
        SharedPreferencesManager.putInt("unAllReadNumber",unAllReadNumber).commit();

        //设置main气泡
        UnReadNumber unread=new UnReadNumber();
        unread.setUnReadNumber(unAllReadNumber);
        EventBus.getDefault().post(unread);
    }
}
