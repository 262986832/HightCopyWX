package com.idisfkj.hightcopywx.util;

/**
 * Created by fvelement on 2017/9/21.
 */

import com.qiniu.android.common.Constants;
import com.qiniu.android.dns.util.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * GENERAL_PUNCTUATION 判断中文的“号
     * CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
     * HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
     *
     * @param c
     * @return
     */
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检查输入的单词是不是超过一个
     *
     * @param input 输入的字符串
     * @return true 如果输入的字符超过一个
     */
    public static boolean isMoreThanOneWord(String input) {
        String[] result1 = input.split(" ");
        String[] result2 = input.split(",");
        String[] result3 = input.split(".");
        String[] result4 = input.split("，");
        String[] result5 = input.split("。");
        String[] result6 = input.split("？");
        if (isMoreThanOne(result1) || isMoreThanOne(result2) || isMoreThanOne(result3) ||
                isMoreThanOne(result4) || isMoreThanOne(result5) || isMoreThanOne(result6)) {
            return true;
        }
        return false;
    }

    private static boolean isMoreThanOne(String[] result) {
        return result.length > 1;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidUrl(String input) {
        String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(input);
        if (m.find()) {
            return true;
        }
        return false;
    }
    public static boolean isValidNumeric(String str)
    {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    public static String join(Object[] array, String sep) {
        return join(array, sep, null);
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    public static String join(Collection list, String sep) {
        return join(list, sep, null);
    }

    /**
     * @see #join(Object[] array, String sep, String prefix)
     */
    public static String join(Collection list, String sep, String prefix) {
        Object[] array = list == null ? null : list.toArray();
        return join(array, sep, prefix);
    }

    /**
     * 以指定的分隔符来进行字符串元素连接
     * <p>
     * 例如有字符串数组array和连接符为逗号(,)
     * <code>
     * String[] array = new String[] { "hello", "world", "qiniu", "cloud","storage" };
     * </code>
     * 那么得到的结果是:
     * <code>
     * hello,world,qiniu,cloud,storage
     * </code>
     * </p>
     *
     * @param array  需要连接的对象数组
     * @param sep    元素连接之间的分隔符
     * @param prefix 前缀字符串
     * @return 连接好的新字符串
     */
    public static String join(Object[] array, String sep, String prefix) {
        if (array == null) {
            return "";
        }

        int arraySize = array.length;

        if (arraySize == 0) {
            return "";
        }

        if (sep == null) {
            sep = "";
        }

        if (prefix == null) {
            prefix = "";
        }

        StringBuilder buf = new StringBuilder(prefix);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(sep);
            }
            buf.append(array[i] == null ? "" : array[i]);
        }
        return buf.toString();
    }

    /**
     * 以json元素的方式连接字符串中元素
     * <p>
     * 例如有字符串数组array
     * <code>
     * String[] array = new String[] { "hello", "world", "qiniu", "cloud","storage" };
     * </code>
     * 那么得到的结果是:
     * <code>
     * "hello","world","qiniu","cloud","storage"
     * </code>
     * </p>
     *
     * @param array 需要连接的字符串数组
     * @return 以json元素方式连接好的新字符串
     */
    public static String jsonJoin(String[] array) {
        int arraySize = array.length;
        int bufSize = arraySize * (array[0].length() + 3);
        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(',');
            }

            buf.append('"');
            buf.append(array[i]);
            buf.append('"');
        }
        return buf.toString();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean inStringArray(String s, String[] array) {
        for (String x : array) {
            if (x.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] utf8Bytes(String data) {
        try {
            return data.getBytes(Constants.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static String utf8String(byte[] data) {
        try {
            return new String(data, Constants.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5Lower(String src) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(src.getBytes(Charset.forName("UTF-8")));
        byte[] md5Bytes = digest.digest();
        return Hex.encodeHexString(md5Bytes);
    }
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.trim().length() > 0));
    }
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.trim().length() == 0));
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


}
