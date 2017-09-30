package com.idisfkj.hightcopywx.find;

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
import com.idisfkj.hightcopywx.beans.EncourageEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class FindFragment extends Fragment {
    @Bind(R.id.find_recyclerView)
    RecyclerView mFind_recyclerView;

    private List<EncourageEntity> mDataList;

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
     }

    private void initData() {
        mDataList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            EncourageEntity item = new EncourageEntity();
//            item.encouragetitle = "内容" + i;
//            mDataList.add(item);
//            //mDataList.addAll(App.encourageEntityList);
//        }

         mDataList.addAll(App.encourageEntityList);
    }

    private void initAdapter() {
        final BaseQuickAdapter firstAdapter = new FirstAdapter(R.layout.item, mDataList);
        firstAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mDataList.get(position).setEncouragetitle("更新--" + position);
                firstAdapter.setNewData(mDataList);
            }
        });

        mFind_recyclerView.setAdapter(firstAdapter);
    }


}
