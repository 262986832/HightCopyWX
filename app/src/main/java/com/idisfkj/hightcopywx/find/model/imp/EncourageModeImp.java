package com.idisfkj.hightcopywx.find.model.imp;


import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.RespondPage;
import com.idisfkj.hightcopywx.beans.gson.JsonParseUtils;
import com.idisfkj.hightcopywx.find.model.EncourageEntity;
import com.idisfkj.hightcopywx.find.model.EncourageModel;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fvelement on 2017/10/10.
 */

public class EncourageModeImp implements EncourageModel {
    String limit = "5";
    @Inject
    public Retrofit retrofit;

    public EncourageModeImp(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    interface EncourageService {
        @GET("getEncourageList")
        Observable<RespondPage> getEncourageList(@Header("token") String token
                , @Query("page") String page
                , @Query("limit") String limit
                , @Query("sidx") String sidx
                , @Query("order") String order);
    }

    @Override
    public void getEncourageData(final GetEncourageListener getEncourageListener, int page) {
        EncourageService apiService = retrofit.create(EncourageService.class);

        rx.Observable<RespondPage> observable = apiService.getEncourageList(App.token, String.valueOf(page), limit, "id", "asc");
        if (observable == null) {
            return;
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RespondPage>() {
                    @Override
                    public void onCompleted() {
                        System.out.print("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.print("");
                    }

                    @Override
                    public void onNext(RespondPage respondPage) {
                        JsonParseUtils jsonParseUtils = new JsonParseUtils();
                        List<EncourageEntity> list = jsonParseUtils.parseString2List(respondPage.getPage().getListString(), EncourageEntity.class);
                        getEncourageListener.onGetEncourageSuccess(list);
                    }

                });
    }

}
