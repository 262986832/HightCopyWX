package com.idisfkj.hightcopywx.beans;

import java.util.Date;

/**
 * Created by fvelement on 2017/9/20.
 */

public class WordsStudentEntity {
    //
    private Integer id;
    private Integer wordid;
    //第几天
    private Integer dayth;
    //英文
    private String english;
    //中文
    private String chinese;
    //图片地址
    private String imgurl;
    //图书编号
    private Integer bookid;
    //拥有者
    private String ownmobile;
    //第一次插入日期
    private Date firstinserttime;
    //第一次正确数目
    private Integer firstcorrectcount;
    //第一次错误数目
    private Integer firstwrongcount;
    //第一次通过日期
    private Date firstpassdate;
    //第二次正确数目
    private Integer secondcorrectcount;
    //第二次错误数目
    private Integer secondwrongcount;
    //第二次通过日期
    private Date secondpassdate;
    //第三次正确数目
    private Integer thirdcorrectcount;
    //第三次错误数目
    private Integer thirdwrongcount;
    //第三次通过日期
    private Date thirdpassdate;
    //第四次正确数目
    private Integer fourthcorrectcount;
    //第四次错误数目
    private Integer fourthwrongcount;
    //第四次通过日期
    private Date fourthpassdate;

    public Integer getWordid() {
        return wordid;
    }

    public void setWordid(Integer wordid) {
        this.wordid = wordid;
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
     * 设置：拥有者
     */
    public void setOwnmobile(String ownmobile) {
        this.ownmobile = ownmobile;
    }
    /**
     * 获取：拥有者
     */
    public String getOwnmobile() {
        return ownmobile;
    }
    /**
     * 设置：第一次插入日期
     */
    public void setFirstinserttime(Date firstinserttime) {
        this.firstinserttime = firstinserttime;
    }
    /**
     * 获取：第一次插入日期
     */
    public Date getFirstinserttime() {
        return firstinserttime;
    }
    /**
     * 设置：第一次正确数目
     */
    public void setFirstcorrectcount(Integer firstcorrectcount) {
        this.firstcorrectcount = firstcorrectcount;
    }
    /**
     * 获取：第一次正确数目
     */
    public Integer getFirstcorrectcount() {
        return firstcorrectcount;
    }
    /**
     * 设置：第一次错误数目
     */
    public void setFirstwrongcount(Integer firstwrongcount) {
        this.firstwrongcount = firstwrongcount;
    }
    /**
     * 获取：第一次错误数目
     */
    public Integer getFirstwrongcount() {
        return firstwrongcount;
    }
    /**
     * 设置：第一次通过日期
     */
    public void setFirstpassdate(Date firstpassdate) {
        this.firstpassdate = firstpassdate;
    }
    /**
     * 获取：第一次通过日期
     */
    public Date getFirstpassdate() {
        return firstpassdate;
    }
    /**
     * 设置：第二次正确数目
     */
    public void setSecondcorrectcount(Integer secondcorrectcount) {
        this.secondcorrectcount = secondcorrectcount;
    }
    /**
     * 获取：第二次正确数目
     */
    public Integer getSecondcorrectcount() {
        return secondcorrectcount;
    }
    /**
     * 设置：第二次错误数目
     */
    public void setSecondwrongcount(Integer secondwrongcount) {
        this.secondwrongcount = secondwrongcount;
    }
    /**
     * 获取：第二次错误数目
     */
    public Integer getSecondwrongcount() {
        return secondwrongcount;
    }
    /**
     * 设置：第二次通过日期
     */
    public void setSecondpassdate(Date secondpassdate) {
        this.secondpassdate = secondpassdate;
    }
    /**
     * 获取：第二次通过日期
     */
    public Date getSecondpassdate() {
        return secondpassdate;
    }
    /**
     * 设置：第三次正确数目
     */
    public void setThirdcorrectcount(Integer thirdcorrectcount) {
        this.thirdcorrectcount = thirdcorrectcount;
    }
    /**
     * 获取：第三次正确数目
     */
    public Integer getThirdcorrectcount() {
        return thirdcorrectcount;
    }
    /**
     * 设置：第三次错误数目
     */
    public void setThirdwrongcount(Integer thirdwrongcount) {
        this.thirdwrongcount = thirdwrongcount;
    }
    /**
     * 获取：第三次错误数目
     */
    public Integer getThirdwrongcount() {
        return thirdwrongcount;
    }
    /**
     * 设置：第三次通过日期
     */
    public void setThirdpassdate(Date thirdpassdate) {
        this.thirdpassdate = thirdpassdate;
    }
    /**
     * 获取：第三次通过日期
     */
    public Date getThirdpassdate() {
        return thirdpassdate;
    }
    /**
     * 设置：第四次正确数目
     */
    public void setFourthcorrectcount(Integer fourthcorrectcount) {
        this.fourthcorrectcount = fourthcorrectcount;
    }
    /**
     * 获取：第四次正确数目
     */
    public Integer getFourthcorrectcount() {
        return fourthcorrectcount;
    }
    /**
     * 设置：第四次错误数目
     */
    public void setFourthwrongcount(Integer fourthwrongcount) {
        this.fourthwrongcount = fourthwrongcount;
    }
    /**
     * 获取：第四次错误数目
     */
    public Integer getFourthwrongcount() {
        return fourthwrongcount;
    }
    /**
     * 设置：第四次通过日期
     */
    public void setFourthpassdate(Date fourthpassdate) {
        this.fourthpassdate = fourthpassdate;
    }
    /**
     * 获取：第四次通过日期
     */
    public Date getFourthpassdate() {
        return fourthpassdate;
    }
}
