package com.idisfkj.hightcopywx.chat.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.WordsEntity;

import java.util.List;

/**
 * Created by fvelement on 2017/9/29.
 */

public class PractiseAdapter extends BaseQuickAdapter<WordsEntity, BaseViewHolder> {

    public PractiseAdapter(int layoutResId, List<WordsEntity> list) {
        super(layoutResId, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, WordsEntity item) {
        Glide.with(App.getAppContext()).
                load(item.getImgurl())
                .crossFade(3000)
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loaderror)
                .into((ImageView) helper.getView(R.id.practise_card_imgurl));
        helper.setText(R.id.practise_card_title, item.getEnglish());
        helper.setText(R.id.practise_card_text, item.getChinese());
        helper.addOnClickListener(R.id.practise_card_imgurl)
                .addOnClickListener(R.id.practise_card_title)
                .addOnClickListener(R.id.practise_card_text)
                .addOnClickListener(R.id.practise_picture);

    }


}