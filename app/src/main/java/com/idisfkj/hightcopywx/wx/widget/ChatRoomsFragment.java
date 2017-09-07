package com.idisfkj.hightcopywx.wx.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapter.ChatRoomsAdapter;
import com.idisfkj.hightcopywx.adapter.OnItemTouchListener;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.wx.WXItemDecoration;
import com.idisfkj.hightcopywx.wx.presenter.ChatRoomsPresent;
import com.idisfkj.hightcopywx.wx.presenter.ChatRoomsPresentImp;
import com.idisfkj.hightcopywx.wx.view.ChatRoomsView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 聊天通讯界面
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsFragment extends Fragment implements ChatRoomsView, LoaderManager.LoaderCallbacks<Cursor> {
    @InjectView(R.id.wx_recyclerView)
    RecyclerView wxRecyclerView;
    private ChatRoomsAdapter chatRoomsAdapter;
    private ChatRoomsPresent mChatRoomsPresent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.wx_layout, null);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    public void init() {
        chatRoomsAdapter = new ChatRoomsAdapter(getContext());
        mChatRoomsPresent = new ChatRoomsPresentImp(this,getContext());
        wxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wxRecyclerView.addItemDecoration(new WXItemDecoration(getContext()));
        wxRecyclerView.setAdapter(chatRoomsAdapter);
        wxRecyclerView.addOnItemTouchListener(new OnItemTouchListener(wxRecyclerView) {
            @Override
            public void onItemListener(RecyclerView.ViewHolder vh) {
                ChatRoomsAdapter.ViewHolder wxhd=(ChatRoomsAdapter.ViewHolder)vh;
                int chatType=wxhd.getChatType();
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chatRoomID", ((ChatRoomsAdapter.ViewHolder) vh).chatRoomID);
                bundle.putString("chatTitle", ((ChatRoomsAdapter.ViewHolder) vh).chatTitle);
                bundle.putInt("chatType", chatType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mChatRoomsPresent.creatLoader(SharedPreferencesManager.getString("userPhone"));
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader != null && data.getCount() <= 0) {
            mChatRoomsPresent.initData();
        }
        chatRoomsAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        chatRoomsAdapter.changeCursor(null);
    }

    @Override
    public void onInitDataComplete( Cursor data) {
        chatRoomsAdapter.changeCursor(data);
        ToastUtils.showShort("初始化课程信息完成");
    }

    @Override
    public void onInitDataing() {
        ToastUtils.showShort("正在初始化课程信息...");
    }
}
