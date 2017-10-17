package com.idisfkj.hightcopywx.find.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.find.ItemDecoration;
import com.idisfkj.hightcopywx.find.adapter.FindAdapter;
import com.idisfkj.hightcopywx.find.model.EncourageEntity;
import com.idisfkj.hightcopywx.find.presenter.EncouragePresenter;
import com.idisfkj.hightcopywx.find.presenter.imp.EncouragePresenterImp;
import com.idisfkj.hightcopywx.find.view.EncourageView;
import com.idisfkj.hightcopywx.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.idisfkj.hightcopywx.R.layout.find_item;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class FindFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener,
        EncourageView {
    @Bind(R.id.find_recyclerView)
    RecyclerView mFind_recyclerView;

    private List<EncourageEntity> mDataList;
    private FindAdapter findAdapter;
    private EncouragePresenter mEncouragePresenter;

    private int page = 1;
    private boolean isEnd = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.find_layout, null);
        ButterKnife.bind(this, view);
        mEncouragePresenter = new EncouragePresenterImp(this);
        initView();
        initData();
        initAdapter();
        return view;
    }

    private void initView() {
//        mFind_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFind_recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mFind_recyclerView.addItemDecoration(new ItemDecoration(App.getAppContext(), 100));

    }

    private void initData() {
        mDataList = new ArrayList<>();
        mEncouragePresenter.getEncourageData(page);
    }

    private void initAdapter() {
        findAdapter = new FindAdapter(find_item, mDataList);
        findAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                mDataList.get(position).setEncouragetitle("更新--" + position);
//                findAdapter.setNewData(mDataList);
            }
        });
        findAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener(){

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ChatMessageInfo chatMessageInfo= (ChatMessageInfo) baseQuickAdapter.getItem(i);
            }
        });
        findAdapter.setOnLoadMoreListener(this, mFind_recyclerView);
        findAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mFind_recyclerView.setAdapter(findAdapter);
    }


    @Override
    public void onLoadMoreRequested() {
        if (isEnd) {
            findAdapter.loadMoreEnd(false);
        } else {
            mEncouragePresenter.getEncourageData(page);
        }
    }

    @Override
    public void onGetEncourageSuccess(List<EncourageEntity> encourageEntityList) {
        if(encourageEntityList!=null && encourageEntityList.size()==0){
            isEnd=true;
            findAdapter.loadMoreEnd(false);
            ToastUtils.showShort("没有更多数据");
            return;
        }
        findAdapter.addData(encourageEntityList);
        findAdapter.loadMoreComplete();
        page++;
    }
}
