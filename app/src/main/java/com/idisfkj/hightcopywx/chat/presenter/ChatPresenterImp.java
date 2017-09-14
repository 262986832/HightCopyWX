package com.idisfkj.hightcopywx.chat.presenter;

import android.content.CursorLoader;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.base.presenter.BasePresenter;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.UnReadNumber;
import com.idisfkj.hightcopywx.chat.model.ChatModel;
import com.idisfkj.hightcopywx.chat.model.ChatModelImp;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudyImp;
import com.idisfkj.hightcopywx.chat.model.ChatModelTranslateImp;
import com.idisfkj.hightcopywx.chat.view.ChatView;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class ChatPresenterImp extends BasePresenter<ChatView> implements ChatPresenter,
        ChatModel.requestListener {
    private ChatModel mChatModel;
    private ChatRoomsDataHelper mChatRoomsDataHelper;
    private int page;
    private int limit;

    public ChatPresenterImp() {
        mChatRoomsDataHelper = new ChatRoomsDataHelper(App.getAppContext());
    }

    public void setChatType(int chatType){
        if (chatType == App.CHAT_TYPE_CHINESETOENGLISH)
            mChatModel = new ChatModelTranslateImp(UrlUtils.ZHTOEN);
        else if (chatType == App.CHAT_TYPE_ENGLISHTOCHINESE)
            mChatModel = new ChatModelTranslateImp(UrlUtils.ENTOGH);
        else if (chatType == App.CHAT_TYPE_ENGLISH_STUDY){
            mChatModel = new ChatModelStudyImp();
            ChatModelStudyImp chatModelStudyImp =(ChatModelStudyImp) mChatModel;
            chatModelStudyImp.initStudyData();
        }
        else
            mChatModel = new ChatModelImp();
    }


    @Override
    public CursorLoader creatLoader(String charRoomid,int page) {
        return mChatModel.initData(charRoomid,page);
    }


    @Override
    public void sendData(ChatMessageInfo chatMessageInfo) {
        mChatModel.insertData(chatMessageInfo);
        mViewRef.get().onReloadData();
        mChatModel.requestData(this, chatMessageInfo);

    }


    @Override
    public void cleanUnReadNum(String chatRoomId) {
        SharedPreferencesManager.putInt("unReadNumber" + chatRoomId, 0).commit();
        mChatRoomsDataHelper.update(0, chatRoomId);

        updateAllReadNumber(chatRoomId);
    }
    private void updateAllReadNumber(String chatRoomId){
        int count = SharedPreferencesManager.getInt("unReadNumber" + chatRoomId, 0);
        int allCount = SharedPreferencesManager.getInt("unAllReadNumber" + chatRoomId, 0);
        int nowCount=allCount-count;
        SharedPreferencesManager.putInt("unAllReadNumber" , nowCount).commit();

        //设置main气泡
        UnReadNumber unread=new UnReadNumber();
        unread.setUnReadNumber(nowCount);
        EventBus.getDefault().post(unread);
    }

    @Override
    public void updateLasterContent(String chatRoomId) {

    }


    @Override
    public void onSucceed(ChatMessageInfo chatMessageInfo) {
        mChatModel.insertData(chatMessageInfo);
        mViewRef.get().onReloadData();
    }

    @Override
    public void onError(String errorMessage) {
        ToastUtils.showShort("网络异常,请检查网络");
    }


}
