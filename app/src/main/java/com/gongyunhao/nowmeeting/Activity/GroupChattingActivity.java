package com.gongyunhao.nowmeeting.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cooltechworks.views.ScratchTextView;
import com.gongyunhao.nowmeeting.Adapter.ChattingRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.deal.KeyBoardObserver;
import com.gongyunhao.nowmeeting.deal.MeasureLinearLayout;
import com.gongyunhao.nowmeeting.deal.SharePrefenceUtils;
import com.gongyunhao.nowmeeting.test.ChattingItem;
import com.gongyunhao.nowmeeting.util.KeyBoardUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;


public class GroupChattingActivity extends BaseActivity implements View.OnTouchListener,KeyBoardObserver,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener,EasyPermissions.PermissionCallbacks{

    private ImageButton ib_more_emoji,ib_more_function;
    private ImageButton imageButtonBack;
    private TextView textViewChattingName;
    private List<ChattingItem> chattingItems;
    private Context mContext;
    private RecyclerView recyclerView;
    private ChattingRecyclerviewAdapter chattingRecyclerviewAdapter;
    private Conversation conversation;
    private String groupId;
    private String messageContent;
    private String myName;
    private EmojiconEditText emojiconEditText;
    private MeasureLinearLayout rootLayout;
    private SwipeRefreshLayout swipeLayout;
    private FrameLayout pannel,emojiframe;
    private LinearLayout linear_more_function;
    private RelativeLayout relativeLayoutLottery,relativeLayoutStatistics,relativeLayoutChooseAlbum,relativeLayoutCamera;
    private boolean isEmoji=false;
    private boolean isMore = false;
    private static final int  RC_CAMERA = 1000;
    private static final int RC_ALBUM = 1001;
    public static String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().getPath()+"/NowMeeting/camera/";// 拍照路径
    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CROP_REQUEST = 2;
    public final static int CAMERA_REQUEST_CODE = 3;
    public final static int LOTTERYRESULT = 4;
    public final static int STATISTICS = 5;
    private String Tag = "GroupChattingActivity";
    private SimpleDateFormat format;
    private String cameraPath;
    private String picturePath;
    private Message message;
    private String lotterryResult;
    private boolean isLottery;
    private LinearLayoutManager linearLayout;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化高度，和软键盘一致，初值为手机高度一半
        pannel.getLayoutParams().height = SharePrefenceUtils.getKeyBoardHeight(this);
        rootLayout.getKeyBoardObservable().register(this);
        setEmojiconFragment(false);

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
                            .hideSoftInputFromWindow(GroupChattingActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    sendMessage();
                }
                return false;
            }
        });


        chattingRecyclerviewAdapter.setOnItemClickListener(new ChattingRecyclerviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (chattingItems.get(position).getViewType()==ChattingItem.LEFT_LOTTERY||chattingItems.get(position).getViewType()==ChattingItem.RIGHT_LOTTERY){

                    List<String> nameList = chattingItems.get(position).getNameList();
                    Log.d("lotteryResult","---->"+nameList);
                    for (int i=0 ; i<nameList.size() ; i++){
                        if (nameList.get(i).equals(myName)){

                            isLottery = true;

                        }
                    }

                    if (isLottery){
                        show1("恭喜你，中奖!!!!");
                    }else {
                        show1("很遗憾，你错过了大奖...");
                    }

                }else if (chattingItems.get(position).getViewType()==ChattingItem.LEFT_VOTE||chattingItems.get(position).getViewType()==ChattingItem.RIGHT_VOTE){

                    Intent intent = new Intent(GroupChattingActivity.this,SubmitVoteActivity.class);
                    intent.putExtra("voteContent",chattingItems.get(position).getVoteContent());
                    intent.putExtra("voteName",chattingItems.get(position).getVoteName());
                    intent.putExtra("voteId",chattingItems.get(position).getVoteId());

                    intent.putExtra("conferenceName",conversation.getTitle());
                    startActivity(intent);

                }

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
                    ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(chattingItems.size()-1,0);
                    emojiconEditText.setText("");

                } else {
                    Log.d(Tag,"---->消息发送失败");
                    Toast.makeText(GroupChattingActivity.this,"消息发送失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        MessageSendingOptions options = new MessageSendingOptions();
//        options.setRetainOffline(true);
        JMessageClient.sendMessage(message);

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
                    ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(chattingItems.size()-1,0);
                    Log.d(Tag,"---->图片发送成功");

                }else {

                    Log.d(Tag,"---->图片发送失败");

                }

            }
        });
