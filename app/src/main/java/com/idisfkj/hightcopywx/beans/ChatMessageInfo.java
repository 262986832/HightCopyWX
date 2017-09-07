package com.idisfkj.hightcopywx.beans;

import java.io.Serializable;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public class ChatMessageInfo implements Serializable {
    private String ownMobile;
    private String chatRoomID;
    private int sendOrReciveFlag;
    private int messageType;
    private String messageTitle;
    private String messageContent;
    private String messageImgUrl;
    private String messageVoiceUrl;
    private String time;
    private String sendMobile;

    public ChatMessageInfo() {

    }

    public ChatMessageInfo(String ownMobile, String chatRoomID, int sendOrReciveFlag, int messageType, String messageTitle, String messageContent, String messageImgUrl, String messageVoiceUrl, String time, String sendMobile) {
        this.ownMobile = ownMobile;
        this.chatRoomID = chatRoomID;
        this.sendOrReciveFlag = sendOrReciveFlag;
        this.messageType = messageType;
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.messageImgUrl = messageImgUrl;
        this.messageVoiceUrl = messageVoiceUrl;
        this.time = time;
        this.sendMobile = sendMobile;
    }

    public String getOwnMobile() {
        return ownMobile;
    }

    public void setOwnMobile(String ownMobile) {
        this.ownMobile = ownMobile;
    }

    public String getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(String chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public int getSendOrReciveFlag() {
        return sendOrReciveFlag;
    }

    public void setSendOrReciveFlag(int sendOrReciveFlag) {
        this.sendOrReciveFlag = sendOrReciveFlag;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageImgUrl() {
        return messageImgUrl;
    }

    public void setMessageImgUrl(String messageImgUrl) {
        this.messageImgUrl = messageImgUrl;
    }

    public String getMessageVoiceUrl() {
        return messageVoiceUrl;
    }

    public void setMessageVoiceUrl(String messageVoiceUrl) {
        this.messageVoiceUrl = messageVoiceUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }
}
