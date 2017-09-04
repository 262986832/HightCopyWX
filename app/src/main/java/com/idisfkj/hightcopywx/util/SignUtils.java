package com.idisfkj.hightcopywx.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by fvelement on 2017/9/4.
 */

public class SignUtils {
    public static String getSign(String appid, String q, String salt, String key) {
        try {
            q = new String(q.getBytes(), "UTF-8");
            return stringToMD5(new StringBuilder(appid).append(q).append(salt).append(key).toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String stringToMD5(String str) {

        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            return toHexString(mdTemp.digest());
        } catch (Exception e) {
            return null;
        }
    }

    public static String toHexString(byte[] md) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        int j = md.length;
        char str[] = new char[j * 2];
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[2 * i] = hexDigits[byte0 >>> 4 & 0xf];
            str[i * 2 + 1] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }


    public static String getRandomInt(int length) { //length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
