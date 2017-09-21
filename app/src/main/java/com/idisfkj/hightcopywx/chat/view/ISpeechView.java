package com.idisfkj.hightcopywx.chat.view;

/**
 * Created by fvelement on 2017/9/21.
 */

public interface ISpeechView {
    void onSpeechRecognizerComplete(String string);
    void onSpeechRecognizerError(String string);
}
