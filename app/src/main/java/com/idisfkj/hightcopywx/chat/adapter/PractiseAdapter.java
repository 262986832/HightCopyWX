package com.idisfkj.hightcopywx.chat.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.beans.WordsEntity;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by fvelement on 2017/9/29.
 */

public class PractiseAdapter extends BaseQuickAdapter<WordsEntity, BaseViewHolder> {

    public PractiseAdapter(int layoutResId, List<WordsEntity> list) {
        super(layoutResId, list);
    }
    private boolean img_folded=false;
    private boolean english_folded=false;

    public boolean isEnglish_folded() {
        return english_folded;
    }

    public void setEnglish_folded(boolean english_folded) {
        this.english_folded = english_folded;
    }

    public boolean isFolded() {
        return img_folded;
    }

    public void setFolded(boolean folded) {
        this.img_folded = folded;
    }

    @Override
    protected void convert(BaseViewHolder helper, WordsEntity item) {
        String imgurl=item.getImgurl();
        if(StringUtils.isBlank(imgurl))
            imgurl=App.BOOK_IMG_URL+item.getEnglish()+".jpg";
        Glide.with(App.getAppContext()).
                load(imgurl)
                .crossFade(3000)
                .error(R.mipmap.loaderror)
                .into((ImageView) helper.getView(R.id.practise_card_imgurl));
        helper.setText(R.id.practise_card_title, item.getEnglish());
        helper.setText(R.id.practise_card_text, item.getChinese());
        helper.addOnClickListener(R.id.practise_card_imgurl)
                .addOnClickListener(R.id.practise_card_title)
                .addOnClickListener(R.id.practise_card_text)
                .addOnClickListener(R.id.practise_picture);
        if(img_folded)
            helper.setGone(R.id.practise_card_imgurl,false);
        else
            helper.setGone(R.id.practise_card_imgurl,true);
        if(english_folded)
            helper.setGone(R.id.practise_card_title,false);
        else
            helper.setGone(R.id.practise_card_title,true);


    }

}