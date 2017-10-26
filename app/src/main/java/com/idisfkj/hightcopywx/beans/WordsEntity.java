package com.idisfkj.hightcopywx.beans;

import java.io.Serializable;

/**
 * Created by fvelement on 2017/9/13.
 */

public class WordsEntity implements Serializable {
    //
    private Integer id;
    //英文
    private String english;
    //中文
    private String chinese;
    //图片地址
    private String imgurl;
    //声音地址
    private String voiceurl;
    //图书编号
    private Integer bookid;
    //第几天
    private Integer dayth;

    public String getVoiceurl() {
        return voiceurl;
    }

    public void setVoiceurl(String voiceurl) {
        this.voiceurl = voiceurl;
    }

    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：英文
     */
    public void setEnglish(String english) {
        this.english = english;
    }
    /**
     * 获取：英文
     */
    public String getEnglish() {
        return english;
    }
    /**
     * 设置：中文
     */
    public void setChinese(String chinese) {
        this.chinese = chinese;
    }
    /**
     * 获取：中文
     */
    public String getChinese() {
        return chinese;
    }
    /**
     * 设置：图片地址
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    /**
     * 获取：图片地址
     */
    public String getImgurl() {
        return imgurl;
    }
    /**
     * 设置：图书编号
     */
    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }
    /**
     * 获取：图书编号
     */
    public Integer getBookid() {
        return bookid;
    }
    /**
     * 设置：第几天
     */
    public void setDayth(Integer dayth) {
        this.dayth = dayth;
    }
    /**
     * 获取：第几天
     */
    public Integer getDayth() {
        return dayth;
    }
}
