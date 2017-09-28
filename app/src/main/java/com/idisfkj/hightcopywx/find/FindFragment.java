package com.idisfkj.hightcopywx.find;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapters.FindAdapter;
import com.idisfkj.hightcopywx.chat.presenter.ChatRoomsPresent;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatRoomsPresentImp;
import com.idisfkj.hightcopywx.chat.view.ChatRoomsView;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class FindFragment extends Fragment implements ChatRoomsView,LoaderManager.LoaderCallbacks<Cursor> {
    @Bind(R.id.find_recyclerView)
    RecyclerView mFind_recyclerView;
    @Bind(R.id.layout_swipe_refresh)
    SwipeRefreshLayout mLayout_swipe_refresh;

    private FindAdapter mFindAdapter;
    private ChatRoomsPresent mChatRoomsPresent;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.find_layout, null);
        ButterKnife.bind(this, view);
        init();
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    public void init() {
        mFindAdapter = new FindAdapter(getContext());
        mChatRoomsPresent = new ChatRoomsPresentImp(this, getContext());
        mFind_recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mFind_recyclerView.addItemDecoration(new ItemDecoration(App.getAppContext(),100));
        mFind_recyclerView.setAdapter(mFindAdapter);
        //下拉刷新
        mLayout_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                //getNewPayge();
                mLayout_swipe_refresh.setRefreshing(false);
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mChatRoomsPresent.creatLoader(SharedPreferencesManager.getString("userPhone"), page);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFindAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFindAdapter.changeCursor(null);
    }

    @Override
    public void onInitDataEnd(Cursor data) {

    }

    @Override
    public void onInitDataBegin() {

    }
}
