package com.idisfkj.hightcopywx;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.idisfkj.hightcopywx.find.model.EncourageEntity;
import com.idisfkj.hightcopywx.injection.components.AppComponent;
import com.idisfkj.hightcopywx.injection.components.DaggerAppComponent;
import com.idisfkj.hightcopywx.injection.modules.AppModule;
import com.iflytek.cloud.SpeechUtility;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by idisfkj on 16/4/21.
 * Email : idisfkj@qq.com.
 */
public class App extends Application{
    private static Context mContext;
    public static final String APP_ID = "2882303761517464903";
    public static final String APP_KEY = "5631746467903";
    public static final String APP_SECRET_KEY = "HxMA7STSUQMLEiDX+zo+5A==";
    public static final String TAG = "com.idisfkj.hightcopywx";
    public static final int CHAT_TYPE_CHAT=0;
    public static final int CHAT_TYPE_PRACTISE=1;
    public static final int CHAT_TYPE_ENGLISH_STUDY=2;
    public static final int CHAT_TYPE_TRANSLATE_ENZH=3;
    public static final int SEND_FLAG=1;
    public static final int RECEIVE_FLAG=0;
    public static final int MESSAGE_TYPE_TEXT=1;
    public static final int MESSAGE_TYPE_VOICE=2;
    public static final int MESSAGE_TYPE_IMG=3;
    public static final int MESSAGE_TYPE_CARD=4;
    public static final int MESSAGE_TYPE_VIDEO=5;
    public static final int MESSAGE_STATUS_SUCCESS=0;
    public static final int MESSAGE_STATUS_FAIL=1;
    public static final int MESSAGE_STATUS_SENDING=2;
    public static final String VOICE_URL="http://oww4rwkcc.bkt.clouddn.com/";
    public static final String BOOK_VOICE_URL="http://oxnbp01a8.bkt.clouddn.com/";
    public static final String BOOK_IMG_URL="http://ov66bzns1.bkt.clouddn.com/";
    //生词需要练习几天后，继续联系
    public static final int FIRST_EXERCISE_DAY=3;
    //正确几次后，允许通过
    public static final int CORRECT_COUNT_PASS=5;
    public static String mNowChatRoomID = "-1";
    public static String ownMobile = "-1";
    public static String token = "";
    public static String headUploadToken = "";
    public static String voiceUploadToken = "";
    public static String userName = "宝贝";
    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    public static List<EncourageEntity> encourageEntityList;
    private static AppComponent mAppComponent;
    private static App instance;

    public static App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        mContext = getApplicationContext();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);

        sp = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        //初始语音识别
        SpeechUtility.createUtility(this, "appid="+BuildConfig.XUNFEI_SCREAT_KEY);
        //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }

        //小米推送调试日记
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

    }


    public  AppComponent getAppComponent() {
        return this.mAppComponent;
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static Context getAppContext(){
        return mContext;
    }
}
