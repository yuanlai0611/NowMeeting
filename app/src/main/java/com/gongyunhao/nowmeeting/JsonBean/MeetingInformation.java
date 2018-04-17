package com.gongyunhao.nowmeeting.JsonBean;
//    ┏┓　   ┏┓  
// ┏━━┛┻━━━━━┛┻ ┓ 
// ┃　　　　　　 ┃  
// ┃　　　━　    ┃  
// ┃　＞　　＜　 ┃  
// ┃　　　　　　 ┃  
// ┃... ⌒ ...  ┃  
// ┃　　　　　 　┃  
// ┗━━━┓　　　┏━┛  
//     ┃　　　┃　  
//     ┃　　　┃  
//     ┃　　　┃  神兽保佑  
//     ┃　　　┃  代码无bug　　  
//     ┃　　　┃  
//     ┃　　　┗━━━━━━━━━┓
//     ┃　　　　　　　    ┣┓
//     ┃　　　　         ┏┛
//     ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
//       ┃ ┫ ┫   ┃ ┫ ┫
//       ┗━┻━┛   ┗━┻━┛
//
//  作者：棒棒小糖
//  來源：简书
//

/**
 * Creste by GongYunHao on 2018/4/16
 */
public class MeetingInformation {

    private int id;

    private int categoryId;

    private String name;

    private int chatroomId;

    private int sponsorId;

    private int status;

    private String introduction;

    private String city;

    private String location;

    private String sponsorName;

    private String time;

    private int signedNumber;

    private int participatedNumber;

    private static final int TITLE = 1;

    private static final int MEETING = 2;

    private int viewType;

    private int meetingType;

    public static final int HOT = 3;

    public static final int FRIEND_PARTICIPATE = 4;

    public static final int PARTICIPATE = 5;

    private int meetingPictureId;

    public String getMeetingCity() {
        return meetingCity;
    }

    public void setMeetingCity(String meetingCity) {
        this.meetingCity = meetingCity;
    }

    private String meetingCity;

    public void setMeetingType(int meetingType){
        this.meetingType = meetingType;
    }

    public int getMeetingType(){
        return meetingType;
    }

    public void setMeetingPictureId(int meetingPictureId){
        this.meetingPictureId = meetingPictureId;
    }

    public int getMeetingPictureId(){
        return meetingPictureId;
    }

    public int getViewType(){
        return viewType;
    }

    public void setViewType(int viewType){
        if (viewType == TITLE){
            this.viewType = TITLE;
        }else if (viewType == MEETING){
            this.viewType = MEETING;
        }
    }


    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSignedNumber() {
        return signedNumber;
    }

    public void setSignedNumber(int signedNumber) {
        this.signedNumber = signedNumber;
    }

    public int getParticipatedNumber() {
        return participatedNumber;
    }

    public void setParticipatedNumber(int participatedNumber) {
        this.participatedNumber = participatedNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    private String photo;

    private String link;

    private int createTime;

    private int updateTime;


}
