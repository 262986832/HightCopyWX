package com.idisfkj.hightcopywx.main.widget;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.base.fragment.PictureSelectFragment;

import butterknife.Bind;


/**
 * Created by fvelement on 2017/9/25.
 */

public class MainFragment extends PictureSelectFragment {

    @Bind(R.id.main_frag_picture_iv)
    ImageView mPictureIv;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void initEvents() {
        // 设置图片点击监听
        mPictureIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                mPictureIv.setImageBitmap(bitmap);

                String filePath = fileUri.getEncodedPath();
                String imagePath = Uri.decode(filePath);
                Toast.makeText(mContext, "图片已经保存到:" + imagePath, Toast.LENGTH_LONG).show();
            }
        });
    }

}