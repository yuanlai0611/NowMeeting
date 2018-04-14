package com.gongyunhao.nowmeeting.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.gongyunhao.nowmeeting.test.ChattingItem;
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

public class GroupChattingActivity extends BaseActivity implements View.OnTouchListener{

    private ImageButton imageButtonBack;
    private ImageButton imageButtonSend;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.chatting_recyclerview:
                hideSoftInput(GroupChattingActivity.this, editTextContent);
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
            case R.id.send:
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
                            Toast.makeText(GroupChattingActivity.this,"消息发送失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                MessageSendingOptions options = new MessageSendingOptions();
                options.setRetainOffline(true);
                JMessageClient.sendMessage(message,options);

                break;
            case R.id.layout_edit:

                editTextContent.requestFocus();
                showSoftInput(GroupChattingActivity.this, editTextContent);
                handler.sendEmptyMessageDelayed(0,250);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_group_chatting);
    }

    @Override
    public void initViews() {

        imageButtonBack = findViewById( R.id.chatting_back );
        imageButtonSend = findViewById(R.id.send);
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

    }

    @Override
    public void initListeners() {

        imageButtonBack.setOnClickListener(this);
        imageButtonSend.setOnClickListener(this);

    }

    @Override
    public void initData() {


        UserInfo userInfo = JMessageClient.getMyInfo();
        myName = userInfo.getUserName();

        Intent intent = getIntent();
        groupId = Long.parseLong(intent.getStringExtra("groupNumber"));
        Log.d(Tag,"---->"+groupId);
        conversation = JMessageClient.getGroupConversation(groupId);
        textViewChattingName.setText(conversation.getTitle());

        List<Message> messageList = conversation.getAllMessage();
        Log.d(Tag,"---->"+messageList.size());


        for (int i=1 ; i<messageList.size() ; i++){
            UserInfo userInfo1 = messageList.get(i).getFromUser();
            TextContent textContent = (TextContent)messageList.get(i).getContent();
            Log.d(Tag,"---->"+textContent.getText());

            if (userInfo1.getUserName().equals(myName)){
                ChattingItem chattingItem = new ChattingItem();
                chattingItem.setViewType(ChattingItem.RIGHT);
                chattingItem.setChattingMessage(textContent.getText());
                chattingItem.setPictureId(R.drawable.head2);
                chattingItems.add(chattingItem);
            }else{
                ChattingItem chattingItem = new ChattingItem();
                chattingItem.setViewType(ChattingItem.LEFT);
                chattingItem.setChattingMessage(textContent.getText());
                chattingItem.setPictureId(R.drawable.head1);
                chattingItems.add(chattingItem);
            }
        }

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


    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}
