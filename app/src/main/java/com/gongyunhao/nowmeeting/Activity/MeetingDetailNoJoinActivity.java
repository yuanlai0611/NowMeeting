package com.gongyunhao.nowmeeting.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.JsonBean.MeetingInformation;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.gongyunhao.nowmeeting.view.MyFoldTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Response;

public class MeetingDetailNoJoinActivity extends BaseActivity implements View.OnClickListener{
    private Button add_friend;
    private TextView tv_meeting_name,tv_meeting_date,tv_meeting_location,sponsor_name;
    private MyFoldTextView myFoldTextView;
    private int meeting_ID;
    private String MEETING_ID_EXTRA="nojoin_meeting_id";
    private String ADD_FRIEND_EXTRA="AddFriendActivity_AddFriend";
    private String meetingUrl="http://39.106.47.27:8080/conference/api/conference/dogetConferenceInfo";
    private Response response;
    private MeetingInformation meeting;
    private String responsestr;
    private boolean issuccess;
    private Button btn_join_m;
    private static final int REQUEST_SUCCESS=0;
    private static final int REQUEST_FAILED=1;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(  ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage( msg );
            switch (msg.what){
                case REQUEST_FAILED://失败
                    showToast( "出错了！" );
                    finish();
                    break;
                case REQUEST_SUCCESS://成功
                    tv_meeting_name.setText( meeting.getName() );
                    tv_meeting_date.setText( meeting.getTime() );
                    tv_meeting_location.setText( meeting.getCity()+meeting.getLocation() );
                    //自定义FoldTextView测试文字
                    myFoldTextView.setText(meeting.getIntroduction());
                    sponsor_name.setText( meeting.getSponsorName() );
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        meeting_ID=getIntent(  ).getIntExtra( MEETING_ID_EXTRA,0 );
        if (meeting_ID==0){
            finish();
            showToast( "出错了！" );
        }else {
            new Thread( new Runnable( ) {
                @Override
                public void run() {
                    try {
                        response= OkHttpUtil.getInstance().getMeetingInfoResponse( String.valueOf( meeting_ID ),meetingUrl );
                        responsestr=response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace( );
                    }
                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject( responsestr );
                        issuccess=jsonObject.getBoolean( "success" );
                    } catch (JSONException e) {
                        e.printStackTrace( );
                    }
                    if (!issuccess){//没有找到
                        Message message_fail=new Message();
                        message_fail.what=REQUEST_FAILED;
                        handler.sendMessage( message_fail );
                    }else {//找到了
                        parseDataWithJSON(responsestr);//username的数组
                        Message message_success=new Message();
                        message_success.what=REQUEST_SUCCESS;
                        handler.sendMessage( message_success );
                    }

                }
            } ).start();
        }
    }
    private void parseDataWithJSON(String responsestr2) {
        try {
            JSONObject jsonObject=new JSONObject( responsestr2 );
            JSONObject data=jsonObject.getJSONObject( "data" );
            meeting=new MeetingInformation();
            meeting.setId( data.getInt( "id" ) );
            meeting.setCategoryId( data.getInt( "categoryId" ) );
            meeting.setSponsorName( data.getString( "sponsorName" ) );
            meeting.setName( data.getString( "name" ) );
            meeting.setChatroomId( data.getInt( "chatroomId" ) );
            meeting.setSponsorId( data.getInt( "sponsorId" ) );
            meeting.setStatus( data.getInt( "status" ) );
            meeting.setIntroduction( data.getString( "introduction" ) );
            meeting.setCity( data.getString( "city" ) );
            meeting.setLocation( data.getString( "location" ) );
            meeting.setTime( data.getString( "time" ) );
            meeting.setSignedNumber( data.getInt( "signedNumber" ) );
            meeting.setParticipatedNumber( data.getInt( "participatedNumber" ) );
            meeting.setPhoto( data.getString( "photo" ) );

        } catch (JSONException e) {
            e.printStackTrace( );
        }
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_meeting_detail_no_join );
    }

    @Override
    public void initViews() {
        TextView textView=findViewById( R.id.chatting_title_name );
        textView.setText( "会议详情" );
        add_friend=findViewById( R.id.btn_add_friend );
        tv_meeting_name=findViewById( R.id.tv_meeting_name_no_join );
        tv_meeting_date=findViewById( R.id.tv_meeting_date_no_join );
        tv_meeting_location=findViewById( R.id.tv_location_no_join );
        myFoldTextView=findViewById( R.id.ftv_meeting_detail );
        sponsor_name=findViewById( R.id.meeting_sponsor_name );
        btn_join_m=findViewById( R.id.btn_join_meeting );
    }

    @Override
    public void initListeners() {
        btn_join_m.setOnClickListener( this );
        add_friend.setOnClickListener( this );
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_friend:
                Intent intent=new Intent( MeetingDetailNoJoinActivity.this,UserDetailActivity.class );
                intent.putExtra( ADD_FRIEND_EXTRA,meeting.getSponsorName() );//传入用户名
                startActivity( intent );
                break;
            case R.id.btn_join_meeting:
                //申请入群
                Intent intent1=new Intent( MeetingDetailNoJoinActivity.this,AddFriendActivity.class );
                intent1.putExtra( "joinmeeting",meeting_ID );
                startActivity( intent1 );
                break;
        }
    }
}
