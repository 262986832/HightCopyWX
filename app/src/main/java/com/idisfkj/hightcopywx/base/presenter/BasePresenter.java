package com.idisfkj.hightcopywx.base.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by fvelement on 2017/9/11.
 */

public abstract class BasePresenter<V> {
    protected Reference<V> mViewRef;//View接口类型弱引用
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view); //建立关联
    }

    protected V getView() {
        return mViewRef.get();//获取View
    }

    public boolean isViewAttached() {//判断是否与View建立了关联
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {//解除关联
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
