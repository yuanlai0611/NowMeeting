package com.gongyunhao.nowmeeting.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.R;

public class AddFriendActivity extends AppCompatActivity {
    private String ADD_FRIEND_EXTRA="MeetingDetailNoJoinActivity_AddFriend";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_friend );
        String friendData=getIntent().getStringExtra( ADD_FRIEND_EXTRA );
        Toast.makeText( AddFriendActivity.this,friendData,Toast.LENGTH_SHORT ).show();

        TextView textView=findViewById( R.id.chatting_title_name );
        textView.setText( "添加好友" );

    }
}
