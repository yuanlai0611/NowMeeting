package com.gongyunhao.nowmeeting.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.Base.PermissionActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.ImgUtil;
import com.gongyunhao.nowmeeting.util.QRCodeUtil;

public class QrCodeActivity extends PermissionActivity {
    private String QR_CODE_CONTENT="Extra_Qr_Content";
    private String qrcontent;
    private ImageView iv;
    private TextView titlename;
    private Button save,share;
    private Uri uri;
    private Bitmap mSavedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_qr_code );
        initViews();
        titlename.setText( "会议二维码" );
        qrcontent=getIntent().getStringExtra( QR_CODE_CONTENT );
        logoBitmap( qrcontent );

        share.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                ImgUtil imgUtil=new ImgUtil();
                if (ContextCompat.checkSelfPermission( QrCodeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED){
                    requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                }else {
                    uri=imgUtil.saveImageToGallery( QrCodeActivity.this,mSavedBitmap );
                    shareImg( "分享二维码",null,null,uri );
                }
            }
        } );

        save.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                ImgUtil imgUtil=new ImgUtil();
                if (ContextCompat.checkSelfPermission( QrCodeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED){
                    requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                }else {
                    imgUtil.saveImageToGallery( QrCodeActivity.this,mSavedBitmap );
                    showToast( "以保存到手机" );
                }
            }
        } );

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
            case 2:
                ImgUtil imgUtil=new ImgUtil();
                imgUtil.saveImageToGallery( QrCodeActivity.this,mSavedBitmap );
                showToast( "以保存到手机" );
                break;
            case 3:
                ImgUtil imgUtil1=new ImgUtil();
                uri=imgUtil1.saveImageToGallery( QrCodeActivity.this,mSavedBitmap );
                shareImg( "分享二维码",null,null,uri );
                break;
        }

    }

    @Override
    public void setContentView() {
    }

    @Override
    public void initViews() {
        iv=findViewById( R.id.iv_qr_detail );
        titlename=findViewById( R.id.chatting_title_name );
        save=findViewById( R.id.button_save_qr_code );
        share=findViewById( R.id.button_share_qr_code );
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
    }

    public void generateBitmap(String mcontent) {
        QRCodeUtil qrCodeUtil=new QRCodeUtil();
        Bitmap qrBitmap = qrCodeUtil.generateBitmap(mcontent,450, 450);
        mSavedBitmap=qrBitmap;
        iv.setImageBitmap(qrBitmap);
    }

    public void logoBitmap(String mcontent) {
        QRCodeUtil qrCodeUtil=new QRCodeUtil();
        Bitmap qrBitmap = qrCodeUtil.generateBitmap(mcontent,450, 450);
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_qr_center);
        Bitmap bitmap = qrCodeUtil.addLogo(qrBitmap, logoBitmap);
        mSavedBitmap=bitmap;
        iv.setImageBitmap(bitmap);
    }

    /**
     * 分享图片和文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param subject
     *            主题
     * @param content
     *            分享内容（文字）
     * @param uri
     *            图片资源URI
     */
    private void shareImg(String dlgTitle, String subject, String content, Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (content != null && !"".equals(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
        }

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }

    /**
     * 分享文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param subject
     *            主题
     * @param content
     *            分享内容（文字）
     */
    private void shareText(String dlgTitle, String subject, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity( Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }

}
