package com.gongyunhao.nowmeeting.Activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.test.ChattingItem;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.gongyunhao.nowmeeting.view.DrawView;
import com.gongyunhao.nowmeeting.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.raphets.roundimageview.RoundImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{
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
    private String cameraPath;
    private String picturePath;
    private static final int  RC_CAMERA = 1000;
    private static final int RC_ALBUM = 1001;
    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CAMERA_REQUEST_CODE = 2;
    private String Tag = "SignInActivity";
    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath()+"/NowMeeting/camera/";// 拍照路径


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
        roundImageView_select_picture.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_signin_cancel:
                finish();
                break;
            case R.id.rv_select_photo_sign_in:
                askForAlbum();
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
                                            user_address_sign_in,company_sign_in,signature_sign_in,workplace_sign_in,picturePath );

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
        //头像
        roundImageView_select_picture=findViewById( R.id.rv_select_photo_sign_in );//头像avatar

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


    public void openAlbum(){

        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, ALBUM_REQUEST_CODE); // 打开相册

    }


    private String getImagePath(Intent data) {

        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;

    }



    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    public void openCamera(){

        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            cameraPath = SAVED_IMAGE_DIR_PATH + System.currentTimeMillis() + ".png";
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            String out_file_path = SAVED_IMAGE_DIR_PATH;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            } // 把文件地址转换成Uri格式
            Uri uri = Uri.fromFile(new File(cameraPath));
            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(SignInActivity.this, "请确认已经插入SD卡",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {

        if (requestCode==RC_CAMERA){
            Toast.makeText(SignInActivity.this,"照相机权限同意",Toast.LENGTH_SHORT).show();
        }else if(requestCode==RC_ALBUM){
            Toast.makeText(SignInActivity.this,"相册权限同意",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {

        if (requestCode==RC_CAMERA){
            Toast.makeText(SignInActivity.this,"照相机权限拒绝",Toast.LENGTH_SHORT).show();
        }else if(requestCode==RC_ALBUM){
            Toast.makeText(SignInActivity.this,"相册权限拒绝",Toast.LENGTH_SHORT).show();
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }


    @AfterPermissionGranted(RC_CAMERA)
    public void askForCamera(){

        if (EasyPermissions.hasPermissions(SignInActivity.this, Manifest.permission.CAMERA)){
            openCamera();
        }else{
            PermissionRequest request = new PermissionRequest.Builder(SignInActivity.this,RC_CAMERA,Manifest.permission.CAMERA).build();
            EasyPermissions.requestPermissions(request);
        }

    }

    @AfterPermissionGranted(RC_ALBUM)
    public void askForAlbum(){

        if (EasyPermissions.hasPermissions(SignInActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            openAlbum();
        }else{
            PermissionRequest request = new PermissionRequest.Builder(SignInActivity.this,RC_ALBUM,Manifest.permission.WRITE_EXTERNAL_STORAGE).build();
            EasyPermissions.requestPermissions(request);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.

        }

        if (resultCode == RESULT_OK) {
            if (requestCode == ALBUM_REQUEST_CODE) {
                try {
                    picturePath = getImagePath(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
