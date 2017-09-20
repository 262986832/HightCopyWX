package com.idisfkj.hightcopywx.beans;

import java.util.List;

/**
 * Created by fvelement on 2017/9/13.
 */

public class RespondStudy {
    private int code;
    private List<WordsStudentEntity> listWords;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<WordsStudentEntity> getListWords() {
        return listWords;
    }

    public void setListWords(List<WordsStudentEntity> listWords) {
        this.listWords = listWords;
    }
}
