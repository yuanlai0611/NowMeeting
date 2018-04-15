package com.gongyunhao.nowmeeting.Activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.R;

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

    RecyclerView recyclerViewAddFriend;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(  ){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage( msg );
            switch (msg.what){
                case RECIVICE_FRIEND_REQUEST:
                    //收到了好友请求
                    break;
                case ACCEPTED_MY_INVITATION:
                    break;
                case REFUSE_MY_INVITATION:
                    break;
                case DELETE_ME:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        recyclerViewAddFriend = (RecyclerView)findViewById(R.id.recyclerview_add_friend);

    }


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
