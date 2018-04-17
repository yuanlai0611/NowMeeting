package com.gongyunhao.nowmeeting.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Adapter.SearchMeetingAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.JsonBean.MeetingInformation;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {
    private ImageButton ib_back;
    private EditText et_search;
    private String meetinglistdata;
    private String inforUrl = "http://39.106.47.27:8080/conference/api/conference/dosearchConferenceByName";
    private String response;
    private boolean issuccess;
    private SearchMeetingAdapter searchMeetingAdapter;
    private List<MeetingInformation> meetingInformationList;
    private RecyclerView recycler_meeting;
    private TextView tv_nodata;
    private static final int REQUEST_SUCCESS=0;
    private static final int REQUEST_FAILED=1;
    private static final int REQUEST_LOAD=2;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(  ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage( msg );
            switch (msg.what){
                case REQUEST_FAILED://没有查找到会议
                    recycler_meeting.setVisibility( View.GONE );
                    tv_nodata.setVisibility( View.VISIBLE );
                    meetingInformationList.clear();
                    searchMeetingAdapter.notifyDataSetChanged();
                    break;
                case REQUEST_SUCCESS://查找成功
                    tv_nodata.setVisibility( View.GONE );
                    recycler_meeting.setVisibility( View.VISIBLE );
                    searchMeetingAdapter.notifyDataSetChanged();
                    break;
                case REQUEST_LOAD://加载列表详细数据
                    break;
                    default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setEnterTransition(new Explode(  ));
//        setContentView( );
//        initViews();

        ib_back.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //TODO:你自己的业务逻辑
                    new Thread( new Runnable( ) {
                        @Override
                        public void run() {
                            try {
                                meetinglistdata= OkHttpUtil.getInstance().doSearchByName( et_search.getText().toString(),inforUrl );
                            } catch (IOException e) {
                                e.printStackTrace( );
                            }
                            Log.d( "Search++++++",meetinglistdata );
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject( meetinglistdata );
                                issuccess=jsonObject.getBoolean( "success" );
                            } catch (JSONException e) {
                                e.printStackTrace( );
                            }
                            if (!issuccess){//没有找到
                                Message message_fail=new Message();
                                message_fail.what=REQUEST_FAILED;
                                handler.sendMessage( message_fail );
                            }else {//找到了
                                parseDataWithJSON(meetinglistdata);//username的数组
                                Message message_success=new Message();
                                message_success.what=REQUEST_SUCCESS;
                                handler.sendMessage( message_success );
                            }
                        }
                    } ).start();

                    return true;
                }
                return false;
            }

        });

    }

    private void parseDataWithJSON(String friendlistdata) {
        try {
            meetingInformationList.clear();
            JSONObject jsonObject=new JSONObject( friendlistdata );
            JSONArray data=jsonObject.getJSONArray( "data" );
            for (int i=0;i<data.length();i++){
                JSONObject jsonObject1=data.getJSONObject( i );
                MeetingInformation meeting=new MeetingInformation();
                meeting.setId( jsonObject1.getInt( "id" ) );
                meeting.setCategoryId( jsonObject1.getInt( "categoryId" ) );
                meeting.setSponsorName( jsonObject1.getString( "sponsorName" ) );
                meeting.setName( jsonObject1.getString( "name" ) );
                meeting.setChatroomId( jsonObject1.getInt( "chatroomId" ) );
                meeting.setSponsorId( jsonObject1.getInt( "sponsorId" ) );
                meeting.setStatus( jsonObject1.getInt( "status" ) );
                meeting.setIntroduction( jsonObject1.getString( "introduction" ) );
                meeting.setCity( jsonObject1.getString( "city" ) );
                meeting.setLocation( jsonObject1.getString( "location" ) );
                meeting.setTime( jsonObject1.getString( "time" ) );
                meeting.setSignedNumber( jsonObject1.getInt( "signedNumber" ) );
                meeting.setParticipatedNumber( jsonObject1.getInt( "participatedNumber" ) );
                meeting.setPhoto( jsonObject1.getString( "photo" ) );
                meetingInformationList.add( meeting );
            }
        } catch (JSONException e) {
            e.printStackTrace( );
        }
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_search );
    }

    @Override
    public void initViews() {
        ib_back=findViewById( R.id.search_imageButton_back );
        et_search=findViewById( R.id.title_edittext_search );
        meetingInformationList=new ArrayList<>(  );
        tv_nodata=findViewById( R.id.search_meeting_no_data );
        recycler_meeting=findViewById( R.id.recycle_search );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this );
        recycler_meeting.setLayoutManager( linearLayoutManager );
        searchMeetingAdapter=new SearchMeetingAdapter(meetingInformationList,SearchActivity.this);
        recycler_meeting.setAdapter( searchMeetingAdapter );
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {

    }
}
