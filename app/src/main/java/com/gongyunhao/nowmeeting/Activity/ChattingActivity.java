package com.gongyunhao.nowmeeting.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gongyunhao.nowmeeting.Adapter.ChattingRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.deal.KeyBoardObserver;
import com.gongyunhao.nowmeeting.deal.MeasureLinearLayout;
import com.gongyunhao.nowmeeting.deal.SharePrefenceUtils;
import com.gongyunhao.nowmeeting.test.ChattingItem;
import com.gongyunhao.nowmeeting.util.KeyBoardUtils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class ChattingActivity extends BaseActivity implements View.OnTouchListener,KeyBoardObserver,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener,EasyPermissions.PermissionCallbacks{

    private ImageButton ib_more_emoji,ib_more_function;
    private ImageButton imageButtonBack;
    private TextView textViewChattingName;
    private List<ChattingItem> chattingItems;
    private Context mContext;
    private RecyclerView recyclerView;
    private ChattingRecyclerviewAdapter chattingRecyclerviewAdapter;
    private Conversation conversation;
    private String userName;
    private String messageContent;
    private String myName;
    private EmojiconEditText emojiconEditText;
    private MeasureLinearLayout rootLayout;
    private SwipeRefreshLayout swipeLayout;
    private FrameLayout pannel,emojiframe;
    private EmojiconEditText mEditEmojicon;
    private LinearLayout linear_more_function;
    private boolean isEmoji=false;
    private SimpleDateFormat format;
    private static final int  RC_CAMERA = 1000;
    private static final int RC_ALBUM = 1001;
    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath()+"/NowMeeting/camera/";// 拍照路径
    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CROP_REQUEST = 2;
    public final static int CAMERA_REQUEST_CODE = 3;
    private String cameraPath;
    private String picturePath;
    private Message message;
    private RelativeLayout relativeLayoutChooseAlbum;
    private RelativeLayout relativeLayoutCamera;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JMessageClient.registerEventReceiver(this);
        setEmojiconFragment(true);
        //初始化高度，和软键盘一致，初值为手机高度一半
        pannel.getLayoutParams().height = SharePrefenceUtils.getKeyBoardHeight(this);
        rootLayout.getKeyBoardObservable().register(this);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeLayout.setRefreshing(false);
            }
        });

        emojiconEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER&&event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ChattingActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    sendMessage();
                }
                return false;
            }
        });

    }

    public void sendMessage(){

        messageContent = emojiconEditText.getText().toString();
        Message message = conversation.createSendMessage(new TextContent(messageContent));
        message.setOnSendCompleteCallback(new  BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseDesc) {
                if (responseCode == 0) {
                    Log.d(Tag,"---->消息发送成功");

                    ChattingItem chattingItem = new ChattingItem();
                    chattingItem.setViewType(ChattingItem.RIGHT);
                    chattingItem.setChattingMessage(messageContent);
                    chattingItem.setPictureId(R.drawable.head2);
                    chattingItems.add(chattingItem);
                    chattingRecyclerviewAdapter.notifyDataSetChanged();
                    emojiconEditText.setText("");

                } else {
                    Log.d(Tag,"---->消息发送失败");
                    Toast.makeText(ChattingActivity.this,"消息发送失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        MessageSendingOptions options = new MessageSendingOptions();
        options.setRetainOffline(true);
        JMessageClient.sendMessage(message,options);

    }


    public void sendPicture(){

        File file = new File(picturePath);
        try{
            message = conversation.createSendMessage(new ImageContent(file));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

                if (i==0){

                    ChattingItem chattingItem = new ChattingItem();
                    chattingItem.setViewType(ChattingItem.RIGHT_PHOTO);
                    chattingItem.setPhotoPath(picturePath);
                    chattingItem.setPictureId(R.drawable.head2);
                    chattingItems.add(chattingItem);
                    chattingRecyclerviewAdapter.notifyDataSetChanged();
                    Log.d(Tag,"---->图片发送成功");

                }else {

                    Log.d(Tag,"---->图片发送失败");

                }

            }
        });
        MessageSendingOptions messageSendingOptions = new MessageSendingOptions();
        messageSendingOptions.setRetainOffline(true);
        JMessageClient.sendMessage(message,messageSendingOptions);


        }




    //EmojiconsFragment表情显示的fragment
    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    //表情点击回调
    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }
    //删除表情点击回调
    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);


    }

    @Override
    public void onBackPressed() {

        Message message = conversation.getLatestMessage();
        UserInfo userInfo = message.getFromUser();

        if (message.getContent() instanceof TextContent){

            TextContent textContent = (TextContent)message.getContent();
            Intent intent = new Intent();
            intent.putExtra("type","text");
            intent.putExtra("title",conversation.getTitle());
            intent.putExtra("message",textContent.getText());
            intent.putExtra("time",format.format(message.getCreateTime()));
            intent.putExtra("userName",userInfo.getUserName());
            setResult(RESULT_OK,intent);
            finish();


        }else {

            Intent intent = new Intent();
            intent.putExtra("type","image");
            intent.putExtra("title",conversation.getTitle());
            intent.putExtra("time",format.format(message.getCreateTime()));
            intent.putExtra("userName",userInfo.getUserName());
            setResult(RESULT_OK,intent);
            finish();


        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.choose_album:
                askForAlbum();
                break;

            case R.id.camera:
                askForCamera();
                break;

            case R.id.chatting_back:
                Message message = conversation.getLatestMessage();
                UserInfo userInfo = message.getFromUser();

                if (message.getContent() instanceof TextContent){

                    TextContent textContent = (TextContent)message.getContent();
                    Intent intent = new Intent();
                    intent.putExtra("type","text");
                    intent.putExtra("title",conversation.getTitle());
                    intent.putExtra("message",textContent.getText());
                    intent.putExtra("time",format.format(message.getCreateTime()));
                    intent.putExtra("userName",userInfo.getUserName());
                    setResult(RESULT_OK,intent);
                    finish();


                }else {

                    Intent intent = new Intent();
                    intent.putExtra("type","image");
                    intent.putExtra("title",conversation.getTitle());
                    intent.putExtra("time",format.format(message.getCreateTime()));
                    intent.putExtra("userName",userInfo.getUserName());
                    setResult(RESULT_OK,intent);
                    finish();


                }
                break;

            case R.id.ib_chatting_more_function:
                if (isEmoji) {
                    //想要显示面板
                    if (rootLayout.getKeyBoardObservable().isKeyBoardVisibile()) {
                        //当前软键盘为 挂起状态
                        //隐藏软键盘并显示面板
                        mEditEmojicon.clearFocus();
                        KeyBoardUtils.hideKeyboard(mEditEmojicon);
                    } else {
                        //显示面板
                        pannel.setVisibility(View.VISIBLE);
                        linear_more_function.setVisibility( View.VISIBLE );
                        emojiframe.setVisibility( View.GONE );
                    }
                    isEmoji=!isEmoji;
                } else {
                    //想要关闭面板
                    //挂起软键盘，并隐藏面板
                    mEditEmojicon.requestFocus();
                    KeyBoardUtils.showKeyboard(mEditEmojicon);
                    isEmoji=!isEmoji;

                }
                break;

            case R.id.ib_chatting_more_emoji:
                isEmoji=!isEmoji;
                if (isEmoji) {
                    //想要显示面板
                    if (rootLayout.getKeyBoardObservable().isKeyBoardVisibile()) {
                        //当前软键盘为 挂起状态
                        //隐藏软键盘并显示面板
                        mEditEmojicon.clearFocus();
                        KeyBoardUtils.hideKeyboard(mEditEmojicon);
                    } else {
                        //显示面板
                        pannel.setVisibility(View.VISIBLE);
                        linear_more_function.setVisibility( View.GONE );
                        emojiframe.setVisibility( View.VISIBLE );
                    }
                } else {
                    //想要关闭面板
                    //挂起软键盘，并隐藏面板
                    mEditEmojicon.requestFocus();
                    KeyBoardUtils.showKeyboard(mEditEmojicon);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.chatting_recyclerview:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //点击列表部分就清除焦点并初始化状态
                    if (pannel.getVisibility() == View.VISIBLE) {
                        pannel.setVisibility(View.GONE);
                        isEmoji=false;
                    } else {
                        mEditEmojicon.clearFocus();
                        KeyBoardUtils.hideKeyboard(mEditEmojicon);
                    }
                }
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_chatting);
    }

    @Override
    public void initViews() {

        imageButtonBack = findViewById( R.id.chatting_back );
        recyclerView = findViewById(R.id.chatting_recyclerview);
        textViewChattingName = (TextView)findViewById(R.id.chatting_title_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        chattingItems = new ArrayList<>();
        mContext = this;
        chattingRecyclerviewAdapter = new ChattingRecyclerviewAdapter(mContext,chattingItems);
        recyclerView.setAdapter(chattingRecyclerviewAdapter);
        rootLayout =findViewById(R.id.root_layout);
        swipeLayout =findViewById(R.id.swipe_layout);
        linear_more_function=findViewById( R.id.linear_more_function );
        ib_more_function=findViewById( R.id.ib_chatting_more_function );
        emojiframe=findViewById( R.id.emojicons );
        pannel =findViewById(R.id.pannel);
        ib_more_emoji=findViewById( R.id.ib_chatting_more_emoji );
        mEditEmojicon=findViewById( R.id.et_chatting );
        emojiconEditText = (EmojiconEditText)findViewById(R.id.et_chatting);
        relativeLayoutChooseAlbum = (RelativeLayout)findViewById(R.id.choose_album);
        relativeLayoutCamera = (RelativeLayout)findViewById(R.id.camera);

    }

    @Override
    public void initListeners() {

        imageButtonBack.setOnClickListener(this);
        ib_more_function.setOnClickListener(this);
        ib_more_emoji.setOnClickListener(this);
        recyclerView.setOnTouchListener(this);
        relativeLayoutChooseAlbum.setOnClickListener(this);
        relativeLayoutCamera.setOnClickListener(this);

    }

    @Override
    public void initData() {

        format = new SimpleDateFormat("HH:mm:ss");
        UserInfo userInfo = JMessageClient.getMyInfo();
        myName = userInfo.getUserName();
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        textViewChattingName.setText(userName);
        conversation = JMessageClient.getSingleConversation(userName);
        List<Message> messageList = conversation.getAllMessage();
        for (int i=0 ; i<messageList.size() ; i++){

            if (messageList.get(i).getContent() instanceof TextContent){

                Log.d( Tag,""+messageList.size() );
                UserInfo userInfo1 = messageList.get(i).getFromUser();
                if (userInfo1.getUserName().equals(myName)){
                    ChattingItem chattingItem = new ChattingItem();
                    TextContent textContent = (TextContent)messageList.get(i).getContent();
                    Log.d( Tag,textContent.getText() );
                    chattingItem.setViewType(ChattingItem.RIGHT);
                    chattingItem.setChattingMessage(textContent.getText());
                    chattingItem.setPictureId(R.drawable.head2);
                    chattingItems.add(chattingItem);
                }else{
                    ChattingItem chattingItem = new ChattingItem();
                    TextContent textContent = (TextContent)messageList.get(i).getContent();
                    Log.d( Tag,textContent.getText() );
                    chattingItem.setViewType(ChattingItem.LEFT);
                    chattingItem.setChattingMessage(textContent.getText());
                    chattingItem.setPictureId(R.drawable.head1);
                    chattingItems.add(chattingItem);
                }

            }else {

                Log.d( Tag,""+messageList.size() );
                UserInfo userInfo1 = messageList.get(i).getFromUser();
                if (userInfo1.getUserName().equals(myName)){
                    ImageContent imageContent = (ImageContent) messageList.get(i).getContent();
                    ChattingItem chattingItem = new ChattingItem();
                    chattingItem.setPhotoPath(imageContent.getLocalThumbnailPath());
                    chattingItem.setViewType(ChattingItem.RIGHT_PHOTO);
                    chattingItem.setPictureId(R.drawable.head2);
                    chattingItems.add(chattingItem);
                }else{
                    ImageContent imageContent = (ImageContent) messageList.get(i).getContent();
                    ChattingItem chattingItem = new ChattingItem();
                    chattingItem.setPhotoPath(imageContent.getLocalThumbnailPath());
                    chattingItem.setViewType(ChattingItem.LEFT_PHOTO);
                    chattingItem.setPictureId(R.drawable.head1);
                    chattingItems.add(chattingItem);
                }

            }


        }

        chattingRecyclerviewAdapter.notifyDataSetChanged();

    }

    public void onEventMainThread(MessageEvent event){
        //do your own business
        Message msg = event.getMessage();
        switch (msg.getContentType()){

            case text:

                    TextContent textContent = (TextContent)msg.getContent();
                    ChattingItem chattingItem = new ChattingItem();
                    chattingItem.setViewType(ChattingItem.LEFT);
                    chattingItem.setChattingMessage(textContent.getText());
                    chattingItem.setPictureId(R.drawable.head1);
                    chattingItems.add(chattingItem);
                    chattingRecyclerviewAdapter.notifyDataSetChanged();

                break;

            case image:

                ImageContent imageContent = (ImageContent)msg.getContent();
                String imagePath = imageContent.getLocalThumbnailPath();
                ChattingItem chattingItem1 = new ChattingItem();
                chattingItem1.setViewType(ChattingItem.LEFT_PHOTO);
                chattingItem1.setPhotoPath(imagePath);
                chattingItem1.setPictureId(R.drawable.head1);
                chattingItems.add(chattingItem1);
                chattingRecyclerviewAdapter.notifyDataSetChanged();

                break;
            default:
                break;


        }

    }

    @Override
    public void update(boolean keyBoardVisibile, int keyBoardHeight) {
        if (keyBoardVisibile) {
            //软键盘挂起
            pannel.setVisibility(View.GONE);
            isEmoji=false;
        } else {
            //回复原样
            if (isEmoji) {
                if (pannel.getLayoutParams().height != keyBoardHeight) {
                    pannel.getLayoutParams().height = keyBoardHeight;
                }
                pannel.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy( );
        JMessageClient.unRegisterEventReceiver(this);
        rootLayout.getKeyBoardObservable( ).unRegister( this );
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
            showToast( "请确认已经插入SD卡" );
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
            showToast( "照相机权限同意" );
        }else if(requestCode==RC_ALBUM){
            showToast( "相册权限同意" );
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {

        if (requestCode==RC_CAMERA){
            showToast( "照相机权限拒绝" );
        }else if(requestCode==RC_ALBUM){
            showToast( "相册权限拒绝" );
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }


    @AfterPermissionGranted(RC_CAMERA)
    public void askForCamera(){

        if (EasyPermissions.hasPermissions(ChattingActivity.this, Manifest.permission.CAMERA)){
            openCamera();
        }else{
            PermissionRequest request = new PermissionRequest.Builder(ChattingActivity.this,RC_CAMERA,Manifest.permission.CAMERA).build();
            EasyPermissions.requestPermissions(request);
        }

    }

    @AfterPermissionGranted(RC_ALBUM)
    public void askForAlbum(){

        if (EasyPermissions.hasPermissions(ChattingActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            openAlbum();
        }else{
            PermissionRequest request = new PermissionRequest.Builder(ChattingActivity.this,RC_ALBUM,Manifest.permission.WRITE_EXTERNAL_STORAGE).build();
            EasyPermissions.requestPermissions(request);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.

        }

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == ALBUM_REQUEST_CODE) {
                try {
                    picturePath = getImagePath(data);
                    sendPicture();
                    Log.d(Tag, "---->" + getImagePath(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST_CODE) {

                picturePath = cameraPath;
                sendPicture();
                Log.d(Tag, "---->" + cameraPath);

            }
        }

    }



}
