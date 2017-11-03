package com.idisfkj.hightcopywx.chat.widget;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.base.widget.BaseActivityNew;
import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.chat.adapter.PractiseAdapter;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterPractise;
import com.idisfkj.hightcopywx.chat.view.ChatViewPractise;
import com.idisfkj.hightcopywx.injection.components.DaggerPractiseComponent;
import com.idisfkj.hightcopywx.injection.modules.PractiseModule;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.idisfkj.hightcopywx.R.layout.practise_item;

/**
 * Created by fvelement on 2017/10/30.
 */

public class ChatActivityPractise extends BaseActivityNew
        implements ChatViewPractise,BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.practise_view)
    RecyclerView mRecyclerView;
    String mChatRoomID;
    private List<WordsEntity> mDataList;
    private PractiseAdapter mPractiseAdapter;
    @Inject
    public ChatPresenterPractise mChatPresenterPractise;

    private int page = 1;
    private boolean isEnd = false;
    private PLMediaPlayer mMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practise_layout);
        ButterKnife.bind(this);
        DaggerPractiseComponent
                .builder()
                .practiseModule(new PractiseModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);

        Bundle bundle = getIntent().getExtras();
        mChatRoomID = bundle.getString("chatRoomID");
        String chatTitle = bundle.getString("chatTitle");
        getActionBar().setTitle(chatTitle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mMediaPlayer = new PLMediaPlayer(App.getAppContext());
        initView();
        initData();
        initAdapter();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.practis_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.img_fold_item:
                item.setTitle(item.getTitle().equals("图片展开")?"图片折叠":"图片展开");
                setImgFold();
                break;
            case R.id.english_fold_item:
                item.setTitle(item.getTitle().equals("单词展开")?"单词折叠":"单词展开");
                setEnglishFold();
                break;
            case R.id.select_item:
                //todo 删除
                break;
            case R.id.clean_item:
                //todo 删除
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setImgFold(){
        mPractiseAdapter.setFolded(mPractiseAdapter.isFolded()==false?true:false);
        mPractiseAdapter.notifyDataSetChanged();
    }
    private void setEnglishFold(){
        mPractiseAdapter.setEnglish_folded(mPractiseAdapter.isEnglish_folded()==false?true:false);
        mPractiseAdapter.notifyDataSetChanged();
    }
    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(App.getAppContext()));

    }

    private void initData() {
        mDataList = new ArrayList<>();
        mChatPresenterPractise.getWordsData(page);
    }

    private void initAdapter() {
        mPractiseAdapter = new PractiseAdapter(practise_item, mDataList);
        mPractiseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WordsEntity wordsEntity = (WordsEntity) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.practise_picture:
                        adapter.getViewByPosition(position,R.id.practise_card_imgurl)
                                .setVisibility( adapter.getViewByPosition(position,R.id.practise_card_imgurl).getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                        break;
                    case R.id.practise_card_imgurl:
                        playWord(wordsEntity.getEnglish());
                        break;
                    case R.id.practise_card_title:
                        playWord(wordsEntity.getEnglish());
                        break;
                }

            }
        });
        mPractiseAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mPractiseAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mPractiseAdapter);
    }
    private void playWord(String word){
        try {
            mMediaPlayer.setDataSource(App.BOOK_VOICE_URL+word.replaceAll(" ", "") +".mp3");
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

    }
    @Override
    public void onGetWordsSuccess(List<WordsEntity> wordsEntityList) {
        if(wordsEntityList!=null && wordsEntityList.size()==0){
            isEnd=true;
            mPractiseAdapter.loadMoreEnd(false);
            ToastUtils.showShort("没有更多数据");
            return;
        }
        mPractiseAdapter.addData(wordsEntityList);
        mPractiseAdapter.loadMoreComplete();
        page++;
    }

    @Override
    public void onLoadMoreRequested() {
        if (isEnd) {
            mPractiseAdapter.loadMoreEnd(false);
        } else {
            mChatPresenterPractise.getWordsData(page);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        ButterKnife.unbind(this);
    }
}
