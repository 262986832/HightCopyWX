package com.idisfkj.hightcopywx.adapters;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.util.CursorUtils;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.idisfkj.hightcopywx.util.SpeechSynthesizerService;
import com.idisfkj.hightcopywx.util.ToastUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.idisfkj.hightcopywx.R.id.chat_receive_content;
import static com.idisfkj.hightcopywx.util.CursorUtils.formatString;

/**
 * 聊天适配器
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public class ChatAdapter extends RecyclerViewCursorBaseAdapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private View view;
    private static final int RECEIVE_MESSAGE = 0;
    private static final int SEND_MESSAGE = 1;
    private static final int SYSTEM_MESSAGE = 2;
    private Cursor mCursor;
    private String mRoleName;


    public ChatAdapter(Context context) {
        super(context, null);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    public void setCursor(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RECEIVE_MESSAGE) {
            view = mLayoutInflater.inflate(R.layout.chat_receive, parent, false);
            return new ChatReceiveViewHolder(view);
        } else if (viewType == SEND_MESSAGE) {
            view = mLayoutInflater.inflate(R.layout.chat_send, parent, false);
            return new ChatSendViewHolder(view);
        } else {
            view = mLayoutInflater.inflate(R.layout.chat_system, parent, false);
            return new ChatSystemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        int messageType = CursorUtils.formatInt(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageType);
        if (holder instanceof ChatReceiveViewHolder) {

            ((ChatReceiveViewHolder) holder).chatReceiveTime.
                    setText(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.time));

            ((ChatReceiveViewHolder) holder).chat_receive_man_name
                    .setText(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.sendName));

            if (messageType == App.MESSAGE_TYPE_CARD) {
                ((ChatReceiveViewHolder) holder).chat_receive_card.setVisibility(View.VISIBLE);
                ((ChatReceiveViewHolder) holder).chatReceiveContent.setVisibility(View.GONE);
                Glide.with(App.getAppContext()).
                        load(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageImgUrl))
                        .crossFade(5000)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(((ChatReceiveViewHolder) holder).chat_receive_card_imgurl);
                ((ChatReceiveViewHolder) holder).chat_receive_card_title.
                        setText(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageTitle));
                ((ChatReceiveViewHolder) holder).chat_receive_card_text.
                        setText(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));
            } else {
                ((ChatReceiveViewHolder) holder).chat_receive_card.setVisibility(View.GONE);
                ((ChatReceiveViewHolder) holder).chatReceiveContent.setVisibility(View.VISIBLE);
                ((ChatReceiveViewHolder) holder).chatReceiveContent.
                        setText(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));
            }


        } else if (holder instanceof ChatSendViewHolder) {
            //隐藏发送失败图标
            if (CursorUtils.formatInt(cursor, ChatMessageDataHelper.ChatMessageDataInfo.status) == App.MESSAGE_STATUS_SUCCESS)
                ((ChatSendViewHolder) holder).chat_item_fail.setVisibility(View.GONE);

            ((ChatSendViewHolder) holder).chatSendTime.
                    setText(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.time));

            String name = App.userName;
            ((ChatSendViewHolder) holder).chat_send_man_name.setText(name);
            mRoleName = formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.roleID)
                    .equals("baby") ? "宝贝" : "家长";
            ((ChatSendViewHolder) holder).chat_send_man_role.setText(mRoleName);

            if (messageType == App.MESSAGE_TYPE_VOICE) {
                ((ChatSendViewHolder) holder).chat_send_voice.setVisibility(View.VISIBLE);
                ((ChatSendViewHolder) holder).chatSendContent.setVisibility(View.GONE);
            } else {
                ((ChatSendViewHolder) holder).chat_send_voice.setVisibility(View.GONE);
                ((ChatSendViewHolder) holder).chatSendContent.setVisibility(View.VISIBLE);
            }

            String head = SharedPreferencesManager.getString("head", "");
            if (!head.equals("")) {
                Uri uri = Uri.fromFile(new File(head));
                ((ChatSendViewHolder) holder).chat_send_man_picture.setImageURI(uri);
            }
            String voiceUrl = CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageVoiceUrl);
            if (StringUtils.isNoneEmpty(voiceUrl)) {
                ((ChatSendViewHolder) holder).voiceUrl = voiceUrl;
            }

            ((ChatSendViewHolder) holder).content =
                    CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent);


            ((ChatSendViewHolder) holder).chatSendContent.setText
                    (CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));
            ((ChatSendViewHolder) holder).chat_send_voice.setText
                    (CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));

//            if (CursorUtils.formatInt(mCursor, ChatMessageDataHelper.ChatMessageDataInfo.sendOrReciveFlag) == App.MESSAGE_TYPE_TEXT) {
//                ((ChatSendViewHolder) holder).chatSendContent.setCompoundDrawables(null, null, null, null);
//            }


        } else {
            ((ChatSystemViewHolder) holder).chatSystemTime.
                    setText(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.time));
            ((ChatSystemViewHolder) holder).chatSystemContent.
                    setText(formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mCursor.moveToPosition(position)) {
            if (CursorUtils.formatInt(mCursor, ChatMessageDataHelper.ChatMessageDataInfo.sendOrReciveFlag) == 0) {
                return RECEIVE_MESSAGE;
            } else if (CursorUtils.formatInt(mCursor, ChatMessageDataHelper.ChatMessageDataInfo.sendOrReciveFlag) == 1) {
                return SEND_MESSAGE;
            } else {
                return SYSTEM_MESSAGE;
            }
        }
        return 0;
    }


    //发送信息
    public static class ChatSendViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chat_send_time)
        TextView chatSendTime;
        @Bind(R.id.chat_send_man_picture)
        ImageView chat_send_man_picture;
        @Bind(R.id.chat_send_man_name)
        TextView chat_send_man_name;
        @Bind(R.id.chat_send_man_role)
        TextView chat_send_man_role;

        @Bind(R.id.chat_send_voice)
        TextView chat_send_voice;
        @Bind(R.id.chat_send_content)
        TextView chatSendContent;
        @Bind(R.id.chat_item_fail)
        ImageView chat_item_fail;

        String content;
        String voiceUrl;
        private MediaPlayer mediaPlayer;

        ChatSendViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        @OnClick(R.id.chat_send_man_picture)
        public void onHeadClick() {
            ToastUtils.showShort(content);
        }

        @OnClick(R.id.chat_send_content)
        public void onContentClick() {
            ToastUtils.showShort(chatSendContent.getText());
        }

        @OnClick(R.id.chat_send_voice)
        public void onVoiceClick() {
            if (StringUtils.isNoneEmpty(voiceUrl)) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mediaPlayer.reset();
                        try {
                            mediaPlayer.setDataSource(voiceUrl);
                            mediaPlayer.prepare();//prepare之后自动播放
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        }
    }

    //收到信息
    public static class ChatReceiveViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chat_receive_time)
        TextView chatReceiveTime;
        @Bind(R.id.chat_receive_picture)
        ImageView chatReceivePicture;
        @Bind(chat_receive_content)
        TextView chatReceiveContent;

        @Bind(R.id.chat_receive_man_name)
        TextView chat_receive_man_name;

        @Bind(R.id.chat_receive_card_title)
        TextView chat_receive_card_title;
        @Bind(R.id.chat_receive_card_text)
        TextView chat_receive_card_text;
        @Bind(R.id.chat_receive_card_imgurl)
        ImageView chat_receive_card_imgurl;
        @Bind(R.id.chat_receive_card)
        CardView chat_receive_card;

        protected SpeechSynthesizerService speechSynthesizerService;

        ChatReceiveViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            speechSynthesizerService = new SpeechSynthesizerService(App.getAppContext());
        }

        @OnClick({R.id.chat_receive_card_title, R.id.chat_receive_card_imgurl})
        public void onTitleClick() {
            speechSynthesizerService.play(chat_receive_card_title.getText().toString());
        }

        @OnClick({R.id.chat_receive_card_text})
        public void onContentClick() {
            speechSynthesizerService.play(chat_receive_card_text.getText().toString());
        }

        @OnClick({chat_receive_content})
        public void onReceiveContentClick() {
            speechSynthesizerService.play(chatReceiveContent.getText().toString());
        }

    }

    public static class ChatSystemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chat_system_time)
        TextView chatSystemTime;
        @Bind(R.id.chat_system_content)
        TextView chatSystemContent;

        ChatSystemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
