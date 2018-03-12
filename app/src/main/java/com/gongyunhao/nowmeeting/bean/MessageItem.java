package com.gongyunhao.nowmeeting.bean;

/**
 * Created by yuanyuanlai on 2018/3/9.
 */

public class MessageItem {

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



}
