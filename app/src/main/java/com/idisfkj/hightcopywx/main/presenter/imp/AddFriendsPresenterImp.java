package com.idisfkj.hightcopywx.main.presenter.imp;

import android.database.Cursor;
import android.widget.TextView;

import com.idisfkj.hightcopywx.base.presenter.BasePresenter;
import com.idisfkj.hightcopywx.dao.RegisterDataHelper;
import com.idisfkj.hightcopywx.main.presenter.AddFriendsPresenter;
import com.idisfkj.hightcopywx.main.view.AddFriendsView;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public class AddFriendsPresenterImp extends BasePresenter<AddFriendsView> implements AddFriendsPresenter {

    private AddFriendsView mAddFriendsView;

    public AddFriendsPresenterImp(AddFriendsView addFriendsView) {
        mAddFriendsView = addFriendsView;
    }

    @Override
    public void switchView(CharSequence text) {
        if (!"".equals(text.toString())) {
            mAddFriendsView.showSearch();
        } else {
            mAddFriendsView.goneSearch();
        }
        mAddFriendsView.changeText(text);
    }

    @Override
    public void switchActicity(TextView searchContent, RegisterDataHelper helper) {
        String text = searchContent.getText().toString().trim();
        Cursor cursor = helper.query(text);
        if (cursor.getCount() > 0)
            mAddFriendsView.jumpSearchResult(text);
        else
            mAddFriendsView.showToast("不存在该用户！");
        cursor.close();
    }
}
