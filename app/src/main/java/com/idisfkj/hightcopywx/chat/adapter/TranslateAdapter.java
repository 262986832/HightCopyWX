package com.idisfkj.hightcopywx.chat.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;

import java.util.List;

/**
 * Created by fvelement on 2017/10/13.
 */

public class TranslateAdapter extends BaseMultiItemQuickAdapter<ChatMessageInfo, BaseViewHolder> {
    public TranslateAdapter(Context context, List data) {
        super(data);
        addItemType(App.SEND_FLAG, R.layout.chat_send);
        addItemType(App.RECEIVE_FLAG, R.layout.chat_receive);

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ChatMessageInfo chatMessageInfo) {

        switch (baseViewHolder.getItemViewType()) {
            case App.SEND_FLAG:
                baseViewHolder.setVisible(R.id.chat_send_man_name, false);
                baseViewHolder.setText(R.id.chat_send_content, chatMessageInfo.getMessageContent());
                baseViewHolder.addOnClickListener(R.id.chat_send_content).addOnClickListener(R.id.chat_send_man_picture);
                if (chatMessageInfo.getStatus() == App.MESSAGE_STATUS_SUCCESS) {
                    baseViewHolder.setVisible(R.id.chat_item_fail, false);
                }
                break;
            case App.RECEIVE_FLAG:
                baseViewHolder.setVisible(R.id.chat_receive_man_name, false);
                baseViewHolder.setText(R.id.chat_receive_content, chatMessageInfo.getMessageContent());
                baseViewHolder.setVisible(R.id.chat_receive_content, true);
                baseViewHolder.addOnClickListener(R.id.chat_receive_content).addOnClickListener(R.id.chat_receive_content);
                break;
        }
    }
}
