package com.gongyunhao.nowmeeting.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.DrawView;
import com.gongyunhao.nowmeeting.R;

public class LoginActivity extends BaseActivity {
    private Button btn_login;
    private LinearLayout linear_text,linear_edit;
    private Animation animation1,animation2;
    private TextView textView_signin;
    private EditText editText_username,editText_userpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        initWindow();
        initViews();
        btn_login.setOnClickListener( this );
        textView_signin.setOnClickListener( this );

    }


    @Override
    public void onBackPressed() {
        if (linear_edit.getVisibility()==View.VISIBLE){
            linear_edit.startAnimation( animation1 );
            linear_text.setVisibility( View.VISIBLE );
            linear_edit.setVisibility( View.GONE );
            linear_text.startAnimation( animation2 );
            editText_username.setText( "" );
            editText_userpass.setText( "" );
        }else {
            super.onBackPressed( );
        }
    }

    public void initViews() {
        btn_login=findViewById( R.id.btn_login );
        linear_text=findViewById( R.id.linear_text );
        linear_edit=findViewById( R.id.linear_edit );
        editText_username=findViewById( R.id.et_username );
        editText_userpass=findViewById( R.id.et_userpass );
        textView_signin=findViewById( R.id.textView_signin );
        animation1= AnimationUtils.loadAnimation( this,R.anim.login_anim_linear_hint );
        animation2=AnimationUtils.loadAnimation( this,R.anim.login_anim_linear_show );
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if (linear_text.getVisibility()==View.VISIBLE){
                    linear_text.startAnimation( animation1 );
                    linear_text.setVisibility( View.GONE );
                    linear_edit.setVisibility( View.VISIBLE );
                    linear_edit.startAnimation( animation2 );
                }else {
                    //账号密码登录
                }
                break;
            case R.id.textView_signin:
                Intent intent=new Intent( LoginActivity.this,SignInActivity.class );
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
        }

    }

    @Override
    public void setContentView() {

    }


    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        FrameLayout ll=(FrameLayout) findViewById(R.id.login_frame);
        final DrawView view=new DrawView(LoginActivity.this,"LoginActivity");
        //通知view组件重绘
        view.invalidate();
        ll.addView(view);
    }
}
