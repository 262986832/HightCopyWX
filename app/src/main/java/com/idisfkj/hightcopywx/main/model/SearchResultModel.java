package com.idisfkj.hightcopywx.main.model;

import android.content.Context;
import android.view.View;

import com.idisfkj.hightcopywx.adapters.SearchResultAdapter;
import com.idisfkj.hightcopywx.main.model.imp.SearchResultModelImp;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public interface SearchResultModel {
    void buildDialog(Context context, String number, View view, SearchResultAdapter adapter,SearchResultModelImp.requestListener listener);
}
