package com.idisfkj.hightcopywx.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.idisfkj.hightcopywx.registerLogin.widget.RegisterActivity;
import com.idisfkj.hightcopywx.util.CursorUtils;
import com.idisfkj.hightcopywx.util.ToastUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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
    private Bitmap sendBitmap;

    public ChatAdapter(Context context) {
        super(context, null);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        File path = new File(RegisterActivity.SAVE_PATH + RegisterActivity.PICTURE_NAME);
        if (path.exists()) {
            sendBitmap = BitmapFactory.decodeFile(RegisterActivity.SAVE_PATH + RegisterActivity.PICTURE_NAME);
        }
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

        if (holder instanceof ChatReceiveViewHolder) {

            ((ChatReceiveViewHolder) holder).chatReceiveTime.
                    setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.time));

            ((ChatReceiveViewHolder) holder).chat_receive_man_name
                    .setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.sendName));

            int messageType = CursorUtils.formatInt(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageType);
            if ( messageType == App.MESSAGE_TYPE_CARD) {
                ((ChatReceiveViewHolder) holder).chat_receive_card.setVisibility(View.VISIBLE);
                ((ChatReceiveViewHolder) holder).chatReceiveContent.setVisibility(View.GONE);
                Glide.with(App.getAppContext()).
                        load(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageImgUrl))
                        .crossFade(5000)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into( ((ChatReceiveViewHolder) holder).chat_receive_card_imgurl);
                ((ChatReceiveViewHolder) holder).chat_receive_card_title.
                        setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageTitle));
                ((ChatReceiveViewHolder) holder).chat_receive_card_text.
                        setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));
            } else {
                ((ChatReceiveViewHolder) holder).chat_receive_card.setVisibility(View.GONE);
                ((ChatReceiveViewHolder) holder).chatReceiveContent.setVisibility(View.VISIBLE);
                ((ChatReceiveViewHolder) holder).chatReceiveContent.
                        setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));
            }


        } else if (holder instanceof ChatSendViewHolder) {
            //隐藏发送失败图标
            if (CursorUtils.formatInt(cursor, ChatMessageDataHelper.ChatMessageDataInfo.status) == App.MESSAGE_STATUS_SUCCESS)
                ((ChatSendViewHolder) holder).chat_item_fail.setVisibility(View.GONE);

            ((ChatSendViewHolder) holder).chatSendTime.
                    setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.time));

            String name = App.userName;
            ((ChatSendViewHolder) holder).chat_send_man_name.setText(name);

            if (sendBitmap != null)
                ((ChatSendViewHolder) holder).chat_send_man_picture.setImageBitmap(sendBitmap);

            ((ChatSendViewHolder) holder).content =
                    CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent);

            ((ChatSendViewHolder) holder).chatSendContent.setText
                    (CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));

            ((ChatSendViewHolder) holder).chatSendContent.setVisibility(View.VISIBLE);
        } else {
            ((ChatSystemViewHolder) holder).chatSystemTime.
                    setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.time));
            ((ChatSystemViewHolder) holder).chatSystemContent.
                    setText(CursorUtils.formatString(cursor, ChatMessageDataHelper.ChatMessageDataInfo.messageContent));
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
        @InjectView(R.id.chat_send_time)
        TextView chatSendTime;
        @InjectView(R.id.chat_send_man_picture)
        ImageView chat_send_man_picture;
        @InjectView(R.id.chat_send_man_name)
        TextView chat_send_man_name;

        @InjectView(R.id.chat_send_content)
        TextView chatSendContent;
        @InjectView(R.id.chat_item_fail)
        ImageView chat_item_fail;

        String content;

        ChatSendViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        @OnClick(R.id.chat_send_man_picture)
        public void onHeadClick() {
            ToastUtils.showShort(content);
        }

        @OnClick(R.id.chat_send_content)
        public void onContentClick() {
            ToastUtils.showShort(chatSendContent.getText());
        }
    }

    //收到信息
    public static class ChatReceiveViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.chat_receive_time)
        TextView chatReceiveTime;
        @InjectView(R.id.chat_receive_picture)
        ImageView chatReceivePicture;
        @InjectView(R.id.chat_receive_content)
        TextView chatReceiveContent;

        @InjectView(R.id.chat_receive_man_name)
        TextView chat_receive_man_name;

        @InjectView(R.id.chat_receive_card_title)
        TextView chat_receive_card_title;
        @InjectView(R.id.chat_receive_card_text)
        TextView chat_receive_card_text;
        @InjectView(R.id.chat_receive_card_imgurl)
        ImageView chat_receive_card_imgurl;
        @InjectView(R.id.chat_receive_card)
        CardView chat_receive_card;


        ChatReceiveViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public static class ChatSystemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.chat_system_time)
        TextView chatSystemTime;
        @InjectView(R.id.chat_system_content)
        TextView chatSystemContent;

        ChatSystemViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
