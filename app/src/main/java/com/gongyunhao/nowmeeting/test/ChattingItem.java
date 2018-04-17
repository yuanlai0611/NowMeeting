package com.gongyunhao.nowmeeting.test;

import android.graphics.Bitmap;

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

    public static final int LEFT_VOTE = 3;

    public static final int RIGHT_VOTE = 4;

    public static final int LEFT_LOTTERY = 5;

    public static final int RIGHT_LOTTERY = 6;

    public static final int LEFT_PHOTO = 7;

    public static final int RIGHT_PHOTO = 8;

    private String chattingMessage;

    private int viewType;

    private int pictureId;

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }



    private String photoPath;

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

        this.viewType = viewType;

    }

    public int getViewType(){
        return viewType;
    }

}
