package com.idisfkj.hightcopywx.find.model;

/**
 * Created by fvelement on 2017/9/29.
 */

public class EncourageEntity {
     //
    public Integer id;
    //激励图
    public String encourageimgurl;
    //激励内容
    public String encouragetext;
    //激励标题
    public String encouragetitle;
    //第几天
    public Integer dayth;

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
     * 设置：激励图
     */
    public void setEncourageimgurl(String encourageimgurl) {
        this.encourageimgurl = encourageimgurl;
    }
    /**
     * 获取：激励图
     */
    public String getEncourageimgurl() {
        return encourageimgurl;
    }
    /**
     * 设置：激励内容
     */
    public void setEncouragetext(String encouragetext) {
        this.encouragetext = encouragetext;
    }
    /**
     * 获取：激励内容
     */
    public String getEncouragetext() {
        return encouragetext;
    }
    /**
     * 设置：激励标题
     */
    public void setEncouragetitle(String encouragetitle) {
        this.encouragetitle = encouragetitle;
    }
    /**
     * 获取：激励标题
     */
    public String getEncouragetitle() {
        return encouragetitle;
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