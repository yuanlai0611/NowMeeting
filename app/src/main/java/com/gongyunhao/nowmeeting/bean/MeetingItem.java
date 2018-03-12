package com.gongyunhao.nowmeeting.bean;

/**
 * Created by yuanyuanlai on 2018/3/10.
 */

public class MeetingItem {


    private static final int TITLE = 1;

    private static final int MEETING = 2;

    private String meetingName;

    private int viewType;

    private String titleName;



    //这个不需要，暂时测试用

    private int meetingPictureId;

    private String startTime;

    private userItem []users;

    private String []picturesAboutMeeting;

    private String meetingDescription;

    public String getMeetingName(){
        return meetingName;
    }

    public String getMeetingDescription(){
        return meetingDescription;
    }

    public void setMeetingName(String meetingName){
        this.meetingName = meetingName;
    }

    public void setMeetingDescription(String meetingDescription){
        this.meetingDescription = meetingDescription;
    }

    public int getViewType(){
        return viewType;
    }

    public String getTitleName(){
        return titleName;
    }

    public void setTitleName(String titleName){
        this.titleName = titleName;
    }

    public void setViewType(int viewType){
        if (viewType == TITLE){
            this.viewType = TITLE;
        }else if (viewType == MEETING){
            this.viewType = MEETING;
        }
    }

}
