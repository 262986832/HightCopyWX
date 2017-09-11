package com.idisfkj.hightcopywx.xiaomiPush;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.ChatRoomItemInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.dao.ChatRoomsDataHelper;
import com.idisfkj.hightcopywx.util.SharedPreferencesManager;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 小米推送信息接受
 * Created by idisfkj on 16/4/23.
 * Email : idisfkj@qq.com.
 */
public class XiaoMiMessageReceiver extends PushMessageReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;
    private ChatMessageDataHelper chatHelper;
    private Intent intent;
    private NotificationManager manager;

    /**
     * 接收服务器发送的透传消息
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        Gson gson = new Gson();
        ChatMessageInfo chatMessageInfo=gson.fromJson(mMessage,ChatMessageInfo.class);
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount=message.getUserAccount();
        }
        //发送给聊天处理线程
        EventBus.getDefault().post(chatMessageInfo);

    }

    /**
     * 接收服务器发来的通知栏消息
     * (用户点击通知栏触发)
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
    }

    /**
     * 接收服务器发来的通知栏消息
     * （消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息）
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
    }

    /**
     * 接收客户端向服务器发送命令消息后返回的响应
     *
     * @param context
     * @param message
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    /**
     * 接受客户端向服务器发送注册命令消息后返回的响应
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }
        SharedPreferencesManager.putString("regId", mRegId).commit();
    }



    public void chatInformation(String message) {
        // chat information
        int index1 = message.lastIndexOf("(");
        String rMessage = message.substring(0, index1);
        String extraMessage = message.substring(index1 + 1);

        int index2 = extraMessage.indexOf("@");
        int index3 = extraMessage.indexOf("@", index2 + 1);
        int index4 = extraMessage.indexOf("@", index3 + 1);
        String receiverNumber = extraMessage.substring(0, index2);
        String regId = extraMessage.substring(index2 + 1, index3);
        String sendNumber = extraMessage.substring(index3 + 1, index4);
        String userName = extraMessage.substring(index4 + 1);

        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();

        if (App.mNowChatRoomID.equals(sendNumber) ) {
            //在当前聊天界面
            intent.setAction("com.idisfkj.hightcopywx.chat");
            Bundle bundle = new Bundle();
            bundle.putSerializable("chatMessageInfo", chatMessageInfo);
            bundle.putString("message", rMessage);
            intent.putExtras(bundle);
            App.getAppContext().sendBroadcast(intent);
        } else {
            //不在当前聊天界面

            chatHelper.insert(chatMessageInfo);

            SharedPreferencesManager.putInt("unReadNum", SharedPreferencesManager.getInt("unReadNum") + 1).commit();
            ChatRoomsDataHelper wxHelper = new ChatRoomsDataHelper(App.getAppContext());
/*
            Cursor cursor = wxHelper.query(sendNumber, regId, userName);
            int unReadNum = 0;
            int _id = 0;
            if (cursor.moveToFirst()) {
                //更新目标未读消息数
                unReadNum = CursorUtils.formatInt(cursor, ChatRoomsDataHelper.WXItemDataInfo.UNREAD_NUM);
                _id = CursorUtils.formatInt(cursor, ChatRoomsDataHelper.WXItemDataInfo._ID);
                wxHelper.update(++unReadNum, regId, sendNumber);
                cursor.close();
            }

            //更新main未读消息数
            intent.setAction("com.idisfkj.hightcopywx.main");
            App.getAppContext().sendBroadcast(intent);

            wxHelper.update(rMessage, CalendarUtils.getCurrentDate(), regId, sendNumber);

            if (isApp2Background()) {
                manager = (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent = new Intent(App.getAppContext(), MainActivity.class);
                //防止开启重复的Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = new Bundle();
                //防止pendingIntent相同
                intent.setData(Uri.parse("message://" + regId));
                bundle.putInt("_id", _id);
                intent.putExtras(bundle);
                PendingIntent pi = PendingIntent.getActivity(App.getAppContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                Notification notification = new Notification.Builder(App.getAppContext())
                        .setSmallIcon(R.drawable.icon)
                        .setAutoCancel(true)
                        .setTicker("高仿微信有新消息")
                        .setContentTitle(userName)
                        .setContentText("[" + unReadNum + "条] " + rMessage)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                        .setContentIntent(pi)
                        .setWhen(System.currentTimeMillis())
                        .build();
                manager.notify(_id, notification);
            }*/
        }
    }

    public void addFriendInformation(String message) {
        int index1 = message.indexOf("&");
        int index2 = message.indexOf("@");
        String userName = message.substring(0, index1);
        String number = message.substring(index1 + 1, index2);
        String regId = message.substring(index2 + 1);
        String currentAccount = SharedPreferencesManager.getString("userPhone");
        ChatRoomItemInfo itemInfo = new ChatRoomItemInfo();
        /*
        itemInfo.setTitle(userName);
        itemInfo.setMobile(number);
        itemInfo.setRegId(regId);
        itemInfo.setContent(String.format(App.HELLO_MESSAGE, number));
        itemInfo.setChattomobile(currentAccount);
        itemInfo.setTime(CalendarUtils.getCurrentDate());
        ChatRoomsDataHelper wxHelper = new ChatRoomsDataHelper(App.getAppContext());
        //默认添加朋友请求
        wxHelper.insert(itemInfo);

        //插入系统消息
        ChatMessageInfo chatInfo = new ChatMessageInfo();
        chatHelper.insert(chatInfo);*/
    }

    public boolean isApp2Background() {
        ActivityManager manager = (ActivityManager) App.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo infos : processInfos) {
            if (infos.processName.equals(App.getAppContext().getPackageName())) {
                if (infos.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
