package com.idisfkj.hightcopywx.ui.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.EditText;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.ui.model.RegisterModel;
import com.idisfkj.hightcopywx.ui.model.RegisterModelImp;
import com.idisfkj.hightcopywx.ui.view.RegisterView;
import com.idisfkj.hightcopywx.util.ToastUtils;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class RegisterPresenterImp implements RegisterPresenter, RegisterModel.requestRegisterListener {
    private RegisterView mRegisterView;
    private RegisterModel mRegisterModel;

    public RegisterPresenterImp(RegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mRegisterModel = new RegisterModelImp();
    }

    @Override
    public void switchUserLine(boolean hasFocus, int id) {
        switch (id) {
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
        String[] user;
        user = new String[editTexts.length];
        for (int i = 0; i < editTexts.length; i++) {
            user[i] = editTexts[i].getText().toString().trim();
            if (user[i].length() <= 0) {
                ToastUtils.showShort("昵称、号码或密码不能为空");
                return;
            }
        }
        mRegisterModel.requestRegister(this, user[0], user[1], user[2]);
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
    public void onRegisterSucceed() {
        mRegisterView.hideProgressDialog();
        mRegisterView.showSucceedToast();
        mRegisterView.jumpMainActivity();
    }

    @Override
    public void onError(String msg) {
        mRegisterView.hideProgressDialog();
        mRegisterView.showErrorToast();
    }
}
