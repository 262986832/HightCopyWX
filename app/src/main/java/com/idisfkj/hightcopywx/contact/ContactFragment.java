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
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.contact.adapter.ContactAdapter;
import com.idisfkj.hightcopywx.contact.presenter.ContactPresenter;
import com.idisfkj.hightcopywx.contact.view.ContactView;
import com.idisfkj.hightcopywx.injection.components.DaggerContactComponent;
import com.idisfkj.hightcopywx.injection.modules.ContactModules;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.idisfkj.hightcopywx.R.layout.contact_item;


/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class ContactFragment extends Fragment implements ContactView,BaseQuickAdapter.RequestLoadMoreListener{
    @Bind(R.id.contact_recyclerView)
    RecyclerView mRecyclerView;
    @Inject
    public ContactPresenter mContactPresenter;
    private List<WordsEntity> mDataList;
    private ContactAdapter mContactAdapt;

    private int page = 1;
    private boolean isEnd = false;
    private PLMediaPlayer mMediaPlayer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.contact_layout, null);
        ButterKnife.bind(this, view);
        DaggerContactComponent
                .builder()
                .contactModules(new ContactModules(this))
                .appComponent(App.getInstance().getAppComponent())
                .build().inject(this);
        mMediaPlayer = new PLMediaPlayer(App.getAppContext());
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
        mContactPresenter.getWordsData(page);
    }

    private void initAdapter() {
        mContactAdapt = new ContactAdapter(contact_item, mDataList);
        mContactAdapt.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WordsEntity wordsEntity = (WordsEntity) adapter.getItem(position);
                try {
                    mMediaPlayer.setDataSource("http://oxnbp01a8.bkt.clouddn.com/"+wordsEntity.getEnglish()+".mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMediaPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(PLMediaPlayer plMediaPlayer, int i) {
                        mMediaPlayer.start();
                    }
                });
                mMediaPlayer.prepareAsync();



                switch (view.getId()) {
                    case R.id.contact_item_title:
                        ToastUtils.showShort(wordsEntity.getEnglish());
                        break;
                    case R.id.contact_item_content:
                        ToastUtils.showShort(wordsEntity.getChinese());
                        break;
                }
            }
        });
        mContactAdapt.setOnLoadMoreListener(this, mRecyclerView);
        mContactAdapt.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mContactAdapt);
    }

    @Override
    public void onDestroyView() {
        mMediaPlayer.release();
        super.onDestroyView();
    }

    @Override
    public void onGetWordsSuccess(List<WordsEntity> wordsEntityList) {
        if(wordsEntityList!=null && wordsEntityList.size()==0){
            isEnd=true;
            mContactAdapt.loadMoreEnd(false);
            ToastUtils.showShort("没有更多数据");
            return;
        }
        mContactAdapt.addData(wordsEntityList);
        mContactAdapt.loadMoreComplete();
        page++;
    }

    @Override
    public void onLoadMoreRequested() {
        if (isEnd) {
            mContactAdapt.loadMoreEnd(false);
        } else {
            mContactPresenter.getWordsData(page);
        }
    }


}

