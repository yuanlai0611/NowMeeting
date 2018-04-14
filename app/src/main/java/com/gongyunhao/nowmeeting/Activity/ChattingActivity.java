package com.gongyunhao.nowmeeting.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
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

import static com.gongyunhao.nowmeeting.Activity.GroupChattingActivity.hideSoftInput;
import static com.gongyunhao.nowmeeting.Activity.GroupChattingActivity.showSoftInput;


public class ChattingActivity extends BaseActivity implements View.OnTouchListener,KeyBoardObserver,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener{
    private ImageButton ib_more_emoji,ib_more_function;

    private ImageButton imageButtonBack;
//    private ImageButton imageButtonSend;
    private EditText editTextContent;
    private TextView textViewChattingName;
    private List<ChattingItem> chattingItems;
    private Context mContext;
    private RecyclerView recyclerView;
    private ChattingRecyclerviewAdapter chattingRecyclerviewAdapter;
    private Conversation conversation;
    private String userName;
    private String messageContent;
    private String myName;
    private LinearLayout linearLayoutEdit;
    private Long groupId;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    recyclerView.scrollToPosition(chattingItems.size()-1);
                    break;
            }
        }

    };

    private MeasureLinearLayout rootLayout;
    private SwipeRefreshLayout swipeLayout;
    private FrameLayout pannel,emojiframe;
    private EmojiconEditText mEditEmojicon;
    private LinearLayout linear_more_function;

    private boolean isEmoji=false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEmojiconFragment(false);

        //初始化高度，和软键盘一致，初值为手机高度一半
        pannel.getLayoutParams().height = SharePrefenceUtils.getKeyBoardHeight(this);
        rootLayout.getKeyBoardObservable().register(this);

        ib_more_function.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
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
            }
        } );

        ib_more_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
                return false;
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_ENTER){
            Log.i("---","搜索操作执行");
            messageContent = editTextContent.getText().toString();
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
                        editTextContent.setText("");

                    } else {
                        Log.d(Tag,"---->消息发送失败");
                        Toast.makeText(ChattingActivity.this,"消息发送失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            MessageSendingOptions options = new MessageSendingOptions();
            options.setRetainOffline(true);
            JMessageClient.sendMessage(message,options);
            return true;
        }
        return false;
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
        JMessageClient.registerEventReceiver(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.chatting_recyclerview:
                hideSoftInput(ChattingActivity.this, editTextContent);
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.chatting_back:
                finish();
                break;
            case R.id.layout_edit:
                editTextContent.requestFocus();
                showSoftInput(ChattingActivity.this, editTextContent);
                handler.sendEmptyMessageDelayed(0,250);

                break;
            default:
                break;
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_chatting);
    }

    @Override
    public void initViews() {

        imageButtonBack = findViewById( R.id.chatting_back );
//        imageButtonSend = findViewById(R.id.send);
        recyclerView = findViewById(R.id.chatting_recyclerview);
        editTextContent = findViewById(R.id.edit_content);
        textViewChattingName = (TextView)findViewById(R.id.chatting_title_name);
        linearLayoutEdit = (LinearLayout)findViewById(R.id.layout_edit);
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

    }

    @Override
    public void initListeners() {

        imageButtonBack.setOnClickListener(this);
//        imageButtonSend.setOnClickListener(this);

    }

    @Override
    public void initData() {


        UserInfo userInfo = JMessageClient.getMyInfo();
        myName = userInfo.getUserName();

        Intent intent = getIntent();

        userName = intent.getStringExtra("userName");
        textViewChattingName.setText(userName);
        conversation = JMessageClient.getSingleConversation(userName);


        List<Message> messageList = conversation.getAllMessage();
        for (int i=0 ; i<messageList.size() ; i++){
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

    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}
