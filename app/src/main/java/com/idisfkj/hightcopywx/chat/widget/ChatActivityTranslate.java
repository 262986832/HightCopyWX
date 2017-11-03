package com.idisfkj.hightcopywx.chat.widget;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.idisfkj.hightcopywx.base.widget.BaseActivityNew;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.adapter.TranslateAdapter;
import com.idisfkj.hightcopywx.chat.presenter.ChatPresenterTranslate;
import com.idisfkj.hightcopywx.chat.view.ChatTranslateView;
import com.idisfkj.hightcopywx.chat.view.ISpeechView;
import com.idisfkj.hightcopywx.injection.components.DaggerChatActivityTranslateComponent;
import com.idisfkj.hightcopywx.injection.modules.ChatActivityTranslateModule;
import com.idisfkj.hightcopywx.util.SpeechRecognizerService;
import com.idisfkj.hightcopywx.util.SpeechSynthesizerService;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.UrlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by fvelement on 2017/10/13.
 */

public class ChatActivityTranslate extends BaseActivityNew
        implements View.OnFocusChangeListener, ISpeechView, ChatTranslateView , EasyPermissions.PermissionCallbacks{
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
    @Inject
    protected SpeechSynthesizerService speechSynthesizerService;
    private int ENtoZHorZHtoEN;
    @Inject
    public ChatPresenterTranslate chatPresenterTranslate;


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

        DaggerChatActivityTranslateComponent
                .builder()
                .chatActivityTranslateModule(new ChatActivityTranslateModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build().inject(this);
    }

    //初始化语音识别与语音合成
    private void initSpeech() {
        speechRecognizerService = new SpeechRecognizerService(this);
        speechRecognizerService.attachView(this);
    }

    private List<ChatMessageInfo> getData() {
//        ChatMessageInfo chatMessageInfo=new ChatMessageInfo();
//        chatMessageInfo.setSendOrReciveFlag(App.SEND_FLAG);
//        chatMessageInfo.setMessageContent("hello hello ");
//        chatMessageInfoList.add(chatMessageInfo);
//
//        ChatMessageInfo chatMessageInfo1=new ChatMessageInfo();
//        chatMessageInfo1.setSendOrReciveFlag(App.RECEIVE_FLAG);
//        chatMessageInfo1.setMessageContent("how are you");
//        chatMessageInfoList.add(chatMessageInfo1);
//        return chatMessageInfoList;
        return null;
    }

    public static final int RECORD_AUDIO = 100;
    String[] params = {Manifest.permission.RECORD_AUDIO};
    /**
     * 检查权限
     */
    @AfterPermissionGranted(RECORD_AUDIO)
    private void checkPerm() {

        if (EasyPermissions.hasPermissions(this, params)) {
            //已经获取到权限
            speechRecognizerService.startSpeechRecognizer();
        } else {
            EasyPermissions.requestPermissions(this, "需要录音权限", RECORD_AUDIO, params);
        }

    }

    public void init() {
        chatMessageInfoList = new ArrayList<ChatMessageInfo>();
        mChatAdapter = new TranslateAdapter(this, getData());
        mChatAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ChatMessageInfo chatMessageInfo = (ChatMessageInfo) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.chat_send_content:
                        ToastUtils.showShort(chatMessageInfo.getMessageContent());
                        break;
                    case R.id.chat_receive_content:
                        speechSynthesizerService.play(chatMessageInfo.getMessageContent());
                        break;
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        chatView.setLayoutManager(layoutManager);
        chatView.getBaseline();
        chatView.setAdapter(mChatAdapter);

        mChatContent.setOnFocusChangeListener(this);
        getActionBar().setTitle(chatTitle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.chat_send)
    public void onClick() {
        setENTOZH();
        sendMessage(mChatContent.getText().toString());
        mChatContent.setText("");
    }

    //中文
    @OnClick(R.id.chat_send2)
    public void onClick2() {
        setZHTOEN();
        sendMessage(mChatContent2.getText().toString());
        mChatContent2.setText("");
    }

    @OnClick(R.id.voice_button)
    public void onVoiceClick() {
        checkPerm();
        setENTOZH();

    }

    private void setENTOZH() {
        ENtoZHorZHtoEN = UrlUtils.ENTOZH;
        speechRecognizerService.setParam("en_us");
    }

    @OnClick(R.id.voice_button2)
    public void onVoiceClick2() {
        checkPerm();
        setZHTOEN();

    }

    private void setZHTOEN() {
        speechRecognizerService.setParam("zh_cn");
        ENtoZHorZHtoEN = UrlUtils.ZHTOEN;
    }

    private void sendMessage(String message) {
        if (message.trim().length() > 0) {
            //生成一个token
            String voiceName = UUID.randomUUID().toString();
            ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
            chatMessageInfo.setSender();
            chatMessageInfo.setMessageContent(message);
            chatMessageInfo.setMessageType(App.MESSAGE_TYPE_TEXT);
            chatMessageInfo.setChatRoomID(mChatRoomID);
            chatMessageInfo.setMessageVoiceUrl(voiceName);
            chatMessageInfoList.add(chatMessageInfo);
            mChatAdapter.setNewData(chatMessageInfoList);
            chatPresenterTranslate.translate(ENtoZHorZHtoEN, chatMessageInfo);
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
    public void onSpeechRecognizerComplete(String string) {
        sendMessage(string);
    }

    @Override
    public void onSpeechRecognizerError(String string) {
        ToastUtils.showShort("语音识别错误");
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        chatLine.setBackgroundColor(getResources().getColor(R.color.tab_color_s));
    }

    @Override
    public void onTranslateComplete(ChatMessageInfo requestChatMessageInfo, ChatMessageInfo respondChatMessageInfo) {
        int indexOf = chatMessageInfoList.indexOf(requestChatMessageInfo);
        chatMessageInfoList.get(indexOf).setStatus(App.MESSAGE_STATUS_SUCCESS);
        chatMessageInfoList.add(respondChatMessageInfo);
        mChatAdapter.setNewData(chatMessageInfoList);
        chatView.scrollToPosition(chatMessageInfoList.size() - 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        speechRecognizerService.startSpeechRecognizer();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //这里需要重新设置Rationale和title，否则默认是英文格式
            new AppSettingsDialog.Builder(this)
                    .setRationale("语音识别需要录音权限，现在去设置？")
                    .setTitle("权限设置")
                    .build()
                    .show();
        }
    }

}
