package com.idisfkj.hightcopywx.wx.widget;

import android.app.LoaderManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
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

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapter.ChatAdapter;
import com.idisfkj.hightcopywx.adapter.OnItemTouchListener;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.ui.BaseActivity;
import com.idisfkj.hightcopywx.util.VolleyUtils;
import com.idisfkj.hightcopywx.wx.presenter.ChatPresenter;
import com.idisfkj.hightcopywx.wx.presenter.ChatPresenterImp;
import com.idisfkj.hightcopywx.wx.view.ChatView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 聊天界面
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public class ChatActivity extends BaseActivity implements ChatView, View.OnTouchListener, View.OnFocusChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    @InjectView(R.id.chat_content)
    EditText chatContent;
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

    private static final String ACTION_FILTER = "com.idisfkj.hightcopywx.chat";
    private ChatPresenter mChatPresenter;
    private String mChatContent;
    private ChatAdapter mChatAdapter;
    private BroadcastReceiver receiver;
    private InputMethodManager manager;
    private ChatMessageDataHelper chatHelper;
    private String userName;
    private int unReadNum;
    private int _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        ButterKnife.inject(this);
        Bundle bundle = getIntent().getExtras();
        _id = bundle.getInt("_id");
        int chat_type=bundle.getInt("chatType");

        mChatPresenter = new ChatPresenterImp(this,chat_type);
        mChatPresenter.loadData(this, _id);

        NotificationManager manager = (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(_id);

        init();

        if (unReadNum > 0) {
            mChatPresenter.cleanUnReadNum(this, App.mRegId, App.mNumber, unReadNum);
        }
        getActionBar().setTitle(userName);
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

//        @SuppressLint("WrongViewCast") RelativeLayoutThatDetectsSoftKeyboard chatLayout = (RelativeLayoutThatDetectsSoftKeyboard) findViewById(R.id.chat_bottm);
//        chatLayout.setListener(this);
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
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        receiver = new ChatBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_FILTER);
        this.registerReceiver(receiver, filter);

        chatHelper = new ChatMessageDataHelper(this);
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
        chatContent.setOnFocusChangeListener(this);

        getLoaderManager().initLoader(0, null, this);

        voice_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获得x轴坐标
                int x = (int) event.getX();
                // 获得y轴坐标
                int y = (int) event.getY();

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
                        // mVoiceText.setVisibility(View.GONE);
                        // mEditText.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });
    }
    private boolean wantToCancle(int x, int y) {
        // 超过按钮的宽度
        if (x < 0 || x > voice_button.getWidth()) {
            return true;
        }
        // 超过按钮的高度
        if (y < -50 || y > voice_button.getHeight() + 50) {
            return true;
        }
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
        if (event.getAction() == MotionEvent.ACTION_DOWN && isKeyboardShown(chatContent.getRootView()))
            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        chatLine.setBackgroundColor(getResources().getColor(R.color.tab_color_s));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return chatHelper.getCursorLoader(App.mNumber, App.mRegId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader != null && data.getCount() <= 0) {
            mChatPresenter.initData(chatHelper, App.mRegId, App.mNumber, userName);
        }
        mChatAdapter.changeCursor(data);
        mChatAdapter.setCursor(data);
        chatView.smoothScrollToPosition(mChatAdapter.getItemCount());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mChatAdapter.changeCursor(null);
    }

    @Override
    public void loadUserInfo(String regId, String number, String userName, int unReadNum) {
        App.mRegId = regId;
        App.mNumber = number;
        this.userName = userName;
        this.unReadNum = unReadNum;
    }

//    @Override
//    public void onSoftKeyboardShown(boolean isShowing) {
//        Log.d("TAG", "Listener");
//        if (isShowing)
//            chatView.smoothScrollToPosition(mChatAdapter.getItemCount());
//    }

    private class ChatBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mChatPresenter.receiveData(intent, chatHelper);
        }
    }

    @OnClick(R.id.chat_send)
    public void onClick() {
        mChatContent = chatContent.getText().toString();
        if (mChatContent.trim().length() > 0) {
            mChatPresenter.sendData(mChatContent, App.mNumber, App.mRegId, chatHelper);
        }
        chatContent.setText("");
    }

    @OnClick(R.id.voice_swith)
    public void onVoice_swithClick() {
        relativeLayout.setVisibility(relativeLayout.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
        voice_button.setVisibility(voice_button.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //更新数据
        mChatPresenter.updateLasterContent(this, App.mRegId, App.mNumber);
        //重置数据
        App.mNumber = "-1";
        App.mRegId = "-1";
        VolleyUtils.cancelAll("chatRequest");
        this.unregisterReceiver(receiver);
        ButterKnife.reset(this);
    }
}
