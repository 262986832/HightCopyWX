package com.idisfkj.hightcopywx.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.contact.adapter.ContactAdapter;
import com.idisfkj.hightcopywx.contact.presenter.ContactPresenter;
import com.idisfkj.hightcopywx.contact.presenter.impl.ContactPresenterImpl;
import com.idisfkj.hightcopywx.contact.view.ContactView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.idisfkj.hightcopywx.R.layout.contact_item;


/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class ContactFragment extends Fragment implements ContactView,BaseQuickAdapter.RequestLoadMoreListener{
    @Bind(R.id.contact_recyclerView)
    RecyclerView mRecyclerView;
    private ContactPresenter mContactPresenter;
    private List<WordsEntity> mDataList;
    private ContactAdapter mContactAdapt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.contact_layout, null);
        mContactPresenter=new ContactPresenterImpl();
        initView();
        initData();
        initAdapter();
        return view;
    }
    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData() {
        mDataList = new ArrayList<>();
        mContactPresenter.getWordsData(1);
    }

    private void initAdapter() {
        mContactAdapt = new ContactAdapter(contact_item, mDataList);
        mContactAdapt.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener(){

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ChatMessageInfo chatMessageInfo= (ChatMessageInfo) baseQuickAdapter.getItem(i);
            }
        });
        mContactAdapt.setOnLoadMoreListener(this, mRecyclerView);
        mContactAdapt.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mContactAdapt);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onGetWordsSuccess(List<WordsEntity> wordsEntityList) {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}

