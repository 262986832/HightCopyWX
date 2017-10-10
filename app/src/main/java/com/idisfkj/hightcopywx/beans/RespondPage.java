package com.idisfkj.hightcopywx.beans;

import com.idisfkj.hightcopywx.util.PageUtils;

/**
 * Created by fvelement on 2017/9/29.
 */

public class RespondPage {
    private int code;
    private String msg;
    private PageUtils page;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PageUtils getPage() {
        return page;
    }

    public void setPage(PageUtils page) {
        this.page = page;
    }
}
