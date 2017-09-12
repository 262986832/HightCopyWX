package com.idisfkj.hightcopywx.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.util.CursorUtils;
import com.readystatesoftware.viewbadger.BadgeView;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.idisfkj.hightcopywx.util.CursorUtils.formatString;

/**
 * 聊天通信适配器
 * Created by idisfkj on 16/4/22.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomsAdapter extends RecyclerViewCursorBaseAdapter<ChatRoomsAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ChatRoomsAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.wx_item, parent, false);
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        holder.wxItemTitle.setText(formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.title));
        holder.chatTitle=(formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.title));
        holder.wxItemContent.setText(formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.content));
        holder.wxItemTime.setText(formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.time));
        holder.unReadNum = CursorUtils.formatInt(cursor, ChatRoomsDataHelper.WXItemDataInfo.unReadNumber);
        holder.chatType = CursorUtils.formatInt(cursor, ChatRoomsDataHelper.WXItemDataInfo.chatType);
        holder.chatRoomID=CursorUtils.formatString(cursor, ChatRoomsDataHelper.WXItemDataInfo.chatRoomID);
        //更新气泡
        if (holder.unReadNum > 0) {
            holder.badgeView.setText(holder.unReadNum+"");
            holder.badgeView.show();
        }else {
            holder.badgeView.hide();
        }

    }

    void test() {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.wx_item_picture)
        ImageView wxItemPicture;
        @InjectView(R.id.wx_item_title)
        TextView wxItemTitle;
        @InjectView(R.id.wx_item_time)
        TextView wxItemTime;
        @InjectView(R.id.wx_item_content)
        TextView wxItemContent;
        public int unReadNum;
        public BadgeView badgeView;
        public int chatType = 0;

        public String chatRoomID;
        public String chatTitle;
        private Context mContext;




        public int getChatType() {
            return chatType;
        }

        public void setChatType(int chatType) {
            this.chatType = chatType;
        }

        ViewHolder(View view, Context context) {
            super(view);
            ButterKnife.inject(this, view);
            mContext = context;

            badgeView = new BadgeView(mContext, wxItemPicture);
            badgeView.setText("12");
            badgeView.hide();
        }
    }
}
