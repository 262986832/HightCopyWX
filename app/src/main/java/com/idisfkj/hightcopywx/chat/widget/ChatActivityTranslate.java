package com.idisfkj.hightcopywx.chat.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.base.widget.BaseActivity;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.adapter.TranslateAdapter;
import com.idisfkj.hightcopywx.chat.presenter.imp.ChatPresenterTranslateImpl;
import com.idisfkj.hightcopywx.chat.view.ChatTranslateView;
import com.idisfkj.hightcopywx.chat.view.ISpeechView;
import com.idisfkj.hightcopywx.util.SpeechRecognizerService;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fvelement on 2017/10/13.
 */

public class ChatActivityTranslate extends BaseActivity<ChatTranslateView, ChatPresenterTranslateImpl>
        implements View.OnFocusChangeListener, ISpeechView, ChatTranslateView {
    @Bind(R.id.chat_content)
    EditText mChatContent;
    @Bind(R.id.chat_view)
    RecyclerView chatView;
    @Bind(R.id.chat_line)
    View chatLine;
    @Bind(R.id.voice_swith)
    ImageView voiceSwith;
    @Bind(R.id.voice_button)
    TextView voice_button;
    @Bind(R.id.group_text_send)
    RelativeLayout group_text_send;
    @Bind(R.id.layout_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.chat_content2)
    EditText mChatContent2;
    @Bind(R.id.chat_line2)
    View chatLine2;
    @Bind(R.id.voice_swith2)
    ImageView voiceSwith2;
    @Bind(R.id.voice_button2)
    TextView voice_button2;
    @Bind(R.id.group_text_send2)
    RelativeLayout group_text_send2;


    private TranslateAdapter mChatAdapter;
    private String chatTitle;
    protected String mChatRoomID;
    private List<ChatMessageInfo> chatMessageInfoList;
    //语音识别服务
    protected SpeechRecognizerService speechRecognizerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_layout);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        mChatRoomID = bundle.getString("chatRoomID");
        chatTitle = bundle.getString("chatTitle");
        init();
        initSpeech();
    }

    //初始化语音识别与语音合成
    private void initSpeech() {
        speechRecognizerService = new SpeechRecognizerService(this);
        speechRecognizerService.attachView(this);
    }

    private List<ChatMessageInfo> getData() {
        ChatMessageInfo chatMessageInfo=new ChatMessageInfo();
        chatMessageInfo.setSendOrReciveFlag(App.SEND_FLAG);
        chatMessageInfo.setMessageContent("hello hello ");
        chatMessageInfoList.add(chatMessageInfo);

        ChatMessageInfo chatMessageInfo1=new ChatMessageInfo();
        chatMessageInfo1.setSendOrReciveFlag(App.RECEIVE_FLAG);
        chatMessageInfo1.setMessageContent("how are you");
        chatMessageInfoList.add(chatMessageInfo1);
        return chatMessageInfoList;
    }

    public void init() {
        chatMessageInfoList = new ArrayList<ChatMessageInfo>();
        mChatAdapter = new TranslateAdapter(this, getData());
        mChatAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ChatMessageInfo chatMessageInfo= (ChatMessageInfo) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.chat_send_content:
                        ToastUtils.showShort(chatMessageInfo.getMessageContent());
                        break;
                }
            }
        });
        chatView.setLayoutManager(new LinearLayoutManager(this));
        chatView.getBaseline();
        chatView.setAdapter(mChatAdapter);

        mChatContent.setOnFocusChangeListener(this);
        getActionBar().setTitle(chatTitle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.chat_send)
    public void onClick() {
        sendMessage(UrlUtils.ENTOZH,mChatContent.getText().toString());
        mChatContent.setText("");
    }
    //中文
    @OnClick(R.id.chat_send2)
    public void onClick2() {
        sendMessage(UrlUtils.ZHTOEN,mChatContent2.getText().toString());
        mChatContent2.setText("");
    }

    @OnClick(R.id.voice_button)
    public void onVoiceClick() {
        speechRecognizerService.setParam("en_us");
        speechRecognizerService.startSpeechRecognizer();
    }
    @OnClick(R.id.voice_button2)
    public void onVoiceClick2() {
        speechRecognizerService.setParam("zh_cn");
        speechRecognizerService.startSpeechRecognizer();
    }

    private void sendMessage( int type,String message) {
        if (message.trim().length() > 0) {
            //生成一个token
            String voiceName = UUID.randomUUID().toString();
            ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
            chatMessageInfo.setSender();
            chatMessageInfo.setMessageContent(message);
            chatMessageInfo.setMessageType( App.MESSAGE_TYPE_TEXT);
            chatMessageInfo.setChatRoomID(mChatRoomID);
            chatMessageInfo.setMessageVoiceUrl(voiceName);
            chatMessageInfoList.add(chatMessageInfo);
            mChatAdapter.setNewData(chatMessageInfoList);
            mPresenter.translate(type,chatMessageInfo);
        }
    }

    @OnClick(R.id.voice_swith)
    public void onVoice_swithClick() {
        group_text_send.setVisibility(group_text_send.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        voice_button.setVisibility(voice_button.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    @OnClick(R.id.voice_swith2)
    public void onVoice_swithClick2() {
        group_text_send2.setVisibility(group_text_send2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        voice_button2.setVisibility(voice_button2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    @Override
    protected ChatPresenterTranslateImpl createPresenter() {
        return new ChatPresenterTranslateImpl();
    }

    @Override
    public void onSpeechRecognizerComplete(String string) {

    }

    @Override
    public void onSpeechRecognizerError(String string) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        chatLine.setBackgroundColor(getResources().getColor(R.color.tab_color_s));
    }

    @Override
    public void onTranslateComplete(ChatMessageInfo requestChatMessageInfo, ChatMessageInfo respondChatMessageInfo) {
        int indexOf=chatMessageInfoList.indexOf(requestChatMessageInfo);
        chatMessageInfoList.get(indexOf).setStatus(App.MESSAGE_STATUS_SUCCESS);
        chatMessageInfoList.add(respondChatMessageInfo);
        mChatAdapter.setNewData(chatMessageInfoList);
    }
}
