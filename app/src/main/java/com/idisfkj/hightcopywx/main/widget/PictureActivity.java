package com.idisfkj.hightcopywx.main.widget;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.base.fragment.BaseFragment;
import com.idisfkj.hightcopywx.base.widget.BaseActivity;
import com.idisfkj.hightcopywx.main.presenter.imp.MainPresenterImp;
import com.idisfkj.hightcopywx.main.view.MainView;

import butterknife.ButterKnife;

/**
 * Created by fvelement on 2017/9/25.
 */

public class PictureActivity extends BaseActivity<MainView,MainPresenterImp> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_layout);
        ButterKnife.bind(this);
        initMainFragment();
    }

    @Override
    protected MainPresenterImp createPresenter() {
        return new MainPresenterImp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment mFragment = MainFragment.newInstance();
        transaction.replace(R.id.main_act_container, mFragment, mFragment.getFragmentName());
        transaction.commit();
        getActionBar().setTitle("设置头像");
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
