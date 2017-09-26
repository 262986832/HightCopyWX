package com.idisfkj.hightcopywx.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapters.FragmentAdapter;
import com.idisfkj.hightcopywx.base.widget.BaseActivity;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.UnReadNumber;
import com.idisfkj.hightcopywx.beans.eventbus.ShowSetDialog;
import com.idisfkj.hightcopywx.chat.widget.ChatActivity;
import com.idisfkj.hightcopywx.main.presenter.imp.MainPresenterImp;
import com.idisfkj.hightcopywx.main.view.MainView;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.readystatesoftware.viewbadger.BadgeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainView, MainPresenterImp> implements MainView {

    @Bind(R.id.viewPage)
    ViewPager viewPage;
    @Bind(R.id.wei_xin_s)
    ImageView weiXinS;
    @Bind(R.id.address_book_s)
    ImageView addressBookS;
    @Bind(R.id.find_s)
    ImageView findS;
    @Bind(R.id.find)
    ImageView find;
    @Bind(R.id.me_s)
    ImageView meS;
    @Bind(R.id.tab_weiXin_s)
    TextView tabWeiXinS;
    @Bind(R.id.tab_address_s)
    TextView tabAddressS;
    @Bind(R.id.tab_find_s)
    TextView tabFindS;
    @Bind(R.id.tab_me_s)
    TextView tabMeS;

    private List<ImageView> mListImage = new ArrayList<>();
    private List<TextView> mListText = new ArrayList<>();
    private int[] viewId;
    private Bundle bundle;
    private BadgeView badgeView;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);//订阅总线消息
        init();
        initImagePicker();
        //是否是点击通知跳转
        bundle = getIntent().getExtras();
        if (bundle != null) {
            mPresenter.switchActivity();
        }

    }

    public void init() {

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPage.setAdapter(adapter);
        weiXinS.setAlpha(1.0f);
        tabWeiXinS.setAlpha(1.0f);
        setViewPageListener();

        viewId = new int[]{R.id.ll_wx, R.id.ll_address, R.id.ll_find, R.id.ll_me};
        mListImage.add(weiXinS);
        mListImage.add(addressBookS);
        mListImage.add(findS);
        mListImage.add(meS);
        mListText.add(tabWeiXinS);
        mListText.add(tabAddressS);
        mListText.add(tabFindS);
        mListText.add(tabMeS);

        badgeView = new BadgeView(this, weiXinS);
        badgeView.setText("12");
        badgeView.hide();
        Gson mGson = new Gson();

    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(300);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(300);//保存文件的高度。单位像素
    }

    //收到小米推送的消息
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void handleMessage(ChatMessageInfo chatMessageInfo) {
        mPresenter.receiveData(chatMessageInfo);
    }

    //收到main气泡更新消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showBadge(UnReadNumber unReadNumber) {
        int count = unReadNumber.getUnReadNumber();
        if (count > 0) {
            badgeView.setText("" + count);
            badgeView.show();
        } else {
            badgeView.hide();
        }
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMessage(ShowSetDialog showSetDialog) {
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, false); // 是否是直
        startActivityForResult(intent, IMAGE_PICKER);
    }

    int IMAGE_PICKER = 101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Iterator<ImageItem> iterator = images.iterator();
                while (iterator.hasNext()) {
                    ImageItem imageItem = iterator.next();
                    SharedPreferencesManager.putString("head", imageItem.path).commit();
                    uploadImg2QiNiu(imageItem.path, imageItem.name);
                }
            } else {

                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void setViewPageListener() {
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    mListImage.get(position).setAlpha(1 - positionOffset);
                    mListImage.get(position + 1).setAlpha(positionOffset);
                    mListText.get(position).setAlpha(1 - positionOffset);
                    mListText.get(position + 1).setAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchWX() {
        viewPage.setCurrentItem(0, false);
        weiXinS.setAlpha(1.0f);
        tabWeiXinS.setAlpha(1.0f);
        find.setAlpha(1.0f);
    }

    @Override
    public void switchAddressBook() {
        viewPage.setCurrentItem(1, false);
        addressBookS.setAlpha(1.0f);
        tabAddressS.setAlpha(1.0f);
        find.setAlpha(1.0f);
    }

    @Override
    public void switchFind() {
        viewPage.setCurrentItem(2, false);
        findS.setAlpha(1.0f);
        find.setAlpha(0f);
        tabFindS.setAlpha(1.0f);
    }

    @Override
    public void switchMe() {
        viewPage.setCurrentItem(3, false);
        meS.setAlpha(1.0f);
        tabMeS.setAlpha(1.0f);
        find.setAlpha(1.0f);
    }

    @Override
    public void switchAlpha(int id) {
        for (int i = 0; i < viewId.length; i++) {
            if (id != viewId[i]) {
                mListImage.get(i).setAlpha(0f);
                mListText.get(i).setAlpha(0f);
            }
        }
    }

    @Override
    public void jumpChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @OnClick({R.id.ll_wx, R.id.ll_address, R.id.ll_find, R.id.ll_me})
    public void onClick(View view) {
        mPresenter.switchNavigation(view.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    protected MainPresenterImp createPresenter() {
        return new MainPresenterImp();
    }


    private void uploadImg2QiNiu(String path, String name) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(path, name, App.uploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                if (info.isOK()) {
                    String name = "";
                    try {
                        name = res.getString("hash");
                        String headpicPath = "http://owvifpcqf.bkt.clouddn.com/" + name;
                        mPresenter.uploadHeadUrl(headpicPath);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, null);
    }

}
