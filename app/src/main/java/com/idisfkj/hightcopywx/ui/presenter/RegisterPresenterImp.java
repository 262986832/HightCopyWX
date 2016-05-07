package com.idisfkj.hightcopywx.ui.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.EditText;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.ui.modle.RegisterModle;
import com.idisfkj.hightcopywx.ui.modle.RegisterModleImp;
import com.idisfkj.hightcopywx.ui.view.RegisterView;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class RegisterPresenterImp implements RegisterPresenter, RegisterModleImp.saveDataListener, RegisterModleImp.sendAllListener {
    private RegisterView mRegisterView;
    private RegisterModle mRegisterModle;

    public RegisterPresenterImp(RegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mRegisterModle = new RegisterModleImp();
    }

    @Override
    public void switchUserLine(boolean hasFocus,int id) {
        switch (id){
            case R.id.userName_et:
                mRegisterView.changeUserNameLine(hasFocus);
                break;
            case R.id.userPhone_et:
                mRegisterView.changeUserPhoneLine(hasFocus);
                break;
            case R.id.userPassword_et:
                mRegisterView.changeUserPasswordLine(hasFocus);
                break;
        }
    }

    @Override
    public void registerInfo(EditText... editTexts) {
        mRegisterView.showProgressDialog();
        mRegisterModle.saveData(this,editTexts);
    }

    @Override
    public void choosePicture() {
        mRegisterView.showAlertDialog();
    }

    @Override
    public void callCrop(Uri uri) {
        mRegisterView.startCrop(uri);
    }

    @Override
    public void getPicture(Intent intent) {
        mRegisterView.setHeadPicture(intent);
    }

    @Override
    public void savePicture(Bitmap bitmap) {
        mRegisterView.saveHeadPicture(bitmap);
    }

    @Override
    public void onSucceed(String userName,String number) {
        //个人标识
        MiPushClient.setUserAccount(App.getAppContext(),number,null);
        mRegisterModle.sendAll(this,userName,number);
    }

    @Override
    public void onSendSucceed() {
        mRegisterView.hideProgressDialog();
        mRegisterView.showSucceedToast();
        //全体标识
        MiPushClient.subscribe(App.getAppContext(),"register",null);
        mRegisterView.jumpMainActivity();
    }

    @Override
    public void onError() {
        mRegisterView.hideProgressDialog();
        mRegisterView.showErrorToast();
    }
}
