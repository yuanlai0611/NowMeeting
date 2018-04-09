package com.gongyunhao.nowmeeting.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.view.MyFoldTextView;

public class MeetingDetailNoJoinActivity extends BaseActivity implements View.OnClickListener{
    private Button add_friend;

    private String ADD_FRIEND_EXTRA="MeetingDetailNoJoinActivity_AddFriend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView();
        initViews();
        initData();


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

        //自定义FoldTextView测试文字
        MyFoldTextView myFoldTextView=findViewById( R.id.ftv_meeting_detail );
        myFoldTextView.setText( "棒棒糖（lollipop）发明人恩里克。\n");

        add_friend.setOnClickListener( this );
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_friend:
                Intent intent1=new Intent( MeetingDetailNoJoinActivity.this,CreateVoteActivity.class );
                Intent intent=new Intent( MeetingDetailNoJoinActivity.this,AddFriendActivity.class );
                intent.putExtra( ADD_FRIEND_EXTRA,"传入用户id用于添加好友" );
                startActivity( intent1 );
                break;
        }
    }
}
