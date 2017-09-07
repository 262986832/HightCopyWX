package com.idisfkj.hightcopywx.beans;

/**
 * Created by idisfkj on 16/4/22.
 * Email : idisfkj@qq.com.
 */
public class ChatRoomItemInfo {
    private String ownMobile;
    private String chatRoomID;
    private String imgUrl;
    private String title;
    private String content;
    private String time;
    private int unReadNumber;
    private int chatType;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUnReadNumber() {
        return unReadNumber;
    }

    public void setUnReadNumber(int unReadNumber) {
        this.unReadNumber = unReadNumber;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }
}
