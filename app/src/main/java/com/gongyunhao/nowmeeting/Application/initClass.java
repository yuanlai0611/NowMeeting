package com.gongyunhao.nowmeeting.Application;

import android.app.Application;
import android.content.SharedPreferences;

import org.litepal.LitePalApplication;

import cn.jpush.im.android.api.JMessageClient;

public class initClass extends LitePalApplication{



    @Override
    public void onCreate() {

        super.onCreate();
        JMessageClient.setDebugMode(true);
        JMessageClient.init(initClass.this);


    }

}
