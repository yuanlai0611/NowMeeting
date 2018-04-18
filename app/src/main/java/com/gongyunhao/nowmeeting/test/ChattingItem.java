package com.gongyunhao.nowmeeting.test;

import android.graphics.Bitmap;

import java.util.List;

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

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    private List<String> nameList;

    private Bitmap bitmap;

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    private String lotteryName;

    public String getLotteryNumber() {
        return lotteryNumber;
    }

    public void setLotteryNumber(String lotteryNumber) {
        this.lotteryNumber = lotteryNumber;
    }

    private String lotteryNumber;

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
