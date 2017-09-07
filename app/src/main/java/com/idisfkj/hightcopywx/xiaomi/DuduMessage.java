package com.idisfkj.hightcopywx.xiaomi;

/**
 * Created by fvelement on 2017/9/6.
 */

public class DuduMessage {
    private int messageType;
    private String message;
    private String chatToMobile;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatToMobile() {
        return chatToMobile;
    }

    public void setChatToMobile(String chatToMobile) {
        this.chatToMobile = chatToMobile;
    }

}
