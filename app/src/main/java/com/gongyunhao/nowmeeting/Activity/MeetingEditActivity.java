package com.gongyunhao.nowmeeting.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.DataBaseBean.DataBaseUserInfo;
import com.gongyunhao.nowmeeting.JsonBean.MeetingCreate;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.google.gson.Gson;
import org.litepal.crud.DataSupport;
import java.io.IOException;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MeetingEditActivity extends BaseActivity {

    private EditText editTextIntrodoction;
    private EditText editTextPlace;
    private EditText editTextDetailPlace;
    private EditText editTextMeetingName;
    private EditText editTextMeetingTime;
    private TextView textViewPublishMeeting;
    private String meetingCreateUrl = "http://39.106.47.27:8080/conference/api/conference/docreateConference";
    private String myName;
    private int myUserId;
    private int meetingId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBaseUserInfo dataBaseUserInfo = DataSupport.findFirst(DataBaseUserInfo.class);
        myName = dataBaseUserInfo.getUsername();
        myUserId = dataBaseUserInfo.getUserId();

        Log.d(Tag,"---->"+myName);
        Log.d(Tag,"---->"+myUserId);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_meeting_edit);
    }

    @Override
    public void initViews() {
        editTextIntrodoction = (EditText)findViewById(R.id.meeting_introduction);
        editTextPlace = (EditText)findViewById(R.id.place);
        editTextDetailPlace = (EditText)findViewById(R.id.detail_place);
        editTextMeetingName = (EditText)findViewById(R.id.meeting_name);
        editTextMeetingTime = (EditText)findViewById(R.id.meeting_time);
        textViewPublishMeeting = (TextView)findViewById(R.id.publish_meeting);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.publish_meeting:

                String meetingIntroduction = editTextIntrodoction.getText().toString();
                String meetingPlace = editTextPlace.getText().toString();
                String meetingDetailPlace = editTextDetailPlace.getText().toString();
                String meetingName = editTextMeetingName.getText().toString();
                String meetingTime = editTextMeetingTime.getText().toString();
                createMeeing(String.valueOf(myUserId),myName,meetingName,meetingIntroduction,meetingPlace,meetingDetailPlace,meetingTime,"  ");

                break;
            default:
                break;
        }
    }

    @Override
    public void initListeners() {
        textViewPublishMeeting.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    public void createMeeing(final String sponsorId, final String sponsorName, final String conferenceName, final String introduction, final String city,final String location, final String time,final String photo){

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String > subscriber) {
                try {

                    Response response = OkHttpUtil.getInstance().getCreateMeetingResponse(sponsorId,sponsorName,conferenceName,introduction,city,location,time,"啦啦啦啦啦",meetingCreateUrl);

                    if (response.code()==200){

                        subscriber.onNext(response.body().string());
                        subscriber.onCompleted();

                    }else{

                        throw new IOException();

                    }



                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>()
                {

                    @Override
                    public void onNext(String response) {

                       Gson gson = new Gson();
                       MeetingCreate meetingCreate = gson.fromJson(response,MeetingCreate.class);
                       meetingId = meetingCreate.getData();
                       Log.d(Tag,"---->"+meetingId);

                    }

                    @Override
                    public void onCompleted() {

                        Toast.makeText(MeetingEditActivity.this,"会议创建成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MeetingEditActivity.this,MeetingDetailActivity.class);
                        intent.putExtra("meetingId",meetingId);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(MeetingEditActivity.this,"会议创建失败...",Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });




    }
}
