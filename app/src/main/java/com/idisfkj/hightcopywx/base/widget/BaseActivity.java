package com.idisfkj.hightcopywx.base.widget;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.idisfkj.hightcopywx.base.presenter.BasePresenter;

/**
 * Created by idisfkj on 16/4/21.
 * Email : idisfkj@qq.com.
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>>  extends FragmentActivity{
    protected P mPresenter;//Presenter对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        mPresenter = createPresenter();//创建Presenter
        mPresenter.attachView((V) this);
    }

    public void initActionBar(){
        ActionBar mActionBar = getActionBar();
        //取消回退图标
        mActionBar.setDisplayHomeAsUpEnabled(false);
        //取消icon图标
        mActionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract P createPresenter();
}
