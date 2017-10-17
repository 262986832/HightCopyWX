package com.idisfkj.hightcopywx.find.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.model.EncourageEntity;

import java.util.List;

/**
 * Created by fvelement on 2017/9/29.
 */

public class FindAdapter extends BaseQuickAdapter<EncourageEntity, BaseViewHolder> {

    public FindAdapter(int layoutResId, List<EncourageEntity> list) {
        super(layoutResId, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, EncourageEntity item) {
        Glide.with(App.getAppContext()).
                load(item.getEncourageimgurl())
                .crossFade(3000)
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loaderror)
                .into((ImageView) helper.getView(R.id.find_card_imgurl));
        helper.setText(R.id.find_card_title, item.getEncouragetitle());
        helper.setText(R.id.find_card_text, item.getEncouragetext());
    }


}