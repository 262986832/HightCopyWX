package com.idisfkj.hightcopywx.find.presenter.imp;

import com.idisfkj.hightcopywx.find.model.EncourageEntity;
import com.idisfkj.hightcopywx.find.model.EncourageModel;
import com.idisfkj.hightcopywx.find.presenter.EncouragePresenter;
import com.idisfkj.hightcopywx.find.view.EncourageView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by fvelement on 2017/10/10.
 */

public class EncouragePresenterImp implements EncouragePresenter,EncourageModel.GetEncourageListener {
    private EncourageModel encourageModel;
    private EncourageView encourageView;
@Inject
    public EncouragePresenterImp(EncourageView encourageView,EncourageModel encourageModel) {
        this.encourageView = encourageView;
        this.encourageModel=encourageModel;
    }

    @Override
    public void getEncourageData(int page) {
        encourageModel.getEncourageData(this,page);
    }

    @Override
    public void onGetEncourageSuccess(List<EncourageEntity> encourageEntityList) {
        encourageView.onGetEncourageSuccess(encourageEntityList);
    }

    @Override
    public void onGetEncourageFail() {

    }
}
