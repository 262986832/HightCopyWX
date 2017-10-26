package com.idisfkj.hightcopywx.util;

import android.content.Context;
import android.os.Environment;

import com.idisfkj.hightcopywx.chat.view.ISpeechView;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * Created by fvelement on 2017/8/17.
 */

public class SpeechRecognizerService {
    //语音识别
    //语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    private ISpeechView iSpeechView;
    public String lag = "en_us";
    public static final String mVoicePath=Environment.getExternalStorageDirectory() + "/msc/iat.wav";
    public void attachView(ISpeechView view) {
        this.iSpeechView = view;
    }

    public SpeechRecognizerService(Context context) {
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(context, mInitListener);
        setParam("zh_cn");
    }

    public SpeechRecognizerService(Context context,ISpeechView iSpeechView) {
        this.iSpeechView =iSpeechView;
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(context, mInitListener);
        setParam("zh_cn");
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                iSpeechView.onSpeechRecognizerError("语音识别初始化失败：" + code);
            }
        }
    };

    public void setParam(String lag) {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //String lag = "mandarin";
        //String lag = "en_us";
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        mIatVoicePath = Environment.getExternalStorageDirectory().getPath();
//        mIatVoicePath = String.format("%s/%s.wav",mIatVoicePath,new Date().getTime());
//        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, mIatVoicePath);
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, mVoicePath);

    }

    public void startSpeechRecognizer() {
        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            String re = results.getResultString();
            String text = JsonParser.parseIatResult(results.getResultString());
            iSpeechView.onSpeechRecognizerComplete(text);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            iSpeechView.onSpeechRecognizerError(error.getMessage());
        }
    };

}
