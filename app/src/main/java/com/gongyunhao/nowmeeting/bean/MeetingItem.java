package com.gongyunhao.nowmeeting.bean;

import java.util.List;

/**
 * Created by yuanyuanlai on 2018/3/10.
 */

public class MeetingItem {

    private static final int TITLE = 1;

    private static final int MEETING = 2;

    private int viewType;

    private int meetingType;

    public static final int HOT = 3;

    public static final int FRIEND_PARTICIPATE = 4;

    public static final int PARTICIPATE = 5;

    private int meetingPictureId;


    private String meetingName;
    private String meetingDate;

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

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

    public void setMeetingName(String meetingName){
        this.meetingName = meetingName;
    }

    public String getMeetingName(){
        return meetingName;
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

}
