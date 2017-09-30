package com.idisfkj.hightcopywx.beans;

import java.util.List;

/**
 * Created by fvelement on 2017/9/30.
 */

public class RespondPage1<T> {
    public int code;
    public String msg;
    public Page<T> page;

    public class Page<T> {
        //总记录数
        public int totalCount;
        //每页记录数
        public int pageSize;
        //总页数
        public int totalPage;
        //当前页数
        public int currPage;
        //列表数据
        public List<T> list;
    }
}
