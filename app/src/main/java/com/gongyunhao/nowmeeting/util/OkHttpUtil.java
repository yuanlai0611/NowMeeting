package com.gongyunhao.nowmeeting.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Callback;
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

        RequestBody requestBody = new FormBody.Builder()
                .add("username",name)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();

    }

    public String getSignInfo(String remark_name,String password,String phone_number,String email,
                              String user_address, String company,String signature,String workplace)throws IOException{

        RequestBody requestBody=new FormBody.Builder()
                .add( "username",remark_name )
                .add( "password",password )
                .add( "telephone",phone_number )
                .add( "email",email )
                .add( "avatar","null" )
                .add( "address",user_address )
                .add( "signature",signature )
                .add( "graduateSchool",company )
                .add( "workingPlace",workplace )
                .build();
        Request request=new Request.Builder()
                .url("http://39.106.47.27:8080/conference/api/user/doregister")
                .post( requestBody )
                .build();
        Response response=mClient.newCall(request).execute();
        return response.body().string();

    }

    public String doSearchByName(String name,String url)throws IOException{

        RequestBody requestBody = new FormBody.Builder()
                .add("name",name)
                .build();

        Request request=new Request.Builder()
//                .url("http://39.106.47.27:8080/conference//api/user/dosearchUsersByName"+"?name="+name)
                .url( url )
                .post(requestBody)
                .build();
        Response response=mClient.newCall(request).execute();
        return response.body().string();
    }

    public Response getMeetingInfoResponse(String conferenId,String url) throws IOException{

        RequestBody requestBody = new FormBody.Builder()
                .add("conferenceId",conferenId)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = mClient.newCall(request).execute();
        return response;
    }

    public void getLotteryPeople(String conferenceId,String name,String number,String url,Callback callback)throws IOException{

        RequestBody requestBody = new FormBody.Builder()
                .add("conferenceId",conferenceId)
                .add("name",name)
                .add("number",number)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(callback);

    }

    public int getConferenceIdByName(String conferenceName,String url)throws IOException{

        RequestBody requestBody = new FormBody.Builder()
                .add("conferenceName",conferenceName)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = mClient.newCall(request).execute();
        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            return jsonObject1.getInt("id");
        }catch (JSONException e){
            e.printStackTrace();
        }

        return 0;
    }

    public void doSignInConference(String userID,String conferenceId,String url,okhttp3.Callback callback){
        //?userId=36&conferenceId=33
        RequestBody requestBody = new FormBody.Builder()
                .add("userId",userID)
                .add("conferenceId",conferenceId)
                .build();

        Request request = new Request.Builder()
                .url( url )
                .post(requestBody)
                .build();
        mClient.newCall( request ).enqueue( callback );
    }

    //http://39.106.47.27:8080/conference/api/userEntry/dogetAllConference?userId=34
    public void dogetAllConference(String userID,String url,okhttp3.Callback callback){
        //?userId=36&conferenceId=33
        RequestBody requestBody = new FormBody.Builder()
                .add("userId",userID)
                .build();

        Request request = new Request.Builder()
                .url( url )
                .post(requestBody)
                .build();
        mClient.newCall( request ).enqueue( callback );
    }

}
