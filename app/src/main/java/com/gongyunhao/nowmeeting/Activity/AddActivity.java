package com.gongyunhao.nowmeeting.Activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Adapter.QuNiMaDeFriendAdapter;
import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.api.BasicCallback;

public class AddActivity extends AppCompatActivity {
    private static final int RECIVICE_FRIEND_REQUEST=1;
    private static final int ACCEPTED_MY_INVITATION=2;
    private static final int REFUSE_MY_INVITATION=3;
    private static final int DELETE_ME=4;
    private String mFromUserName;
    private String mReason;
    private String response;
    private String inforUrl = "http://39.106.47.27:8080/conference/api/user/dogetInfo";
    private QuNiMaDeFriendAdapter quNiMaDeFriendAdapter;

    RecyclerView recyclerViewAddFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mFromUserName=getIntent().getStringExtra( "qunimadejiahaoyou" );
        mReason=getIntent().getStringExtra( "qunimadejiahaoyoureason" );
        recyclerViewAddFriend = (RecyclerView)findViewById(R.id.recyclerview_add_friend);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( this );
        recyclerViewAddFriend.setLayoutManager( linearLayoutManager );
        quNiMaDeFriendAdapter=new QuNiMaDeFriendAdapter( mFromUserName,mReason,AddActivity.this );
        recyclerViewAddFriend.setAdapter( quNiMaDeFriendAdapter );
    }

//    private void loadUserData(){
//        new Thread( new Runnable( ) {
//            @Override
//            public void run() {
//                try {
//                    response = OkHttpUtil.getInstance().getInfo(mFromUserName,inforUrl);
//                } catch (IOException e) {
//                    e.printStackTrace( );
//                }
//                Gson gson=new Gson();
//                final Root root = gson.fromJson(response, Root.class);
//                runOnUiThread( new Runnable( ) {
//                    @Override
//                    public void run() {
//                        if (!root.getSuccess()){
//                            Toast.makeText( AddActivity.this,root.getMessage(),Toast.LENGTH_SHORT ).show();
//                            finish();
//                        }else {
//                            userdata=root.getData();
//                            loadViews();
//                        }
//                    }
//                } );
//            }
//        } ).start();
//    }

    private void AcceptInvitation(String username){
        ContactManager.acceptInvitation(username, "", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    //接收好友请求成功
                    Toast.makeText( AddActivity.this,"接受好友成功！",Toast.LENGTH_SHORT ).show();
                } else {
                    //接收好友请求失败
                    Toast.makeText( AddActivity.this,"接受好友失败！",Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

    private void DeclineInvitation(String username){
        ContactManager.declineInvitation(username, "", "sorry~", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    //拒绝好友请求成功
                    Toast.makeText( AddActivity.this,"已拒绝！",Toast.LENGTH_SHORT ).show();
                } else {
                    //拒绝好友请求失败
                    Toast.makeText( AddActivity.this,"出错了，拒绝未成功！",Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

}
