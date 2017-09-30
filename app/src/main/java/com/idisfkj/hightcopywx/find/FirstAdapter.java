package com.idisfkj.hightcopywx.find;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.EncourageEntity;

import java.util.List;

/**
 * Created by fvelement on 2017/9/29.
 */

public class FirstAdapter extends BaseQuickAdapter<EncourageEntity, BaseViewHolder> {

    public FirstAdapter(int layoutResId, List<EncourageEntity> list) {
        super(layoutResId, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, EncourageEntity item) {
        Glide.with(App.getAppContext()).
                load("http://owvifpcqf.bkt.clouddn.com/FtEjsJGtC9cwcbMWlXppqtbPrW1y")
                .crossFade(5000)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into((ImageView) helper.getView(R.id.find_card_imgurl));
        helper.setText(R.id.find_card_title, item.getEncouragetitle());
    }
}