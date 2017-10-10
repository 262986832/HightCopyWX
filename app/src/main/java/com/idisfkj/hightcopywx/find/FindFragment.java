package com.idisfkj.hightcopywx.find;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.EncourageEntity;
import com.idisfkj.hightcopywx.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static com.idisfkj.hightcopywx.R.layout.find_item;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class FindFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.find_recyclerView)
    RecyclerView mFind_recyclerView;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<EncourageEntity> mDataList;
    private BaseQuickAdapter findAdapter;
    private static final int TOTAL_COUNTER = 18;
    private int delayMillis = 1000;
    private int mCurrentCounter = 0;
    private boolean isErr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.find_layout, null);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initAdapter();
        return view;
    }

    private void initView() {
        mFind_recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mFind_recyclerView.addItemDecoration(new ItemDecoration(App.getAppContext(), 100));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
    }

    private void initData() {
        mDataList = new ArrayList<>();
        mDataList.addAll(App.encourageEntityList);
    }

    private List<EncourageEntity> getSampleData(int lenth) {
        List<EncourageEntity> encourageEntityArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EncourageEntity encourageEntity = new EncourageEntity();
            encourageEntity.encouragetitle = "title" + i;
            encourageEntity.encouragetext = "text" + i;
            encourageEntityArrayList.add(encourageEntity);
        }
        return encourageEntityArrayList;
    }


    private void initAdapter() {
        findAdapter = new FindAdapter(find_item, mDataList);
        findAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mDataList.get(position).setEncouragetitle("更新--" + position);
                findAdapter.setNewData(mDataList);
            }
        });

        mFind_recyclerView.setAdapter(findAdapter);
    }


    @Override
    public void onRefresh() {
        findAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findAdapter.setNewData(getSampleData(PAGE_SIZE));
                isErr = false;
                mCurrentCounter = PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                findAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (findAdapter.getData().size() < PAGE_SIZE) {
            findAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                findAdapter.loadMoreEnd();//default visible
                //findAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
            } else {
                if (isErr) {
                    findAdapter.addData(getSampleData(PAGE_SIZE));
                    mCurrentCounter = findAdapter.getData().size();
                    findAdapter.loadMoreComplete();
                } else {
                    isErr = true;
                    ToastUtils.showShort("加载数据错误");
                    findAdapter.loadMoreFail();

                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }
}
