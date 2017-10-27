package com.idisfkj.hightcopywx.contact.model.impl;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.RespondPage;
import com.idisfkj.hightcopywx.beans.WordsEntity;
import com.idisfkj.hightcopywx.beans.gson.JsonParseUtils;
import com.idisfkj.hightcopywx.contact.model.ContactModel;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fvelement on 2017/10/19.
 */

public class ContactModelImpl implements ContactModel {
    String limit = "5";
    public Retrofit retrofit;

    public ContactModelImpl(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    interface EncourageService {
        @GET("getWordsList")
        Observable<RespondPage> getEncourageList(@Header("token") String token
                , @Query("page") String page
                , @Query("limit") String limit
                , @Query("sidx") String sidx
                , @Query("order") String order);
    }
    @Override
    public void getWordData(final GetWordListener getWordListener, int page) {

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
                        List<WordsEntity> list = jsonParseUtils.parseString2List(respondPage.getPage().getListString(), WordsEntity.class);
                        getWordListener.onGetWordSuccess(list);
                    }

                });
    }
}
