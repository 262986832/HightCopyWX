package com.idisfkj.hightcopywx.chat.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapters.ChatRoomsAdapter;
import com.idisfkj.hightcopywx.adapters.OnItemTouchListener;
import com.idisfkj.hightcopywx.chat.WXItemDecoration;
import com.idisfkj.hightcopywx.chat.presenter.ChatRoomsPresent;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatRoomsPresentImp;
import com.idisfkj.hightcopywx.chat.view.ChatRoomsView;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 聊天通讯界面
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsFragment extends Fragment implements ChatRoomsView, LoaderManager.LoaderCallbacks<Cursor> {
    @Bind(R.id.wx_recyclerView)
    RecyclerView wxRecyclerView;
    @Bind(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private ChatRoomsAdapter chatRoomsAdapter;
    private ChatRoomsPresent mChatRoomsPresent;
    private int page = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.chat_rooms, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    public void init() {
        chatRoomsAdapter = new ChatRoomsAdapter(getContext());
        mChatRoomsPresent = new ChatRoomsPresentImp(this, getContext());
        wxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wxRecyclerView.addItemDecoration(new WXItemDecoration(getContext()));
        wxRecyclerView.setAdapter(chatRoomsAdapter);
        wxRecyclerView.addOnItemTouchListener(new OnItemTouchListener(wxRecyclerView) {
            @Override
            public void onItemListener(RecyclerView.ViewHolder vh) {
                ChatRoomsAdapter.ViewHolder wxhd = (ChatRoomsAdapter.ViewHolder) vh;
                int chatType = wxhd.getChatType();
                Intent intent;
                switch (chatType) {
                    case App.CHAT_TYPE_PRACTISE:
                        intent = new Intent(getActivity(), ChatActivityPractise.class);
                        break;
                    case App.CHAT_TYPE_ENGLISH_STUDY:
                        intent = new Intent(getActivity(), ChatActivityStudy.class);
                        break;
                    case App.CHAT_TYPE_CHAT:
                        intent = new Intent(getActivity(), ChatActivity.class);
                        break;
                    case App.CHAT_TYPE_TRANSLATE_ENZH:
                        intent = new Intent(getActivity(), ChatActivityTranslate.class);
                        break;
                    default:
                        intent = new Intent(getActivity(), ChatActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putString("chatRoomID", ((ChatRoomsAdapter.ViewHolder) vh).chatRoomID);
                bundle.putString("chatTitle", ((ChatRoomsAdapter.ViewHolder) vh).chatTitle);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getNewPayge();
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    private void getNewPayge() {
        Bundle bundle = new Bundle();
        bundle.putInt("page", ++page);
        getLoaderManager().restartLoader(0, bundle, this);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            int page = args.getInt("page");
            //ToastUtils.showShort("第" + page+"页");
        }
        return mChatRoomsPresent.creatLoader(SharedPreferencesManager.getString("userPhone"), page);
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
    public void onInitDataEnd(Cursor data) {
        chatRoomsAdapter.changeCursor(data);
    }

    @Override
    public void onInitDataBegin() {

    }
}
