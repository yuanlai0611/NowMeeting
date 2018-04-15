package com.gongyunhao.nowmeeting.JsonBean;

public class MeetingCreate {

    private int status;

    private String message;

    private int data;

    private boolean success;

    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setData(int data){
        this.data = data;
    }
    public int getData(){
        return this.data;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }


}
