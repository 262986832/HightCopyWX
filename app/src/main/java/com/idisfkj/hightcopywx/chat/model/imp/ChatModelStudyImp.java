package com.idisfkj.hightcopywx.chat.model.imp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.RespondStudy;
import com.idisfkj.hightcopywx.beans.WordsStudentEntity;
import com.idisfkj.hightcopywx.chat.model.ChatModelStudy;
import com.idisfkj.hightcopywx.util.GsonRequest;
import com.idisfkj.hightcopywx.util.UrlUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import static android.content.ContentValues.TAG;

/**
 * Created by fvelement on 2017/9/13.
 */

public class ChatModelStudyImp extends ChatModelBase implements ChatModelStudy {
    private List<WordsStudentEntity> mWordsStudentEntityList = null;
    private boolean mIslast = false;
    private int mAllStudyCount = 1;
    private int mListIndex = 0;
    private int mWordListsCount = 0;
    private WordsStudentEntity mPreviousWords;
    String jsonMessage;

    public ChatModelStudyImp() {
    }

    @Override
    public void initData(final initListener listener) {
        //ToastUtils.showShort("正在初始化");
        GsonRequest<RespondStudy> gsonRequest = new GsonRequest<RespondStudy>(Request.Method.POST,
                UrlUtils.getNowDayWordListApiUrl(), RespondStudy.class,
                new Response.Listener<RespondStudy>() {
                    @Override
                    public void onResponse(RespondStudy respondStudy) {
                        if (respondStudy.getCode() == 0) {
                            insert(respondStudy.getListWords());
                            listener.onInitSucceed();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("token", App.token);
                return header;
            }
        };

        VolleyUtils.addQueue(gsonRequest, "chatRequest");
    }

    private void insert(List<WordsStudentEntity> wordsStudentEntityList) {
        this.mWordsStudentEntityList = wordsStudentEntityList;
        this.mWordListsCount = mWordsStudentEntityList.size();
    }


    @Override
    public ChatMessageInfo getStudyMessage(String chatRoomID) {
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        if (mListIndex < mWordListsCount) {
            chatMessageInfo.setStatus(App.MESSAGE_STATUS_SENDING);
            chatMessageInfo.setChatRoomID(chatRoomID);
            chatMessageInfo.setMessageType(App.MESSAGE_TYPE_CARD);
            chatMessageInfo.setMessageTitle(mWordsStudentEntityList.get(mListIndex).getEnglish());
            chatMessageInfo.setMessageContent(mWordsStudentEntityList.get(mListIndex).getChinese());
            chatMessageInfo.setMessageImgUrl(mWordsStudentEntityList.get(mListIndex).getImgurl());
            chatMessageInfo.setMessageVoiceUrl(mWordsStudentEntityList.get(mListIndex).getVoiceurl());
            chatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
            chatMessageInfo.setSendMobile(chatRoomID);
            chatMessageInfo.setSendName("贝贝");
            mPreviousWords = mWordsStudentEntityList.get(mListIndex);
            ++mListIndex;
            if (mListIndex == mWordListsCount) {
                ++mAllStudyCount;
                if (mAllStudyCount <= App.CORRECT_COUNT_PASS) {
                    mListIndex = 0;
                } else {
                    mIslast = true;
                }
            }
        } else {
            //学习完成
            chatMessageInfo.setStatus(App.MESSAGE_STATUS_SENDING);
            chatMessageInfo.setChatRoomID(chatRoomID);
            chatMessageInfo.setMessageType(App.MESSAGE_TYPE_CARD);
            chatMessageInfo.setMessageTitle("学习完成");
            chatMessageInfo.setMessageContent("");
            chatMessageInfo.setMessageImgUrl("");
            chatMessageInfo.setSendOrReciveFlag(App.RECEIVE_FLAG);
            chatMessageInfo.setSendMobile(chatRoomID);
            chatMessageInfo.setSendName("贝贝");
        }
        return chatMessageInfo;
    }

    private WordsStudentEntity getPreviousWords() {
        return mPreviousWords;
    }

    @Override
    public void updateStateWrong() {
        int wrongCount;
        if (getPreviousWords().getFirstpassdate() == null) {
            wrongCount = getPreviousWords().getFirstwrongcount();
            getPreviousWords().setFirstwrongcount(++wrongCount);
        } else if (getPreviousWords().getFirstpassdate() != null && getPreviousWords().getSecondpassdate() == null) {
            wrongCount = getPreviousWords().getSecondwrongcount();
            getPreviousWords().setSecondwrongcount(++wrongCount);
        } else if (getPreviousWords().getSecondpassdate() != null && getPreviousWords().getThirdpassdate() == null) {
            wrongCount = getPreviousWords().getThirdwrongcount();
            getPreviousWords().setThirdwrongcount(++wrongCount);
        } else if (getPreviousWords().getThirdpassdate() != null && getPreviousWords().getFourthpassdate() == null) {
            wrongCount = getPreviousWords().getFourthwrongcount();
            getPreviousWords().setFourthwrongcount(++wrongCount);
        } else {
            wrongCount = getPreviousWords().getFourthwrongcount();
            getPreviousWords().setFourthwrongcount(++wrongCount);
        }
        post();
    }

    @Override
    public void updateStateCorrect() {
        int CorrectCount;
        Date previousExerciseDate;
        Date now = new Date();
        if (getPreviousWords().getFirstpassdate() == null) {
            CorrectCount = getPreviousWords().getFirstcorrectcount();
            getPreviousWords().setFirstcorrectcount(++CorrectCount);
            previousExerciseDate = getPreviousWords().getFirstinserttime();
            if (DateUtils.truncatedEquals(now, DateUtils.addDays(previousExerciseDate, App.FIRST_EXERCISE_DAY), Calendar.DATE)) {
                getPreviousWords().setFirstpassdate(now);
            }
        } else if (getPreviousWords().getFirstpassdate() != null && getPreviousWords().getSecondpassdate() == null) {
            CorrectCount = getPreviousWords().getSecondcorrectcount();
            getPreviousWords().setSecondcorrectcount(++CorrectCount);
            if (CorrectCount > App.CORRECT_COUNT_PASS) {
                getPreviousWords().setSecondpassdate(now);
            }
        } else if (getPreviousWords().getSecondpassdate() != null && getPreviousWords().getThirdpassdate() == null) {
            CorrectCount = getPreviousWords().getThirdcorrectcount();
            getPreviousWords().setThirdcorrectcount(++CorrectCount);
            if (CorrectCount > App.CORRECT_COUNT_PASS) {
                getPreviousWords().setThirdpassdate(now);
            }
        } else if (getPreviousWords().getThirdpassdate() != null && getPreviousWords().getFourthpassdate() == null) {
            CorrectCount = getPreviousWords().getFourthcorrectcount();
            getPreviousWords().setFourthcorrectcount(++CorrectCount);
            if (CorrectCount > App.CORRECT_COUNT_PASS) {
                getPreviousWords().setFourthpassdate(now);
            }
        } else {
            CorrectCount = getPreviousWords().getFourthcorrectcount();
            getPreviousWords().setFourthcorrectcount(++CorrectCount);
        }
        post();
    }
    interface IsSameService {
        @GET("isSameWord")
        Observable<String> getEncourageList(@Header("token") String token
                , @Query("word") String page
                , @Query("speech") String limit);
    }
    @Override
    public void  isSame(final IsSameListener isSameListener, String word, String speech) {
        if(word.equals(speech)){
            isSameListener.onisSameComplete(true);
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlUtils.SERVER_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        IsSameService apiService = retrofit.create(IsSameService.class);

        rx.Observable<String> observable = apiService.getEncourageList(App.token,word,speech);
        if (observable == null) {
            return ;
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.print("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.print("");
                    }

                    @Override
                    public void onNext(String response) {
                        isSameListener.onisSameComplete(response.equals("true"));
                    }

                });

        return ;
    }


    private void post() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        jsonMessage = gson.toJson(getPreviousWords());
        GsonRequest<RespondStudy> gsonRequest = new GsonRequest<RespondStudy>(Request.Method.POST,
                UrlUtils.updateNowDayWordStateApiUrl(jsonMessage), RespondStudy.class,
                new Response.Listener<RespondStudy>() {
                    @Override
                    public void onResponse(RespondStudy respondStudy) {
                        if (respondStudy.getCode() == 0) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                header.put("token", App.token);
                return header;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonMessage.getBytes();
            }
        };
        VolleyUtils.addQueue(gsonRequest, "chatRequest");
    }

    @Override
    public boolean isLast() {
        return mIslast;
    }

}
