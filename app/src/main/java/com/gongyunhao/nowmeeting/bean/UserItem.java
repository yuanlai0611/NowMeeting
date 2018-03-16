package com.gongyunhao.nowmeeting.bean;

/**
 * _oo0oo_
 * 08888888o
 * 88" . "88
 * (| -_- |)
 * 0\  =  /0
 * <p>
 * 佛祖保佑代码无bug
 * <p>
 * <p>
 * Created by yuanlai on 2018/3/15.
 */

public class UserItem {

    private int userPictureId;

    private String userName;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserPictureId(int userPictureId){
        this.userPictureId = userPictureId;
    }

    public int getUserPictureId(){
        return userPictureId;
    }
}

