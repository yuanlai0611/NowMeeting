package com.gongyunhao.nowmeeting.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Base.PermissionActivity;
import com.gongyunhao.nowmeeting.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;

public class ScanActivity extends PermissionActivity implements QRCodeView.Delegate{
    private QRCodeView qrCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_scan );
        //透明状态栏
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
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
