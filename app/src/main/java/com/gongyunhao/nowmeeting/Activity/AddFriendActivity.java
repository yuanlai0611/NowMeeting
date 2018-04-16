package com.gongyunhao.nowmeeting.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.R;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class AddFriendActivity extends AppCompatActivity {
    private String ADD_FRIEND_EXTRA="AddFriendActivity_AddFriend";
    private Button btn_send_add_friend_request;
    private EditText et_add_friend_reason;
    private boolean isaddfriend=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_friend );
        btn_send_add_friend_request=findViewById( R.id.btn_send_add_friend_request );
        et_add_friend_reason=findViewById( R.id.et_add_friend_reason );
        TextView textView=findViewById( R.id.chatting_title_name );

        final String friendName=getIntent().getStringExtra( ADD_FRIEND_EXTRA );
        final int meetingID=getIntent().getIntExtra( "joinmeeting",0 );
        if (meetingID==0){
            isaddfriend=true;
        }
        if (isaddfriend){
            textView.setText( "添加好友" );
        }else {
            textView.setText( "申请加入会议" );
        }

        btn_send_add_friend_request.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                String reason=et_add_friend_reason.getText().toString();
                if (isaddfriend){
                    //发送添加好友请求
                    ContactManager.sendInvitationRequest(friendName, "", reason, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (0 == responseCode) {
                                //好友请求请求发送成功
                                Toast.makeText( AddFriendActivity.this,"好友请求发送成功！",Toast.LENGTH_SHORT ).show();
                                finish();
                            } else {
                                //好友请求发送失败
                                Toast.makeText( AddFriendActivity.this,"好友请求发送失败!",Toast.LENGTH_SHORT ).show();
                            }
                        }
                    });
                }else {
                    //申请入群
                    JMessageClient.applyJoinGroup(meetingID, reason, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
                                Toast.makeText(AddFriendActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddFriendActivity.this, "申请失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        } );

    }

}
