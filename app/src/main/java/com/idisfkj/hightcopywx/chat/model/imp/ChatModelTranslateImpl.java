package com.idisfkj.hightcopywx.chat.model.imp;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.BuildConfig;
import com.idisfkj.hightcopywx.beans.BaiduFanyiResponse;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.chat.model.ChatModelTranslate;
import com.idisfkj.hightcopywx.util.SignUtils;
import com.idisfkj.hightcopywx.util.SpeechSynthesizerService;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.idisfkj.hightcopywx.util.UrlUtils.ZHTOEN;

/**
 * Created by fvelement on 2017/10/16.
 */

public class ChatModelTranslateImpl implements ChatModelTranslate {
    protected SpeechSynthesizerService speechSynthesizerService;
    private Retrofit mRetrofit;

    public ChatModelTranslateImpl(Retrofit mRetrofit) {
        this.mRetrofit = mRetrofit;
    }

    interface ApiBaidu {
        //http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4
        @GET("api/trans/vip/translate?")
        Observable<BaiduFanyiResponse> translateBaiDu(
                @Query("q") String q,
                @Query("from") String from,
                @Query("to") String to,
                @Query("appid") String appid,
                @Query("salt") String salt,
                @Query("sign") String sign
        );
    }

    @Override
    public void translate(final TranslateListner translateListner, int type, final ChatMessageInfo chatMessageInfo) {

        ApiBaidu apiService = mRetrofit.create(ApiBaidu.class);

        String salt = SignUtils.getRandomInt(10);
        String sign = SignUtils.getSign(BuildConfig.BAIDU_APP_ID, chatMessageInfo.getMessageContent(), salt, BuildConfig.BAIDU_SCREAT_KEY);
        String fromString = "zh";
        String toString = "en;";
        if (type == ZHTOEN) {
            fromString = "zh";
            toString = "en";
        } else {
            fromString = "en";
            toString = "zh";
        }
        rx.Observable<BaiduFanyiResponse> observable = apiService.translateBaiDu(chatMessageInfo.getMessageContent(), fromString, toString, BuildConfig.BAIDU_APP_ID, salt, sign);
        if (observable == null) {
            return;
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaiduFanyiResponse>() {
                    @Override
                    public void onCompleted() {
                        System.out.print("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.print("");
                    }

                    @Override
                    public void onNext(BaiduFanyiResponse baiduFanyiResponse) {
                        if (baiduFanyiResponse.getTrans_result() != null && baiduFanyiResponse.getTrans_result().size() > 0) {
                            String result = (String) baiduFanyiResponse.getTrans_result().get(0).getDst();
                            ChatMessageInfo mChatMessageInfo = new ChatMessageInfo();
                            mChatMessageInfo.setStatus(App.MESSAGE_STATUS_SUCCESS);
                            mChatMessageInfo.setChatRoomID(chatMessageInfo.getChatRoomID());
                            mChatMessageInfo.setMessageContent(result);
                            mChatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
                            mChatMessageInfo.setSendMobile(chatMessageInfo.getSendMobile());
                            mChatMessageInfo.setSendName("贝贝翻译");
                            translateListner.onTranslateComplete(chatMessageInfo, mChatMessageInfo);
                        }

                    }

                });
    }

}
