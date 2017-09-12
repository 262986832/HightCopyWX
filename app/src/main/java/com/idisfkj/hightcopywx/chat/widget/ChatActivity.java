package com.idisfkj.hightcopywx.chat.widget;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapters.ChatAdapter;
import com.idisfkj.hightcopywx.adapters.OnItemTouchListener;
import com.idisfkj.hightcopywx.base.widget.BaseActivity;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterImp;
import com.idisfkj.hightcopywx.chat.view.ChatView;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 聊天界面
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public class ChatActivity extends BaseActivity<ChatView,ChatPresenterImp>
        implements ChatView, View.OnTouchListener,
        View.OnFocusChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    @InjectView(R.id.chat_content)
    EditText mChatContent;
    @InjectView(R.id.chat_view)
    RecyclerView chatView;
    @InjectView(R.id.chat_line)
    View chatLine;
    @InjectView(R.id.voice_swith)
    ImageView voiceSwith;
    @InjectView(R.id.voice_button)
    TextView voice_button;
    @InjectView(R.id.chat_bottm)
    RelativeLayout relativeLayout;
    @InjectView(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private ChatAdapter mChatAdapter;
    private InputMethodManager manager;

    private String chatTitle;
    private String mChatRoomID;
    private int mChat_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        ButterKnife.inject(this);


        Bundle bundle = getIntent().getExtras();
        mChatRoomID = bundle.getString("chatRoomID");
        chatTitle = bundle.getString("chatTitle");
        mChat_type = bundle.getInt("chatType");
        mPresenter.setChatType(mChat_type);


        init();
        getActionBar().setTitle(chatTitle);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        final View root = findViewById(R.id.main);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = root.getRootView().getHeight() - root.getHeight();
                if (heightDiff > 100)
                    chatView.smoothScrollToPosition(mChatAdapter.getItemCount());
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        mChatAdapter = new ChatAdapter(this);
        chatView.setLayoutManager(new LinearLayoutManager(this));
        chatView.setAdapter(mChatAdapter);
        chatView.setOnTouchListener(this);
        chatView.addOnItemTouchListener(new OnItemTouchListener(chatView) {
            @Override
            public void onItemListener(RecyclerView.ViewHolder vh) {
//                ToastUtils.showLong(vh.getLayoutPosition()+"");
            }
        });
        mChatContent.setOnFocusChangeListener(this);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getNewPayge();
            }
        });

        getLoaderManager().initLoader(0, null, this);

        voice_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();// 获得x轴坐标
                int y = (int) event.getY();// 获得y轴坐标
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //mVoicePop.showAtLocation(v, Gravity.CENTER, 0, 0);
                        voice_button.setText("松开结束");
                        //mPopVoiceText.setText("手指上滑，取消发送");
                        voice_button.setTag("1");
                        //mAudioRecoderUtils.startRecord(mActivity);
                        //通知开始识别
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (wantToCancle(x, y)) {
                            voice_button.setText("松开结束");
                            voice_button.setTag("2");
                        } else {
                            voice_button.setText("松开结束");
                            voice_button.setTag("1");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (voice_button.getTag().equals("2")) {
                            //取消录音（删除录音文件）
                            //mAudioRecoderUtils.cancelRecord();
                        } else {
                            //结束录音（保存录音文件）
                            //mAudioRecoderUtils.stopRecord();
                        }
                        voice_button.setText("按住说话");
                        voice_button.setTag("3");
                        break;
                }
                return true;
            }
        });
    }

    private void getNewPayge() {
        Bundle bundle = new Bundle();
        bundle.putInt("page", ++page);
        getLoaderManager().restartLoader(0, bundle, this);
        swipeRefreshLayout.setRefreshing(false);
    }

    private boolean wantToCancle(int x, int y) {
        if (x < 0 || x > voice_button.getWidth()) // 超过按钮的宽度
            return true;
        if (y < -50 || y > voice_button.getHeight() + 50)// 超过按钮的高度
            return true;
        return false;
    }

    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        // 获取根布局的可视区域r
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        // 本来的实际底部距离 - 可视的底部距离
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN && isKeyboardShown(mChatContent.getRootView()))
//            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        chatLine.setBackgroundColor(getResources().getColor(R.color.tab_color_s));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            int page = args.getInt("page");
            //ToastUtils.showShort("第" + page+"页");
        }
        return mPresenter.creatLoader(mChatRoomID,page);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mChatAdapter.changeCursor(data);
        mChatAdapter.setCursor(data);
        chatView.smoothScrollToPosition(mChatAdapter.getItemCount());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mChatAdapter.changeCursor(null);
    }


    @Override
    public void onInitDataEnd(Cursor data) {

    }

    @Override
    public void onInitDataBegin() {

    }

    @OnClick(R.id.chat_send)
    public void onClick() {
        String chatContent = mChatContent.getText().toString();
        if (chatContent.trim().length() > 0) {
            ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
            chatMessageInfo.setSender();
            chatMessageInfo.setMessageContent(chatContent);
            chatMessageInfo.setChatRoomID(mChatRoomID);

            mPresenter.sendData(chatMessageInfo);
            getLoaderManager().restartLoader(0, null, this);
        }
        mChatContent.setText("");
    }

    @OnClick(R.id.voice_swith)
    public void onVoice_swithClick() {
        relativeLayout.setVisibility(relativeLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        voice_button.setVisibility(voice_button.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //更新数据
        mPresenter.cleanUnReadNum(mChatRoomID);
        //重置数据
        VolleyUtils.cancelAll("chatRequest");

        ButterKnife.reset(this);
    }

    @Override
    protected ChatPresenterImp createPresenter() {
        return new ChatPresenterImp();
    }
}
