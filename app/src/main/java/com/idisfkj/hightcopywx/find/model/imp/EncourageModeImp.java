package com.idisfkj.hightcopywx.find.model.imp;


import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.RespondPage;
import com.idisfkj.hightcopywx.beans.gson.JsonParseUtils;
import com.idisfkj.hightcopywx.find.model.EncourageEntity;
import com.idisfkj.hightcopywx.find.model.EncourageModel;
import com.idisfkj.hightcopywx.util.UrlUtils;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtils.SERVER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        EncourageService apiService = retrofit.create(EncourageService.class);

        rx.Observable<RespondPage> observable = apiService.getEncourageList(App.token, String.valueOf(page), "5", "id", "asc");
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
