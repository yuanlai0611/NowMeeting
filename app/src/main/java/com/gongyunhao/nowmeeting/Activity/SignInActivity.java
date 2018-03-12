package com.gongyunhao.nowmeeting.Activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.DrawView;
import com.gongyunhao.nowmeeting.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imageButton_signin_post;
    private EditText editText_email;
    private String emailPattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setEnterTransition(new Explode(  ));
        setContentView( R.layout.activity_sign_in );
        initWindow();
        imageButton_signin_post=findViewById( R.id.ibtn_signin_post );
        editText_email=findViewById( R.id.et_email );
        imageButton_signin_post.setOnClickListener( this );
        imageButton_signin_post.setEnabled( false );
        editText_email.addTextChangedListener( new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(checkString( editText_email.getText().toString() )){
                    imageButton_signin_post.setEnabled( true );
                }else {
                    imageButton_signin_post.setEnabled( false );
                }
            }
        } );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_signin_post:
                break;
        }
    }

    private boolean checkString(String s) {
        return s.matches(emailPattern);
    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        FrameLayout ll=(FrameLayout) findViewById(R.id.login_frame);
        final DrawView view=new DrawView(SignInActivity.this,"SignInActivity");
        //通知view组件重绘
        view.invalidate();
        ll.addView(view);
    }

}
