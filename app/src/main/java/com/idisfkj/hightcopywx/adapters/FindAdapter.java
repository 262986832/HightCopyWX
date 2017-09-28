package com.idisfkj.hightcopywx.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fvelement on 2017/9/28.
 */

public class FindAdapter extends RecyclerViewCursorBaseAdapter<FindAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public FindAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(FindAdapter.ViewHolder holder, Cursor cursor) {
        Glide.with(App.getAppContext()).
                load("http://owvifpcqf.bkt.clouddn.com/FtEjsJGtC9cwcbMWlXppqtbPrW1y")
                .crossFade(5000)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.find_card_imgurl);
        holder.find_card_title.setText("第一课");
    }

    @Override
    public FindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view, mContext);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.find_card_imgurl)
        ImageView find_card_imgurl;
        @Bind(R.id.find_card_title)
        TextView find_card_title;
        @Bind(R.id.find_card_text)
        TextView find_card_text;
        @Bind(R.id.find_card_imgurl1)
        ImageView find_card_imgurl1;

        ViewHolder(View view, Context context) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.find_card_imgurl)
        public void onHeadClick() {
            ToastUtils.showShort("");
        }
    }
}
