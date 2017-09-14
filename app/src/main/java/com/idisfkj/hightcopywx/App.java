package com.idisfkj.hightcopywx;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    public static final String DEVELOPER_ID = "/f6ukNwIPpdSmkrgsmklcMbW6WefG01XkxdILDNEUVw=";
    public static final String DEVELOPER_MOBILE = "15300112238";
    public static final String COMPLAIN_PROPOSE = "投诉与建议";
    public static final String COMPLAIN_PROPOSE_MESSAGE = "欢迎注册高仿微信App,如有问题可以在此留言与我。";
    public static final String HELLO_MESSAGE = "你已添加了%s，现在可以开始聊天了";
    public static final String UNREADNUM = "unReadNum";
    public static final int CHAT_TYPE_CHAT=0;
    public static final int CHAT_TYPE_ENGLISHTOCHINESE=1;
    public static final int CHAT_TYPE_CHINESETOENGLISH=2;
    public static final int CHAT_TYPE_ENGLISH_STUDY=3;
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
    public static String mNowChatRoomID = "-1";
    public static String ownMobile = "-1";
    public static String token = "";
    public static String userName = "宝贝";
    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        sp = getSharedPreferences("userInfo",Context.MODE_PRIVATE);

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
