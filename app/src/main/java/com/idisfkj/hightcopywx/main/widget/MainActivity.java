package com.idisfkj.hightcopywx.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapters.FragmentAdapter;
import com.idisfkj.hightcopywx.base.widget.BaseActivity;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.UnReadNumber;
import com.idisfkj.hightcopywx.chat.widget.ChatActivity;
import com.idisfkj.hightcopywx.main.presenter.MainPresenterImp;
import com.idisfkj.hightcopywx.main.view.MainView;
import com.readystatesoftware.viewbadger.BadgeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainView,MainPresenterImp> implements MainView {

    @InjectView(R.id.viewPage)
    ViewPager viewPage;
    @InjectView(R.id.wei_xin_s)
    ImageView weiXinS;
    @InjectView(R.id.address_book_s)
    ImageView addressBookS;
    @InjectView(R.id.find_s)
    ImageView findS;
    @InjectView(R.id.me_s)
    ImageView meS;
    @InjectView(R.id.tab_weiXin_s)
    TextView tabWeiXinS;
    @InjectView(R.id.tab_address_s)
    TextView tabAddressS;
    @InjectView(R.id.tab_find_s)
    TextView tabFindS;
    @InjectView(R.id.tab_me_s)
    TextView tabMeS;

    private List<ImageView> mListImage = new ArrayList<>();
    private List<TextView> mListText = new ArrayList<>();
    private int[] viewId;
    private Bundle bundle;
    private BadgeView badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);//订阅总线消息

        init();

        //是否是点击通知跳转
        bundle = getIntent().getExtras();
        if (bundle != null) {
            mPresenter.switchActivity();
        }

    }

    //收到小米推送的消息
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void handleMessage(ChatMessageInfo chatMessageInfo) {
        mPresenter.receiveData(chatMessageInfo);
    }

    //收到main气泡更新消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showBadge(UnReadNumber unReadNumber) {
        int count = unReadNumber.getUnReadNumber();
        if (count > 0) {
            badgeView.setText("" + count);
            badgeView.show();
        } else {
            badgeView.hide();
        }
    }

    public void init() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPage.setAdapter(adapter);
        weiXinS.setAlpha(1.0f);
        tabWeiXinS.setAlpha(1.0f);
        setViewPageListener();

        viewId = new int[]{R.id.ll_wx, R.id.ll_address, R.id.ll_find, R.id.ll_me};
        mListImage.add(weiXinS);
        mListImage.add(addressBookS);
        mListImage.add(findS);
        mListImage.add(meS);
        mListText.add(tabWeiXinS);
        mListText.add(tabAddressS);
        mListText.add(tabFindS);
        mListText.add(tabMeS);

        badgeView = new BadgeView(this, weiXinS);
        badgeView.setText("12");
        badgeView.hide();

    }

    public void setViewPageListener() {
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    mListImage.get(position).setAlpha(1 - positionOffset);
                    mListImage.get(position + 1).setAlpha(positionOffset);
                    mListText.get(position).setAlpha(1 - positionOffset);
                    mListText.get(position + 1).setAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchWX() {
        viewPage.setCurrentItem(0, false);
        weiXinS.setAlpha(1.0f);
        tabWeiXinS.setAlpha(1.0f);
    }

    @Override
    public void switchAddressBook() {
        viewPage.setCurrentItem(1, false);
        addressBookS.setAlpha(1.0f);
        tabAddressS.setAlpha(1.0f);
    }

    @Override
    public void switchFind() {
        viewPage.setCurrentItem(2, false);
        findS.setAlpha(1.0f);
        tabFindS.setAlpha(1.0f);
    }

    @Override
    public void switchMe() {
        viewPage.setCurrentItem(3, false);
        meS.setAlpha(1.0f);
        tabMeS.setAlpha(1.0f);
    }

    @Override
    public void switchAlpha(int id) {
        for (int i = 0; i < viewId.length; i++) {
            if (id != viewId[i]) {
                mListImage.get(i).setAlpha(0f);
                mListText.get(i).setAlpha(0f);
            }
        }
    }

    @Override
    public void jumpChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @OnClick({R.id.ll_wx, R.id.ll_address, R.id.ll_find, R.id.ll_me})
    public void onClick(View view) {
        mPresenter.switchNavigation(view.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
        ButterKnife.reset(this);
    }

    @Override
    protected MainPresenterImp createPresenter() {
        return new MainPresenterImp();
    }

}
