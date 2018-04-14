package com.gongyunhao.nowmeeting.Activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.view.DrawView;
import com.gongyunhao.nowmeeting.R;

import org.raphets.roundimageview.RoundImageView;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private RoundImageView roundImageView_select_picture;
    private EditText et_remark_name_sign_in,et_phone_number_sign_in,et_email_sign_in,et_user_address_sign_in;
    private EditText et_company_sign_in,et_password_sign_in,et_password_re_sign_in,et_signature_sign_in;
    private TextView tv_title_signin_cancel,tv_title_signin_yes;
    private String emailPattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
//        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode(  ));
        setContentView( R.layout.activity_sign_in );
        initWindow();
        initView();
        tv_title_signin_cancel.setOnClickListener( this );
        tv_title_signin_yes.setOnClickListener( this );


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_signin_cancel:
                finish();
                break;
            case R.id.tv_title_signin_yes:
                String remark_name_sign_in=et_remark_name_sign_in.getText().toString();
                String phone_number_sign_in=et_phone_number_sign_in.getText().toString();
                String email_sign_in=et_email_sign_in.getText().toString();
                String user_address_sign_in=et_user_address_sign_in.getText().toString();
                String company_sign_in=et_company_sign_in.getText().toString();
                String password_sign_in=et_password_sign_in.getText().toString();
                String password_re_sign_in=et_password_re_sign_in.getText().toString();
                String signature_sign_in=et_signature_sign_in.getText().toString();
                if (TextUtils.isEmpty( remark_name_sign_in )||TextUtils.isEmpty( phone_number_sign_in )||TextUtils.isEmpty( email_sign_in )||TextUtils.isEmpty( company_sign_in )||TextUtils.isEmpty( user_address_sign_in )||TextUtils.isEmpty( signature_sign_in )||TextUtils.isEmpty( password_sign_in )||TextUtils.isEmpty( password_re_sign_in )){
                    Toast.makeText( SignInActivity.this,"信息填写不完整！",Toast.LENGTH_SHORT ).show();
                }else {
                    if (!password_sign_in.equals( password_re_sign_in )){
                        Toast.makeText( SignInActivity.this,"两次密码输入不一致！",Toast.LENGTH_SHORT ).show();
                    }else {
                        /**
                         * 执行网络请求去注册
                         */
                    }
                }
                break;
        }
    }

    private boolean checkString(String s) {
        return s.matches(emailPattern);
    }

    private void initView(){
        roundImageView_select_picture=findViewById( R.id.rv_select_photo_sign_in );

        //edittext要获取的信息都在这儿
        et_remark_name_sign_in=findViewById( R.id.et_remark_name_sign_in );      //昵称username
        et_phone_number_sign_in=findViewById( R.id.et_phone_number_sign_in );    //电话telephone
        et_email_sign_in=findViewById( R.id.et_email_sign_in );                  //邮箱email
        et_user_address_sign_in=findViewById( R.id.et_user_address_sign_in );    //住址address
        et_company_sign_in=findViewById( R.id.et_company_sign_in );              //公司/毕业学校graduateSchool
        et_password_sign_in=findViewById( R.id.et_password_sign_in );            //密码password
        et_password_re_sign_in=findViewById( R.id.et_password_re_sign_in );      //确认密码
        et_signature_sign_in=findViewById( R.id.et_signature_sign_in );          //个性签名signature

        tv_title_signin_cancel=findViewById( R.id.tv_title_signin_cancel );
        tv_title_signin_yes=findViewById( R.id.tv_title_signin_yes );
    }

    private void initWindow() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.WHITE);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR );
        }
    }

}
