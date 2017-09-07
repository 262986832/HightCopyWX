package com.idisfkj.hightcopywx.util;

import com.idisfkj.hightcopywx.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 请求api工具类
 * Created by idisfkj on 16/4/26.
 * Email : idisfkj@qq.com.
 */
public class UrlUtils {
    private static final String PACKAGE_NAME = "com.idisfkj.hightcopywx";
    private static final String REGID_API = "https://api.xmpush.xiaomi.com/v2/message/regid";
    private static final String REGISTRATION_ID = "&registration_id=";
    private static final String ACCOUNT_API = "https://api.xmpush.xiaomi.com/v2/message/user_account";
    private static final String USER_ACCOUNT = "&user_account=";
    private static final String TOPIC_API = "https://api.xmpush.xiaomi.com/v2/message/topic";
    private static final String TOPIC = "&topic=";
    private static final String ALL_API = "https://api.xmpush.xiaomi.com/v2/message/all";
    private static final String PAYLOAD = "?payload=";
    private static final String RESTRICTED_PACKAGE_NAME = "&restricted_package_name=";


    private static final String NOTIFY_TYPE = "&notify_type=2";
    private static final String PASS_THROUGH = "&pass_through=1";
    private static final String NOTIFY_ID = "&notify_id=0";
    //百度翻译
    private static final String BAIDU_TRANSLATE_API = "http://api.fanyi.baidu.com/api/trans/vip/translate?";
    public static final int ZHTOEN = 0;
    public static final int ENTOGH = 1;
    //服务端
    private static final String SERVER_REGISTER = "http://10.16.66.93:8001/api/";


    public UrlUtils() {
    }
    //百度翻译
    //http://api.fanyi.baidu.com/api/trans/vip/translate?
    // q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4
    public static String getBaiduTranslateApiUrl(String query, int type) {
        String salt = SignUtils.getRandomInt(10);
        String sign = SignUtils.getSign(BuildConfig.BAIDU_APP_ID, query, salt, BuildConfig.BAIDU_SCREAT_KEY);
        StringBuilder url = new StringBuilder(BAIDU_TRANSLATE_API);
        url.append("q=");
        url.append(query);
        if (type == ZHTOEN)
            url.append("&from=zh&to=en&appid=");
        else
            url.append("&from=en&to=zh&appid=");
        url.append(BuildConfig.BAIDU_APP_ID);
        url.append("&salt=");
        url.append(salt);
        url.append("&sign=");
        url.append(sign);
        String urlStr = url.toString();
        return urlStr;
    }
    //注册请求
    public static String getRegisterApiUrl(String userName, String mobile,String password) {
        StringBuilder url = new StringBuilder(SERVER_REGISTER+"register?");
        url.append("mobile=");
        url.append(mobile);
        url.append("&password=");
        url.append(password);
        url.append("&userName=");
        url.append(userName);
        return url.toString();
    }
    //登录请求
    public static String getLoginApiUrl(String mobile, String password,String clientid) {
        StringBuilder url = new StringBuilder(SERVER_REGISTER+"login?");
        url.append("mobile=");
        url.append(mobile);
        url.append("&password=");
        url.append(password);
        url.append("&clientid=");
        url.append(clientid);
        return url.toString();
    }

    /**
     * 聊天URl
     *
     * @param message
     * @param number
     * @param regId
     * @return
     */
    public static String chatAccountUrl(String message, String number, String regId) {

        String content = null;
        try {
            content = URLEncoder.encode(message + "(" + number + "@" +
                    SharedPreferencesManager.getString("regId", "") + "@" + SharedPreferencesManager.getString("userPhone", "") + "@" +
                    SharedPreferencesManager.getString("userName"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = ACCOUNT_API +
                PAYLOAD + content +
                RESTRICTED_PACKAGE_NAME + PACKAGE_NAME +
                USER_ACCOUNT + SharedPreferencesManager.getString("userPhone") +
                NOTIFY_TYPE +
                PASS_THROUGH +
                NOTIFY_ID;
        return url;
    }
    public static String chatTopicUrl(String message, String chatToMobile) {
        return  chatTopicUrl(message,chatToMobile,SharedPreferencesManager.getString("userPhone") );
    }

    public static String chatTopicUrl(String message, String chatToMobile, String topic) {

        String content = null;
        content = URLEncoder.encode(message);
        String url = TOPIC_API +
                PAYLOAD + content +
                RESTRICTED_PACKAGE_NAME + PACKAGE_NAME +
                TOPIC + topic +
                NOTIFY_TYPE +
                PASS_THROUGH +
                NOTIFY_ID;
        return url;
    }

    public static String chatUrl(String message, String number, String regId) {

        String content = null;
        try {
            content = URLEncoder.encode(message + "(" + number + "@" +
                    SharedPreferencesManager.getString("regId", "") + "@" + SharedPreferencesManager.getString("userPhone", "") + "@" +
                    SharedPreferencesManager.getString("userName"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = REGID_API +
                PAYLOAD + content +
                RESTRICTED_PACKAGE_NAME + PACKAGE_NAME +
                REGISTRATION_ID + regId +
                NOTIFY_TYPE +
                PASS_THROUGH +
                NOTIFY_ID;
        return url;
    }

    /**
     * 添加朋友Url
     *
     * @param regId
     * @return
     */
    public static String addFriendUrl(String regId) {
        String content = null;
        try {
            content = URLEncoder.encode(SharedPreferencesManager.getString("userName") + "&" + SharedPreferencesManager.getString("userPhone") + "@" +
                    SharedPreferencesManager.getString("regId", ""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = REGID_API +
                PAYLOAD + content +
                RESTRICTED_PACKAGE_NAME + PACKAGE_NAME +
                REGISTRATION_ID + regId +
                NOTIFY_TYPE +
                PASS_THROUGH +
                NOTIFY_ID;
        return url;
    }

    /**
     * 注册URl
     *
     * @param userName
     * @param number
     * @return
     */
    public static String registerUrl(String userName, String number) {
        String content = userName + "^" + SharedPreferencesManager.getString("regId", "") + "@" + number;
        try {
            content = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = ALL_API +
                PAYLOAD + content +
                RESTRICTED_PACKAGE_NAME + PACKAGE_NAME +
                NOTIFY_TYPE +
                PASS_THROUGH;
        return url;
    }
}
