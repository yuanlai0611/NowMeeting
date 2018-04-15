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

    private String ADD_FRIEND_EXTRA="AddFriendActivity_AddFriend";

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
        myFoldTextView.setText( "1758年，闻名世界的棒棒糖（lollipop）的发明人恩里克·伯纳特·丰利亚多萨，首次推出这种带棍的糖果，结果使一家几乎经营不下去的糖果公司扭亏为盈。\n" +
                "对于一些人来说，在嘴里含着一颗糖果，糖果的棍从嘴唇间露出来，已经成为一种时髦而有趣的标志。\n" +
                "生产棒棒糖（lollipop）的这家西班牙家族公司每年生产40亿只棒棒糖。在全世界拥有许多分公司和工厂，雇佣了将近2000人。他们生产的棒棒糖超过50多个品种，其中包括一种专门针对墨西哥市场的辣味棒棒糖。");

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
                Intent intent=new Intent( MeetingDetailNoJoinActivity.this,UserDetailActivity.class );
                intent.putExtra( ADD_FRIEND_EXTRA,"传入用户id用于添加好友" );//传入用户名
                startActivity( intent );
                break;
        }
    }
}
