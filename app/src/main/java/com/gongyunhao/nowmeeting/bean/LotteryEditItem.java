package com.gongyunhao.nowmeeting.bean;

public class LotteryEditItem {

    private String lotteryType;

    private String prize;

    private String peopleNumber;

    public static final int Header = 1;

    public static final int Item = 2;

    public int getLotteryItemType() {
        return lotteryItemType;
    }

    public void setLotteryItemType(int lotteryItemType) {
        this.lotteryItemType = lotteryItemType;
    }

    private int lotteryItemType;

    public void setLotteryType(String lotteryType){
        this.lotteryType = lotteryType;
    }

    public void setPrize(String prize){
        this.prize = prize;
    }

    public void setPeopleNumber(String peopleNumber){
        this.peopleNumber = peopleNumber;
    }

    public String getLotteryType(){
        return lotteryType;
    }

    public String getPrize(){
        return prize;
    }

    public String getPeopleNumber(){
        return peopleNumber;
    }

}
