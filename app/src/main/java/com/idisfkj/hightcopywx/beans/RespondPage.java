package com.idisfkj.hightcopywx.beans;

import com.idisfkj.hightcopywx.util.PageUtils;

/**
 * Created by fvelement on 2017/9/29.
 */

public class RespondPage<T> {
    private int code;
    private String msg;
    private PageUtils<T> page;

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

    public PageUtils<T> getPage() {
        return page;
    }

    public void setPage(PageUtils<T> page) {
        this.page = page;
    }
}
