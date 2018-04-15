package com.gongyunhao.nowmeeting.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.JsonBean.Data;
import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

public class UserDetailActivity extends BaseActivity {
    private TextView user_name,user_city,signature,telephone;
    private TextView email,workplace,school;
    private Button btn_add_friend_detail;
    private String ADD_FRIEND_EXTRA="AddFriendActivity_AddFriend";
    private String friname;
    private String inforUrl = "http://39.106.47.27:8080/conference/api/user/dogetInfo";
    private String response;
    private AlertDialog alertDialog;
    private Data userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friname=getIntent(  ).getStringExtra( ADD_FRIEND_EXTRA );
        alertDialog=loadingDialog( "获取用户信息中...",true );
        loadUserData();
        btn_add_friend_detail.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( UserDetailActivity.this,AddFriendActivity.class );
                intent.putExtra( ADD_FRIEND_EXTRA,friname );
                startActivity( intent );
            }
        } );
    }

    private void loadUserData(){
        alertDialog.show();
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                try {
                    response = OkHttpUtil.getInstance().getInfo(friname,inforUrl);
                } catch (IOException e) {
                    e.printStackTrace( );
                }
                Gson gson=new Gson();
                final Root root = gson.fromJson(response, Root.class);
//                if (root.getSuccess()){
//                    try {
//                        JSONObject jsonObject=new JSONObject( response );
//                        JSONObject dataobject=jsonObject.getJSONObject( "data" );
//                    } catch (JSONException e) {
//                        e.printStackTrace( );
//                    }
//
//                }
                runOnUiThread( new Runnable( ) {
                    @Override
                    public void run() {
                        if (!root.getSuccess()){
                            showToast( root.getMessage() );
                            finish();
                        }else {
                            userdata=root.getData();
                            Log.d( "userdetail","----------->"+root.getData().getEmail() );
                            loadViews();
                        }
                        alertDialog.dismiss();
                    }
                } );
            }
        } ).start();
    }

    private void loadViews(){
        user_name.setText( userdata.getUsername() );
        user_city.setText( userdata.getAddress() );
        signature.setText( userdata.getSignature() );
        telephone.setText( userdata.getPhone() );
        email.setText( userdata.getEmail() );
        workplace.setText( userdata.getWorkingPlace() );
        school.setText( userdata.getGraduateSchool() );
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_user_detail);
    }

    @Override
    public void initViews() {
        user_name=findViewById( R.id.tv_user_name_detail );
        user_city=findViewById( R.id.tv_user_city_detail );
        signature=findViewById( R.id.tv_signature_detail );
        telephone=findViewById( R.id.tv_telephone_detail );
        email=findViewById( R.id.tv_email_detail );
        workplace=findViewById( R.id.tv_workplace_detail );
        school=findViewById( R.id.tv_school_detail );
        btn_add_friend_detail=findViewById( R.id.btn_add_friend_detail );
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
