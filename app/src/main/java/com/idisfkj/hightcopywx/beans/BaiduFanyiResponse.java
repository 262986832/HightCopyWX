package com.idisfkj.hightcopywx.beans;

import java.util.List;

/**
 * Created by fvelement on 2017/9/4.
 */

public class BaiduFanyiResponse {
    private String from;
    private String to;
    private List<Result> trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Result> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<Result> trans_result) {
        this.trans_result = trans_result;
    }

    public class Result{
        private String src;
        private String dst;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }
    }
}