//        MessageSendingOptions messageSendingOptions = new MessageSendingOptions();
//        messageSendingOptions.setRetainOffline(true);
        JMessageClient.sendMessage(message);


    }


    private void show1(String words) {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_scratch_card, null);
        ScratchTextView scratchTextView = contentView.findViewById(R.id.result);
        scratchTextView.setText(words);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }




    @Override
    public void onBackPressed() {

        Message message = conversation.getLatestMessage();
        UserInfo userInfo = message.getFromUser();
        if (message.getContent() instanceof  ImageContent){

           Intent intent = new Intent();
           intent.putExtra("type","image");
           intent.putExtra("userName",userInfo.getUserName());
           intent.putExtra("time",format.format(message.getCreateTime()));
           intent.putExtra("groupId",groupId);
           setResult(RESULT_OK,intent);
           finish();

        }else if (message.getContent() instanceof  TextContent){

            TextContent textContent = (TextContent)message.getContent();
            Intent intent = new Intent();
            if (textContent.getText().startsWith("lottery")){
             intent.putExtra("isLottery",true);
            }
            if (textContent.getText().startsWith("vote")){
                intent.putExtra("isVote",true);
            }
            intent.putExtra("type","text");
            intent.putExtra("userName",userInfo.getUserName());
            intent.putExtra("message",textContent.getText());
            intent.putExtra("time",format.format(message.getCreateTime()));
            intent.putExtra("groupId",groupId);
            setResult(RESULT_OK,intent);
            finish();

        }


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
        EmojiconsFragment.input(emojiconEditText, emojicon);
    }

    //删除表情点击回调
    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(emojiconEditText);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.chatting_back:
                Message message = conversation.getLatestMessage();
                UserInfo userInfo = message.getFromUser();
                if (message.getContent() instanceof  ImageContent){

                    Intent intent = new Intent();
                    intent.putExtra("type","image");
                    intent.putExtra("userName",userInfo.getUserName());
                    intent.putExtra("time",format.format(message.getCreateTime()));
                    intent.putExtra("groupId",groupId);
                    setResult(RESULT_OK,intent);
                    finish();

                }else if (message.getContent() instanceof  TextContent){

                    TextContent textContent = (TextContent)message.getContent();
                    Intent intent = new Intent();
                    if (textContent.getText().startsWith("lottery")){
                        intent.putExtra("isLottery",true);
                    }
                    if (textContent.getText().startsWith("vote")){
                        intent.putExtra("isVote",true);
                    }
                    intent.putExtra("type","text");
                    intent.putExtra("userName",userInfo.getUserName());
                    intent.putExtra("message",textContent.getText());
                    intent.putExtra("time",format.format(message.getCreateTime()));
                    intent.putExtra("groupId",groupId);
                    setResult(RESULT_OK,intent);
                    finish();

                }
                break;

            case R.id.et_chatting:
                break;
            case R.id.lottery:
                Intent intent = new Intent(GroupChattingActivity.this,LotteryEditActivity.class);
                intent.putExtra("conferenceName",conversation.getTitle());
                startActivityForResult(intent,LOTTERYRESULT);
                break;
            case R.id.statistics:
                Intent intent1 = new Intent(GroupChattingActivity.this,CreateVoteActivity.class);
                intent1.putExtra("conferenceName",conversation.getTitle());
                intent1.putExtra("groupId",groupId);
                intent1.putExtra("userName",myName);
                startActivityForResult(intent1,STATISTICS);
                break;
            case R.id.choose_album:
                askForAlbum();
                break;
            case R.id.camera:
                askForCamera();
                break;
            case R.id.ib_chatting_more_function:

                if (isMore){

                    if (rootLayout.getKeyBoardObservable().isKeyBoardVisibile()){

                        if (isEmoji){

                            //不存在的

                        }else {

                            //更多面板和键盘可见
                            emojiconEditText.clearFocus();
                            KeyBoardUtils.hideKeyboard(emojiconEditText);
                            isEmoji = false;
                            isMore = true;

                        }

                    }else {

                        if (isEmoji){

                            //也是不存在的

                        }else {

                        pannel.setVisibility(View.GONE);
                        linear_more_function.setVisibility(View.GONE);
                        isMore =false;
                        isEmoji =false;

                        }

                    }

                }else{

                    if (rootLayout.getKeyBoardObservable().isKeyBoardVisibile()){

                        if (isEmoji){

                            emojiconEditText.clearFocus();
                            KeyBoardUtils.hideKeyboard(emojiconEditText);
                            emojiframe.setVisibility(View.GONE);
                            linear_more_function.setVisibility(View.VISIBLE);
                            isMore = true;
                            isEmoji = false;

                        }else {

                            emojiconEditText.clearFocus();
                            KeyBoardUtils.hideKeyboard(emojiconEditText);
                            pannel.setVisibility(View.VISIBLE);
                            linear_more_function.setVisibility(View.VISIBLE);
                            isMore = true;
                            isEmoji = false;

                        }

                    }else {

                        if (isEmoji){

                            emojiframe.setVisibility(View.GONE);
                            linear_more_function.setVisibility(View.VISIBLE);
                            isMore = true;
                            isEmoji = false;

                        }else {

                            pannel.setVisibility(View.VISIBLE);
                            linear_more_function.setVisibility(View.VISIBLE);
                            Toast.makeText(GroupChattingActivity.this,"调用了更多的点击事件",Toast.LENGTH_SHORT).show();
                            isMore = true;
                            isEmoji =false;

                        }

                    }

                }

                break;

            case R.id.ib_chatting_more_emoji:


                if (isEmoji){

                    if(rootLayout.getKeyBoardObservable().isKeyBoardVisibile()){

                        if (isMore){



                        }else{

                            linear_more_function.setVisibility(View.GONE);
                            emojiconEditText.clearFocus();
                            KeyBoardUtils.hideKeyboard(emojiconEditText);
                            emojiframe.setVisibility(View.VISIBLE);
                            isEmoji = true;
                            isMore = false;

                        }

                    }else {

                        if (isMore){



                        }else{

                           emojiframe.setVisibility(View.GONE);
                           pannel.setVisibility(View.GONE);
                           isEmoji = false;
                           isMore = false;

                        }

                    }

                }else {

                    if(rootLayout.getKeyBoardObservable().isKeyBoardVisibile()){

                        if (isMore){

                          linear_more_function.setVisibility(View.GONE);
                          emojiframe.setVisibility(View.VISIBLE);
                          isEmoji = true;
                          isMore = false;

                        }else{

                          KeyBoardUtils.hideKeyboard(emojiconEditText);
                          emojiconEditText.clearFocus();
                          emojiframe.setVisibility(View.VISIBLE);
                          pannel.setVisibility(View.VISIBLE);
                          isEmoji = true;
                          isMore =false;

                        }

                    }else {

                        if (isMore){


                            linear_more_function.setVisibility(View.GONE);
                            emojiframe.setVisibility(View.VISIBLE);
                            isEmoji = true;
                            isMore =false;


                        }else{

                            pannel.setVisibility(View.VISIBLE);
                            emojiframe.setVisibility(View.VISIBLE);
                            isEmoji = true;
                            isMore = false;

                        }

                    }

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
                        emojiconEditText.clearFocus();
                        KeyBoardUtils.hideKeyboard(emojiconEditText);
                    }
                }
                break;
            case R.id.et_chatting:


                Log.d(Tag,"---->触摸了输入框");

                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_group_chatting);
    }

    @Override
    public void initViews() {

        JMessageClient.registerEventReceiver(this);
        imageButtonBack = findViewById( R.id.chatting_back );
        recyclerView = findViewById(R.id.chatting_recyclerview);
        textViewChattingName = (TextView)findViewById(R.id.chatting_title_name);
        relativeLayoutLottery = (RelativeLayout) findViewById(R.id.lottery);
        relativeLayoutStatistics = (RelativeLayout) findViewById(R.id.statistics);
        relativeLayoutChooseAlbum = (RelativeLayout) findViewById(R.id.choose_album);
        relativeLayoutCamera = (RelativeLayout) findViewById(R.id.camera);
        linearLayout = new LinearLayoutManager(this);
//        linearLayout.setStackFromEnd(true);
//        recyclerView.setItemViewCacheSize(100);
        recyclerView.setLayoutManager(linearLayout);
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
        emojiconEditText = (EmojiconEditText)findViewById(R.id.et_chatting);

    }

    @Override
    public void initListeners() {

        imageButtonBack.setOnClickListener(this);
        ib_more_function.setOnClickListener(this);
        ib_more_emoji.setOnClickListener(this);
        recyclerView.setOnTouchListener(this);
        relativeLayoutLottery.setOnClickListener(this);
        relativeLayoutStatistics.setOnClickListener(this);
        relativeLayoutChooseAlbum.setOnClickListener(this);
        relativeLayoutCamera.setOnClickListener(this);
        emojiconEditText.setOnClickListener(this);
        emojiconEditText.setOnTouchListener(this);

    }

    @Override
    public void initData() {

        format = new SimpleDateFormat("HH:mm:ss");
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupNumber");
        conversation = JMessageClient.getGroupConversation(Long.parseLong(groupId));
        textViewChattingName.setText(conversation.getTitle());
        List<Message> messageList = conversation.getAllMessage();

        myName = JMessageClient.getMyInfo().getUserName();

        Log.d(Tag,"---->"+myName);

        for (int i=0 ; i<messageList.size() ; i++){

            if (!(messageList.get(i).getContent() instanceof EventNotificationContent)) {

                if (messageList.get(i).getContent() instanceof ImageContent){

                    UserInfo userInfo = messageList.get(i).getFromUser();
                    if (userInfo.getUserName().equals(myName)){

                        ImageContent imageContent = (ImageContent) messageList.get(i).getContent();
                        ChattingItem chattingItem = new ChattingItem();
                        chattingItem.setViewType(ChattingItem.RIGHT_PHOTO);
                        chattingItem.setPhotoPath(imageContent.getLocalThumbnailPath());
                        chattingItem.setPictureId(R.drawable.head1);
                        chattingItems.add(chattingItem);

                    }else {

                        ImageContent imageContent = (ImageContent) messageList.get(i).getContent();
                        ChattingItem chattingItem = new ChattingItem();
                        chattingItem.setViewType(ChattingItem.LEFT_PHOTO);
                        chattingItem.setPhotoPath(imageContent.getLocalThumbnailPath());
                        chattingItem.setPictureId(R.drawable.head1);
                        chattingItems.add(chattingItem);

                    }

                }else if (messageList.get(i).getContent() instanceof TextContent){

                    Log.d(Tag, "" + messageList.size());
                    UserInfo userInfo1 = messageList.get(i).getFromUser();

                    Log.d(Tag, "---->" + userInfo1.getUserName());
                    TextContent textContent = (TextContent) messageList.get(i).getContent();
                    if (textContent.getText().startsWith("lottery")){

                        Log.d(Tag,"---->"+textContent.getText());

                        if (userInfo1.getUserName().equals(myName)) {
                            ChattingItem chattingItem = new ChattingItem();
                            List<String> nameList = getEveryName(textContent.getText());
                            int number = nameList.size();
                            chattingItem.setViewType(ChattingItem.RIGHT_LOTTERY);
                            chattingItem.setLotteryName(nameList.get(number-2));
                            chattingItem.setLotteryNumber("今晚会诞生"+nameList.get(number-1)+"位幸运儿");
                            chattingItem.setNameList(nameList.subList(0,number-2));
                            chattingItem.setPictureId(R.drawable.head2);
                            chattingItems.add(chattingItem);
                        } else {
                            ChattingItem chattingItem = new ChattingItem();
                            List<String> nameList = getEveryName(textContent.getText());
                            int number = nameList.size();
                            chattingItem.setViewType(ChattingItem.LEFT_LOTTERY);
                            chattingItem.setLotteryName(nameList.get(number-2));
                            chattingItem.setLotteryNumber("今晚会诞生"+nameList.get(number-1)+"位幸运儿");
                            chattingItem.setNameList(nameList.subList(0,number-2));
                            chattingItem.setPictureId(R.drawable.head2);
                            chattingItems.add(chattingItem);
                        }

                    }else if (textContent.getText().startsWith("vote")){

                        if (userInfo1.getUserName().equals(myName)) {

                            ChattingItem chattingItem = new ChattingItem();
                            chattingItem.setViewType(ChattingItem.RIGHT_VOTE);
                            List<String> voteList = getEveryVoteName(textContent.getText());
                            chattingItem.setFirstOption(voteList.get(0));
                            chattingItem.setSecondOption(voteList.get(1));
                            chattingItem.setVoteName(voteList.get(voteList.size()-2));
                            chattingItem.setVoteId(voteList.get(voteList.size()-1));
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("vote");
                            for (int j=0 ; j<voteList.size()-2 ; j++){

                                stringBuilder.append(voteList.get(j));
                                stringBuilder.append('/');

                            }
                            chattingItem.setVoteContent(stringBuilder.toString());
                            chattingItem.setPictureId(R.drawable.head1);
                            chattingItems.add(chattingItem);

                        } else {

                            ChattingItem chattingItem = new ChattingItem();
                            chattingItem.setViewType(ChattingItem.LEFT_VOTE);
                            List<String> voteList = getEveryVoteName(textContent.getText());
                            chattingItem.setFirstOption(voteList.get(0));
                            chattingItem.setSecondOption(voteList.get(1));
                            chattingItem.setVoteName(voteList.get(voteList.size()-2));
                            chattingItem.setVoteId(voteList.get(voteList.size()-1));
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("vote");
                            for (int j=0 ; j<voteList.size()-2 ; j++){

                                stringBuilder.append(voteList.get(j));
                                stringBuilder.append('/');

                            }
                            chattingItem.setVoteContent(stringBuilder.toString());
                            chattingItem.setPictureId(R.drawable.head1);
                            chattingItems.add(chattingItem);

                        }


                    } else {
                        if (userInfo1.getUserName().equals(myName)) {
                            ChattingItem chattingItem = new ChattingItem();
                            Log.d(Tag, textContent.getText());
                            chattingItem.setViewType(ChattingItem.RIGHT);
                            chattingItem.setChattingMessage(textContent.getText());
                            chattingItem.setPictureId(R.drawable.head1);
                            chattingItems.add(chattingItem);
                        } else {
                            ChattingItem chattingItem = new ChattingItem();
                            Log.d(Tag, textContent.getText());
                            chattingItem.setViewType(ChattingItem.LEFT);
                            chattingItem.setChattingMessage(textContent.getText());
                            chattingItem.setPictureId(R.drawable.head1);
                            chattingItems.add(chattingItem);
                        }

                    }

                }



            }
        }

        chattingRecyclerviewAdapter.notifyDataSetChanged();

    }

    public void onEventMainThread(MessageEvent event){
        //do your own business
        Log.d(Tag,"---->收到消息");
        Message msg = event.getMessage();
        switch (msg.getContentType()){

            case text:

                TextContent textContent = (TextContent)msg.getContent();
                if (textContent.getText().startsWith("lottery")){

                    ChattingItem chattingItem = new ChattingItem();
                    chattingItem.setViewType(ChattingItem.LEFT_LOTTERY);
                    List<String> nameList = getEveryName(textContent.getText());
                    chattingItem.setPictureId(R.drawable.head1);
                    int number = nameList.size();
                    chattingItem.setLotteryName(nameList.get(number-2));
                    chattingItem.setLotteryNumber("今晚会诞生"+nameList.get(number-1)+"位幸运儿");
                    chattingItem.setNameList(nameList.subList(0,number-2));
                    chattingItems.add(chattingItem);

                }else if (textContent.getText().startsWith("vote")){

                    ChattingItem chattingItem = new ChattingItem();
                    chattingItem.setViewType(ChattingItem.LEFT_VOTE);
                    List<String> voteList = getEveryVoteName(textContent.getText());
                    chattingItem.setVoteName(voteList.get(voteList.size()-2));
                    chattingItem.setVoteId(voteList.get(voteList.size()-1));
                    chattingItem.setFirstOption(voteList.get(0));
                    chattingItem.setSecondOption(voteList.get(1));
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("vote");
                    for (int i=0 ; i<voteList.size()-2 ; i++){
                        stringBuilder.append(voteList.get(i));
                        stringBuilder.append('/');
                    }
                    chattingItem.setVoteContent(stringBuilder.toString());
                    chattingItem.setPictureId(R.drawable.head1);
                    chattingItems.add(chattingItem);

                } else{

                    ChattingItem chattingItem = new ChattingItem();
                    chattingItem.setViewType(ChattingItem.LEFT);
                    chattingItem.setChattingMessage(textContent.getText());
                    Log.d(Tag,textContent.getText());
                    chattingItem.setPictureId(R.drawable.head1);
                    chattingItems.add(chattingItem);

                }

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
            Toast.makeText(GroupChattingActivity.this, "请确认已经插入SD卡",
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
            Toast.makeText(GroupChattingActivity.this,"照相机权限同意",Toast.LENGTH_SHORT).show();
        }else if(requestCode==RC_ALBUM){
            Toast.makeText(GroupChattingActivity.this,"相册权限同意",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {

        if (requestCode==RC_CAMERA){
            Toast.makeText(GroupChattingActivity.this,"照相机权限拒绝",Toast.LENGTH_SHORT).show();
        }else if(requestCode==RC_ALBUM){
            Toast.makeText(GroupChattingActivity.this,"相册权限拒绝",Toast.LENGTH_SHORT).show();
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }


    @AfterPermissionGranted(RC_CAMERA)
    public void askForCamera(){

        if (EasyPermissions.hasPermissions(GroupChattingActivity.this, Manifest.permission.CAMERA)){
            openCamera();
        }else{
            PermissionRequest request = new PermissionRequest.Builder(GroupChattingActivity.this,RC_CAMERA,Manifest.permission.CAMERA).build();
            EasyPermissions.requestPermissions(request);
        }

    }

    @AfterPermissionGranted(RC_ALBUM)
    public void askForAlbum(){

        if (EasyPermissions.hasPermissions(GroupChattingActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            openAlbum();
        }else{
            PermissionRequest request = new PermissionRequest.Builder(GroupChattingActivity.this,RC_ALBUM,Manifest.permission.WRITE_EXTERNAL_STORAGE).build();
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
                    Log.d(Tag,"---->"+getImagePath(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (requestCode == CAMERA_REQUEST_CODE){

                    picturePath = cameraPath;
                    sendPicture();
                Log.d(Tag,"---->"+cameraPath);

            }else if (requestCode == LOTTERYRESULT){

              lotterryResult = data.getStringExtra("lotteryResult");
              final String lotteryName = data.getStringExtra("lotteryName");
              final String lotteryNumber = data.getStringExtra("lotteryNumber");
              Message message = conversation.createSendMessage(new TextContent(lotterryResult+lotteryName+"/"+lotteryNumber+"/"));
              message.setOnSendCompleteCallback(new BasicCallback() {
                  @Override
                  public void gotResult(int i, String s) {

                      if (i==0){

                          Log.d(Tag,"---->发送抽奖成功");
                          ChattingItem chattingItem = new ChattingItem();
                          chattingItem.setLotteryName(lotteryName);
                          chattingItem.setLotteryNumber("今晚会诞生"+lotteryNumber+"位幸运儿");
                          chattingItem.setViewType(ChattingItem.RIGHT_LOTTERY);
                          chattingItem.setNameList(getEveryName(lotterryResult));
                          chattingItem.setPictureId(R.drawable.head1);
                          chattingItems.add(chattingItem);
                          chattingRecyclerviewAdapter.notifyDataSetChanged();

                      }else {



                      }

                  }
              });
//              MessageSendingOptions messageSendingOptions = new MessageSendingOptions();
//              messageSendingOptions.setRetainOffline(true);
              JMessageClient.sendMessage(message);

            }else if (requestCode==STATISTICS){

                final String voteName = data.getStringExtra("voteName");
                final String voteFirstOption = data.getStringExtra("firstOption");
                final String voteSecondOption = data.getStringExtra("secondOption");
                final String voteContent = data.getStringExtra("voteList");
                final int voteId = data.getIntExtra("voteId",0);
                Message message = conversation.createSendMessage(new TextContent(voteContent+voteName+"/"+voteId+"/"));
                message.setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {

                        if (i==0){

                            Log.d(Tag,"---->发送投票成功");
                            ChattingItem chattingItem = new ChattingItem();
                            chattingItem.setViewType(ChattingItem.RIGHT_VOTE);
                            chattingItem.setFirstOption(voteFirstOption);
                            chattingItem.setSecondOption(voteSecondOption);
                            chattingItem.setVoteName(voteName);
                            chattingItem.setVoteId(String.valueOf(voteId));
                            chattingItem.setVoteContent(voteContent);
                            chattingItem.setPictureId(R.drawable.head1);
                            chattingItems.add(chattingItem);
                            chattingRecyclerviewAdapter.notifyDataSetChanged();

                        }else {

                            Log.d(Tag,"---->投票发送失败");

                        }

                    }
                });
//                MessageSendingOptions messageSendingOptions = new MessageSendingOptions();
//                messageSendingOptions.setRetainOffline(true);
                JMessageClient.sendMessage(message);



            }
        }

    }

    public List<String> getEveryName(String name){

        List<String> nameList = new ArrayList<>();

        if (name.startsWith("lottery")){

            name = name.replaceFirst("lottery","");

            StringBuilder stringBuilder = new StringBuilder();

            for (int i=0 ; i<name.length() ; i++){

                if (name.charAt(i)=='/'){

                    nameList.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();

                }else{

                    stringBuilder.append(name.charAt(i));

                }

            }

        }else{

            Log.d(Tag,"发送的消息格式有问题");

        }
        return nameList;

    }

    public List<String> getEveryVoteName(String name){

        List<String> nameList = new ArrayList<>();

        if (name.startsWith("vote")){

            name = name.replaceFirst("vote","");

            StringBuilder stringBuilder = new StringBuilder();

            for (int i=0 ; i<name.length() ; i++){

                if (name.charAt(i)=='/'){

                    nameList.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();

                }else{

                    stringBuilder.append(name.charAt(i));

                }

            }

        }else{

            Log.d(Tag,"发送的消息格式有问题");

        }
        return nameList;

    }

}
