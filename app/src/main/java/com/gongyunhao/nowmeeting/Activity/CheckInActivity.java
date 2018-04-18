package com.gongyunhao.nowmeeting.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.Adapter.SearchFriendAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.JsonBean.Data;
import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class CheckInActivity extends BaseActivity {
    private String meetingID;
    private String getSignedUrl="http://39.106.47.27:8080/conference/api/conference/dogetSignedNumber";
    private String inforUrl = "http://39.106.47.27:8080/conference/api/user/dogetInfo";
    private int signUpNumber,signInNumber;
    private String signInPeople;
    private Boolean issuccess;
    private static final int REQUEST_SUCCESS=0;
    private static final int REQUEST_FAILED=1;
    private static final int REQUEST_LOAD=2;
    private String mresponse;
    private RecyclerView recycler_check_in;
    private SearchFriendAdapter searchFriendAdapter;
    private TextView tv_check_number;
    private List<Data> dataList=new ArrayList<>(  );
    private List<String> msearchfrienddatas=new ArrayList<>(  );

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(  ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage( msg );
            switch (msg.what){
                case REQUEST_FAILED://没有查找到好友
                    break;
                case REQUEST_SUCCESS://查找成功
                    loadFriendDetail();
                    tv_check_number.setText( "已签到"+signInNumber+"人" );
                    searchFriendAdapter.notifyDataSetChanged();
                    break;
                case REQUEST_LOAD://加载列表详细数据
                    searchFriendAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_check_in );
        dataList=new ArrayList<>(  );
        recycler_check_in=findViewById( R.id.recycler_meeting_check_in );
        tv_check_number=findViewById( R.id.tv_is_check_number );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( CheckInActivity.this );
        recycler_check_in.setLayoutManager( linearLayoutManager );
        searchFriendAdapter=new SearchFriendAdapter( dataList,CheckInActivity.this );
        recycler_check_in.setAdapter( searchFriendAdapter );

        meetingID=getIntent().getStringExtra("meetingId");
        if (TextUtils.isEmpty( meetingID )){
            showToast( meetingID );
            finish();
        }else {

            try {
                OkHttpUtil.getInstance().getMeetingInfoResponse( meetingID,getSignedUrl, new okhttp3.Callback( ) {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            JSONObject jsonObject22=new JSONObject( response.body().string() );
                            JSONObject detail=jsonObject22.getJSONObject( "data" );
                            signInNumber=detail.getInt( "signInNumber" );
                            signUpNumber=detail.getInt( "signUpNumber" );
                            signInPeople=detail.getString( "signInPeople" );
                            Log.d( "qqqqqqqqqqqqqq",signInPeople );
                            issuccess=jsonObject22.getBoolean( "success" );

                            if (!issuccess){//没有找到
                                Message message_fail=new Message();
                                message_fail.what=REQUEST_FAILED;
                                handler.sendMessage( message_fail );
                            }else {//找到了
                                msearchfrienddatas=parseDataWithJSON(signInPeople);//username的数组
                                Message message_success=new Message();
                                message_success.what=REQUEST_SUCCESS;
                                handler.sendMessage( message_success );
                            }


                        } catch (JSONException e) {
                            e.printStackTrace( );
                        }
                    }
                } );
            } catch (IOException e) {
                e.printStackTrace( );
            }

        }

    }

    private List<String> parseDataWithJSON(String checklistdata) {
        msearchfrienddatas.clear();
        checklistdata=checklistdata.substring( 1,checklistdata.length()-1 );
        String[] mdatas=checklistdata.split( "," );
        for (int i=0;i<mdatas.length;i++){
            mdatas[i]=mdatas[i].substring( 1,mdatas[i].length()-1 );
            msearchfrienddatas.add( mdatas[i] );
            Log.d( "---Search---",mdatas[i] );
        }
        return msearchfrienddatas;
    }

    private void loadFriendDetail(){
        dataList.clear();
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                for (String name:msearchfrienddatas){
                    try {
                        mresponse=OkHttpUtil.getInstance().getInfo( name,inforUrl );
                    } catch (IOException e) {
                        e.printStackTrace( );
                    }
                    Gson gson=new Gson();
                    final Root root = gson.fromJson(mresponse, Root.class);
                    dataList.add( root.getData() );
                    Message message=new Message();
                    message.what=REQUEST_LOAD;
                    handler.sendMessage( message );
                }
            }
        } ).start();
    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
