package com.idisfkj.hightcopywx.base.widget;

import android.app.ActionBar;
import android.app.Service;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.idisfkj.hightcopywx.base.presenter.BasePresenter;

/**
 * Created by idisfkj on 16/4/21.
 * Email : idisfkj@qq.com.
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends FragmentActivity {
    protected P mPresenter;//Presenter对象
    protected int page = 1;
    protected AudioManager audio;
    //控制播放器音量
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audio.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audio.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        mPresenter = createPresenter();//创建Presenter
        mPresenter.attachView((V) this);
        audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
    }

    public void initActionBar() {
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
