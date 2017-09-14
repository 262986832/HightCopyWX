package com.idisfkj.hightcopywx.chat.model;

import com.idisfkj.hightcopywx.beans.WordsEntity;

import java.util.List;

/**
 * Created by fvelement on 2017/9/13.
 */

public class RespondStudy {
    private int code;
    private List<WordsEntity> listWords;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<WordsEntity> getListWords() {
        return listWords;
    }

    public void setListWords(List<WordsEntity> listWords) {
        this.listWords = listWords;
    }
}
