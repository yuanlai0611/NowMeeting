package com.gongyunhao.nowmeeting.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.DataBaseBean.DataBaseUserInfo;
import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.DeviceInfo;
import cn.jpush.im.api.BasicCallback;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private Button btn_login;
    private LinearLayout linear_text,linear_edit;
    private Animation animation1,animation2;
    private TextView textView_signin;
    private EditText editText_username,editText_userpass;
    private String Tag = "LoginActivity";
    private String inforUrl = "http://39.106.47.27:8080/conference/api/user/dogetInfo";
    private AlertDialog loadingDialog;
    private Boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        initWindow();
        initViews();
        btn_login.setOnClickListener( this );
        textView_signin.setOnClickListener( this );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        btn_login.setOnClickListener( this );
        textView_signin.setOnClickListener( this );
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

                    if (editText_username.getText().toString().isEmpty()){
                        Toast.makeText(LoginActivity.this,"用户名为空",Toast.LENGTH_SHORT).show();
                    }else if (editText_userpass.getText().toString().isEmpty()){
                        Toast.makeText(LoginActivity.this,"密码为空",Toast.LENGTH_SHORT).show();
                    }else {
                        login(editText_username.getText().toString(),editText_userpass.getText().toString());
                    }

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

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("isLogin",MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("isLogin",false);
        if (isLogin==true){
            JMessageClient.login(sharedPreferences.getString("userName", ""), sharedPreferences.getString("passWord", ""), new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i==0){
                        Log.d(Tag,"---->登录成功");
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Log.d(Tag,"---->登录失败");
                    }
                }
            });

        }
        setContentView( R.layout.activity_login );
    }


    private void initWindow() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    public void login(final String name, final String passWord){

        loadingDialog = loadingDialog("正在登录...",false);
        loadingDialog.show();

        JMessageClient.login(name, passWord, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

                if (i==0){
                    Log.d(Tag,"---->极光登录成功");

                    Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String > subscriber) {
                            try {

                                String response = OkHttpUtil.getInstance().getInfo(editText_username.getText().toString(),inforUrl);
                                Log.d(Tag,"---->执行完网络请求");
                                Log.d(Tag,"---->"+response);

                                Gson gson = new Gson();
                                Root root = gson.fromJson(response, Root.class);
                                Log.d(Tag,"---->"+root.getData().getUsername());

                                List<DataBaseUserInfo> dataBaseUserInfoList = DataSupport.findAll(DataBaseUserInfo.class);
                                if (!dataBaseUserInfoList.isEmpty()){
                                    DataSupport.deleteAll(DataBaseUserInfo.class);
                                }
                                DataBaseUserInfo dataBaseUserInfo = new DataBaseUserInfo();
                                dataBaseUserInfo.setUsername(root.getData().getUsername());
                                dataBaseUserInfo.setUserId(root.getData().getId());
                                dataBaseUserInfo.setAddress(root.getData().getAddress());
                                dataBaseUserInfo.setEmail(root.getData().getEmail());
                                dataBaseUserInfo.setGraduateSchool(root.getData().getGraduateSchool());
                                dataBaseUserInfo.setPassword(root.getData().getPassword());
                                dataBaseUserInfo.setPhone(root.getData().getPhone());
                                dataBaseUserInfo.setSignature(root.getData().getSignature());
                                dataBaseUserInfo.setWorkingPlace(root.getData().getWorkingPlace());

                                if (dataBaseUserInfo.save()){
                                    Log.d(Tag,"---->用户信息储存成功");
                                }else{
                                    Log.d(Tag,"---->用户信息储存失败");
                                }


                                // subscriber.onNext(response);
                                subscriber.onCompleted();

                            } catch (IOException e) {
                                e.printStackTrace();
                                subscriber.onError(e);
                            }

                        }
                    })
                            .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                            .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                            .subscribe(new Observer<String>()
                            {

                                @Override
                                public void onNext(String response) {

                                    Log.d(Tag,"---->调用onNext");


                                }

                                @Override
                                public void onCompleted() {

                                    loadingDialog.dismiss();
                                    startIntent( MainActivity.class );
                                    SharedPreferences.Editor editor = getSharedPreferences("isLogin",MODE_PRIVATE).edit();
                                    editor.putBoolean("isLogin",true);
                                    editor.putString("userName",name);
                                    editor.putString("passWord",passWord);
                                    editor.apply();
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {

                                    loadingDialog.dismiss();
                                    Toast.makeText(LoginActivity.this,"登录失败...",Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = getSharedPreferences("isLogin",MODE_PRIVATE).edit();
                                    editor.putBoolean("isLogin",false);
                                    editor.apply();

                                }
                            });



                }else {
                    Log.d(Tag,"---->极光登录失败");
                    loadingDialog.dismiss();
                }

            }
        });


        }

}
