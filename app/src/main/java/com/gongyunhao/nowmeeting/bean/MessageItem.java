package com.gongyunhao.nowmeeting.bean;

import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by yuanyuanlai on 2018/3/9.
 */

public class MessageItem {

    private Conversation conversation;

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage(){
        return message;
    }

    public String getDate(){
        return date;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setImageId(int imageId){
        this.imageId = imageId;
    }

    public int getImageId(){
        return imageId;
    }

    private int imageId;

    private String userName;

    private String message;

    private String date;

    private int messageType;

    public final static int SINGLE = 1;

    public final static int GROUP =2;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

}
