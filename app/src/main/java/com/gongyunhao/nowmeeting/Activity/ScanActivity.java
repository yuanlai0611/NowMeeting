package com.gongyunhao.nowmeeting.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Base.PermissionActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import okhttp3.Call;
import okhttp3.Response;

public class ScanActivity extends PermissionActivity implements QRCodeView.Delegate{
    private QRCodeView qrCodeView;
    private String userID;
    //?userId=36&conferenceId=33
    //扫码签到接口
    private String checkUrl="http://39.106.47.27:8080/conference/api/userEntry/doSignInConference";
    private static final int REQUEST_SUCCESS=0;
    private static final int REQUEST_FAILED=1;
    private static final int REQUEST_ERROR=2;
    private AlertDialog alertDialog;
    private String responsemessage;

    Handler handler=new Handler(  ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage( msg );
            switch (msg.what){
                case REQUEST_SUCCESS:
                    break;
                case REQUEST_FAILED:
                    showToast( "签到出错!" );
                    alertDialog.dismiss();
                    finish();
                    break;
                case REQUEST_ERROR:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_scan );
        SharedPreferences editor = getSharedPreferences("isLogin",MODE_PRIVATE);
        userID=editor.getString( "userID","" );
        //透明状态栏
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        alertDialog=loadingDialog( "扫描成功,加载中",true );
        qrCodeView = (QRCodeView) findViewById(R.id.zxingview);
        if (ContextCompat.checkSelfPermission( ScanActivity.this, Manifest.permission.CAMERA )!= PackageManager.PERMISSION_GRANTED){
            requestPermission(new String[]{Manifest.permission.CAMERA}, 1);
        }else {
            qrCodeView.setDelegate(ScanActivity.this);
        }
    }

    /**
     * 权限成功回调函数
     *
     * @param requestCode
     */
    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 1:
                qrCodeView.setDelegate(ScanActivity.this);
                qrCodeView.startSpotAndShowRect();
                break;
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        alertDialog.show();
        if (result.length()<13){
            showToast( "出错了，"+result );
            finish();

        }else {
            if (result.substring( 0,11 ).equals( "Now_Meeting" )){
                //可以签到了
                String meetingID=result.split( "," )[1];
                OkHttpUtil.getInstance().doSignInConference( userID,meetingID,checkUrl, new okhttp3.Callback( ) {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里处理异常情况
                        Message message=new Message();
                        message.what=REQUEST_FAILED;
                        handler.sendMessage( message );
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        final String responsestr=response.body().string();
                        try {
                            JSONObject jsonObject=new JSONObject( responsestr );
                            responsemessage=jsonObject.getString( "message" );
                        } catch (JSONException e) {
                            e.printStackTrace( );
                        }
                        runOnUiThread( new Runnable( ) {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                                AlertDialog.Builder mdialog=new AlertDialog.Builder( ScanActivity.this );
                                mdialog.setTitle( "扫描提示！" );
                                mdialog.setMessage( responsemessage );
                                mdialog.setCancelable( false );
                                mdialog.setPositiveButton( "OK", new DialogInterface.OnClickListener( ) {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                } );
                                mdialog.show();
                            }
                        } );
                    }
                } );
            }else {
                alertDialog.dismiss();
                showToast( "签到出错" );
                finish();
            }
        }

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "调用摄像头出错", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        qrCodeView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        qrCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        qrCodeView.onDestroy();
        super.onDestroy();
    }

}
