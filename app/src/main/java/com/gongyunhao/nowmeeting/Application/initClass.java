package com.gongyunhao.nowmeeting.Application;

import android.os.StrictMode;
import org.litepal.LitePalApplication;
import cn.jpush.im.android.api.JMessageClient;

public class initClass extends LitePalApplication{


    @Override
    public void onCreate() {

        super.onCreate();
        JMessageClient.setDebugMode(true);
        JMessageClient.init(initClass.this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


    }

}
