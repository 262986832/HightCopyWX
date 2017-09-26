package com.idisfkj.hightcopywx.beans;

/**
 * Created by fvelement on 2017/9/5.
 */

public class RespondLogin {
    private int code;
    private String expire;
    private String token;
    private String msg;
    private String userName;
    private String headUploadToken;
    private String voiceUploadToken;

    public String getHeadUploadToken() {
        return headUploadToken;
    }

    public void setHeadUploadToken(String headUploadToken) {
        this.headUploadToken = headUploadToken;
    }

    public String getVoiceUploadToken() {
        return voiceUploadToken;
    }

    public void setVoiceUploadToken(String voiceUploadToken) {
        this.voiceUploadToken = voiceUploadToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
