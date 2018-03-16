package com.gongyunhao.nowmeeting.test;

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
 * Created by yuanlai on 2018/3/13.
 */

public class ChattingItem {

    public static final int LEFT = 1;

    public static final int RIGHT = 2;

    private String chattingMessage;

    private int viewType;

    private int pictureId;

    public void setPictureId(int pictureId){
        this.pictureId = pictureId;
    }

    public int getPictureId(){
        return pictureId;
    }

    public void setChattingMessage(String chattingMessage){
        this.chattingMessage = chattingMessage;
    }

    public String getChattingMessage(){
        return chattingMessage;
    }

    public void setViewType(int viewType){
        if (viewType == LEFT){
            this.viewType = LEFT;
        }else if (viewType == RIGHT){
            this.viewType = RIGHT;
        }
    }

    public int getViewType(){
        return viewType;
    }

}
