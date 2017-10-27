package com.idisfkj.hightcopywx.injection.modules;

import com.idisfkj.hightcopywx.find.model.EncourageModel;
import com.idisfkj.hightcopywx.find.model.imp.EncourageModeImp;
import com.idisfkj.hightcopywx.find.presenter.EncouragePresenter;
import com.idisfkj.hightcopywx.find.presenter.imp.EncouragePresenterImp;
import com.idisfkj.hightcopywx.find.view.EncourageView;
import com.idisfkj.hightcopywx.injection.FindScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by fvelement on 2017/10/27.
 */
@Module
public class FindModules {
    private EncourageView mEncourageView;
    public FindModules(EncourageView encourageView) {
        this.mEncourageView = encourageView;
    }
    @Provides
    @FindScope
    EncourageModel encourageModel(Retrofit retrofit){
        return  new EncourageModeImp(retrofit);
    }
    @Provides
    @FindScope
    EncouragePresenter encouragePresenter(EncourageModel encourageModel){
        return new EncouragePresenterImp(mEncourageView,encourageModel);
    }

}
