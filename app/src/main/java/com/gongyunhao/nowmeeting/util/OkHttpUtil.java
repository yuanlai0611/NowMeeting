package com.gongyunhao.nowmeeting.util;

import java.io.IOException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yuanyuanlai on 2018/3/8.
 */

public class OkHttpUtil {

    private static OkHttpUtil mOkHttpUtil;
    private OkHttpClient mClient;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private OkHttpUtil(){
        mClient = new OkHttpClient();
    }

    public synchronized static OkHttpUtil getInstance() {
        if ( mOkHttpUtil== null) {
            mOkHttpUtil = new OkHttpUtil();
        }
        return mOkHttpUtil;
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

    public Response getCreateMeetingResponse(String sponsorId,String sponsorName,String conferenceName,String introduction,String city,String location,String time,String photo,String url) throws IOException{

        RequestBody requestBody = new FormBody.Builder()
                .add("sponsorId",sponsorId)
                .add("sponsorName",sponsorName)
                .add("conferenceName",conferenceName)
                .add("introduction",introduction)
                .add("city",city)
                .add("location",location)
                .add("time",time)
                .add("photo",photo)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = mClient.newCall(request).execute();
        return  response;

    }

    public String getInfo(String name,String url) throws IOException{

        Request request = new Request.Builder()
                .url(url+"?username="+name)
                .get()
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();

    }

    public Response getMeetingInfoResponse(String name,String url) throws IOException{

        Request request = new Request.Builder()
                .url(url+"?conferenceId="+name)
                .get()
                .build();
        Response response = mClient.newCall(request).execute();

        return response;

    }

}
