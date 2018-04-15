package com.gongyunhao.nowmeeting.util;

import java.io.IOException;

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

    public  String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

    public String getInfo(String name,String url) throws IOException{

        Request request = new Request.Builder()
                .url(url+"?username="+name)
                .get()
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

    public String doSearchUserByName(String name)throws IOException{
        Request request=new Request.Builder()
                .url("http://39.106.47.27:8080/conference//api/user/dosearchUsersByName"+"?name="+name)
                .get()
                .build();
        Response response=mClient.newCall(request).execute();
        return response.body().string();
    }



}
