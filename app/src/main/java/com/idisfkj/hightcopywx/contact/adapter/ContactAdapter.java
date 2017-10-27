package com.idisfkj.hightcopywx.contact.adapter;

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

public class ContactAdapter extends BaseQuickAdapter<WordsEntity, BaseViewHolder> {

    public ContactAdapter(int layoutResId, List<WordsEntity> list) {
        super(layoutResId, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, WordsEntity item) {
        Glide.with(App.getAppContext()).
                load(item.getImgurl())
                .crossFade(3000)
                .placeholder(R.mipmap.loading)
                .error(R.mipmap.loaderror)
                .into((ImageView) helper.getView(R.id.contact_item_picture));
        helper.setText(R.id.contact_item_title, item.getEnglish());
        helper.setText(R.id.contact_item_content, item.getChinese());
    }


}