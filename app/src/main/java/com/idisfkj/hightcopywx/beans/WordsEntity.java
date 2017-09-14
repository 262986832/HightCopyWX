package com.idisfkj.hightcopywx.beans;

import java.io.Serializable;

/**
 * Created by fvelement on 2017/9/13.
 */

public class WordsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //英文
    private String english;
    //中文
    private String chinese;
    //图片地址
    private String imgurl;
    //班级
    private Integer classes;

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
     * 设置：班级
     */
    public void setClasses(Integer classes) {
        this.classes = classes;
    }
    /**
     * 获取：班级
     */
    public Integer getClasses() {
        return classes;
    }
}
