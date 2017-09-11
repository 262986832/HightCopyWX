package com.idisfkj.hightcopywx.main.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.adapters.SearchResultAdapter;
import com.idisfkj.hightcopywx.base.presenter.BasePresenter;
import com.idisfkj.hightcopywx.beans.ChatRoomItemInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.main.model.SearchResultModel;
import com.idisfkj.hightcopywx.main.model.SearchResultModelImp;
import com.idisfkj.hightcopywx.main.view.SearchResultView;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.VolleyUtils;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public class SearchResultPresenterImp extends BasePresenter<SearchResultView> implements SearchResultPresenter, SearchResultModelImp.requestListener {
    private SearchResultModel mModel;
    private SearchResultView mView;
    private ChatRoomsDataHelper wxHelper;
    private ChatMessageDataHelper chatHelper;

    public SearchResultPresenterImp(SearchResultView View) {
        this.mView = View;
        mModel = new SearchResultModelImp();
    }

    @Override
    public void checkSelection(Context context, String number, View view, SearchResultAdapter adapter) {
        mModel.buildDialog(context,number,view,adapter,this);
    }

    @Override
    public void onSucceed(String userName, String number, String regId, Cursor cursor, ProgressDialog pd) {
        wxHelper = new ChatRoomsDataHelper(App.getAppContext());
        chatHelper = new ChatMessageDataHelper(App.getAppContext());
        ChatRoomItemInfo info = new ChatRoomItemInfo();
        String currentAccount = SharedPreferencesManager.getString("userPhone");

        //添加到聊天通信数据库
        wxHelper.insert(info);
        cursor.close();

        //添加系统消息
//        ChatMessageInfo chatInfo = new ChatMessageInfo(String.format(App.HELLO_MESSAGE,userName),2
//                ,CalendarUtils.getCurrentDate(),currentAccount,regId,number);
//        chatHelper.insert(chatInfo);

        VolleyUtils.cancelAll("addRequest");
        mView.succeedToFinish();
        mView.hideProgressDialog(pd);
        mView.showSucceedToast();
    }

    @Override
    public void onError(ProgressDialog pd) {
        pd.cancel();
        mView.showErrorToast();
    }
}
