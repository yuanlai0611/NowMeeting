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

import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.gongyunhao.nowmeeting.view.DrawView;
import com.gongyunhao.nowmeeting.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.raphets.roundimageview.RoundImageView;

import java.io.IOException;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    private RoundImageView roundImageView_select_picture;
    private EditText et_remark_name_sign_in,et_phone_number_sign_in,et_email_sign_in,et_user_address_sign_in;
    private EditText et_company_sign_in,et_password_sign_in,et_password_re_sign_in,et_signature_sign_in,et_workplace_sign_in;
    private TextView tv_title_signin_cancel,tv_title_signin_yes;
    private String emailPattern="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private String signinUrl = "http://39.106.47.27:8080/conference//api/user/doregister";
    private String remark_name_sign_in;
    private String phone_number_sign_in;
    private String email_sign_in;
    private String user_address_sign_in;
    private String company_sign_in;
    private String password_sign_in;
    private String password_re_sign_in;
    private String signature_sign_in;
    private String workplace_sign_in;

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
                remark_name_sign_in=et_remark_name_sign_in.getText().toString();
                phone_number_sign_in=et_phone_number_sign_in.getText().toString();
                email_sign_in=et_email_sign_in.getText().toString();
                user_address_sign_in=et_user_address_sign_in.getText().toString();
                company_sign_in=et_company_sign_in.getText().toString();
                password_sign_in=et_password_sign_in.getText().toString();
                password_re_sign_in=et_password_re_sign_in.getText().toString();
                signature_sign_in=et_signature_sign_in.getText().toString();
                workplace_sign_in=et_workplace_sign_in.getText().toString();
                //差头像的String，需要传入null
                if (TextUtils.isEmpty( remark_name_sign_in )||TextUtils.isEmpty( phone_number_sign_in )||TextUtils.isEmpty( email_sign_in )||TextUtils.isEmpty( company_sign_in )||TextUtils.isEmpty( user_address_sign_in )||TextUtils.isEmpty( signature_sign_in )||TextUtils.isEmpty( password_sign_in )||TextUtils.isEmpty( password_re_sign_in )||TextUtils.isEmpty( workplace_sign_in )){
                    Toast.makeText( SignInActivity.this,"信息填写不完整！",Toast.LENGTH_SHORT ).show();
                }else {
                    if (!password_sign_in.equals( password_re_sign_in )){
                        Toast.makeText( SignInActivity.this,"两次密码输入不一致！",Toast.LENGTH_SHORT ).show();
                    }else {
                        /**
                         * 执行网络请求去注册
                         */
                        new Thread( new Runnable( ) {
                            String signdata;
                            @Override
                            public void run() {
                                try {
                                    signdata=OkHttpUtil.getInstance().getSignInfo( remark_name_sign_in,password_sign_in,phone_number_sign_in,email_sign_in,
                                            user_address_sign_in,company_sign_in,signature_sign_in,workplace_sign_in );

                                } catch (IOException e) {
                                    e.printStackTrace( );
                                }
                                Gson gson=new Gson();
                                final Root root = gson.fromJson(signdata, Root.class);
                                runOnUiThread( new Runnable( ) {
                                    @Override
                                    public void run() {
                                        if (!root.getSuccess()){
                                            Toast.makeText( SignInActivity.this,root.getMessage(),Toast.LENGTH_SHORT ).show();
                                        }else {
                                            Toast.makeText( SignInActivity.this,"注册成功！",Toast.LENGTH_SHORT ).show();
                                            finish();
                                        }
                                    }
                                } );
                            }
                        } ).start();
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
        et_workplace_sign_in=findViewById( R.id.et_workplace_sign_in );          //工作地点

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
