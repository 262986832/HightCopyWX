package com.idisfkj.hightcopywx.chat.presenter.imp;

import android.content.CursorLoader;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.base.presenter.BasePresenter;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.UnReadNumber;
import com.idisfkj.hightcopywx.chat.model.ChatModel;
import com.idisfkj.hightcopywx.chat.model.imp.ChatModelBase;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenter;
import com.idisfkj.hightcopywx.chat.view.ChatView;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatPresenterBase extends BasePresenter<ChatView> implements ChatPresenter,
        ChatModel.requestListener {
    protected ChatModel mChatModel;
    protected ChatRoomsDataHelper mChatRoomsDataHelper;
    protected ChatMessageDataHelper mChatMessageDataHelper;
    private int page;
    private int limit;

    public ChatPresenterBase() {
        mChatModel=new ChatModelBase();
        mChatRoomsDataHelper = new ChatRoomsDataHelper(App.getAppContext());
        mChatMessageDataHelper = new ChatMessageDataHelper(App.getAppContext());
    }


    @Override
    public CursorLoader creatLoader(String charRoomid, int page) {
        return mChatMessageDataHelper.getCursorLoader(charRoomid, page);
    }


    @Override
    public void sendData(ChatMessageInfo chatMessageInfo) {
        mChatMessageDataHelper.insert(chatMessageInfo);
        mViewRef.get().onReloadData();
        mChatModel.requestData(this, chatMessageInfo);

    }


    @Override
    public void cleanUnReadNum(String chatRoomId) {
        SharedPreferencesManager.putInt("unReadNumber" + chatRoomId, 0).commit();
        mChatRoomsDataHelper.update(0, chatRoomId);

        updateAllReadNumber(chatRoomId);
    }

    private void updateAllReadNumber(String chatRoomId) {
        int count = SharedPreferencesManager.getInt("unReadNumber" + chatRoomId, 0);
        int allCount = SharedPreferencesManager.getInt("unAllReadNumber" + chatRoomId, 0);
        int nowCount = allCount - count;
        SharedPreferencesManager.putInt("unAllReadNumber", nowCount).commit();

        //设置main气泡
        UnReadNumber unread = new UnReadNumber();
        unread.setUnReadNumber(nowCount);
        EventBus.getDefault().post(unread);
    }

    @Override
    public void updateLasterContent(String chatRoomId) {

    }

    @Override
    public void onRequestSucceed(ChatMessageInfo requestChatMessageInfo, ChatMessageInfo respondChatMessageInfo) {
        mChatMessageDataHelper.updateStatus(App.MESSAGE_STATUS_SUCCESS, requestChatMessageInfo.getMessageID());
        mChatMessageDataHelper.insert(respondChatMessageInfo);
        mViewRef.get().onReloadData();
    }

    @Override
    public void onRequestError(String errorMessage) {
        ToastUtils.showShort("网络异常,请检查网络");
    }



}
