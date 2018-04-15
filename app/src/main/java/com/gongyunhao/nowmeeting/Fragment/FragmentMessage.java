package com.gongyunhao.nowmeeting.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Activity.ChattingActivity;
import com.gongyunhao.nowmeeting.Activity.GroupChattingActivity;
import com.gongyunhao.nowmeeting.Adapter.MessageRecyclerViewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseFragment;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.MessageItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

//    ┏┓　   ┏┓
// ┏━━┛┻━━━━━┛┻ ┓ 
// ┃　　　　　　 ┃  
// ┃　　　━　    ┃  
// ┃　＞　　＜　 ┃  
// ┃　　　　　　 ┃  
// ┃... ⌒ ...  ┃  
// ┃　　　　　 　┃  
// ┗━━━┓　　　┏━┛  
//     ┃　　　┃　  
//     ┃　　　┃  
//     ┃　　　┃  神兽保佑  
//     ┃　　　┃  代码无bug　　  
//     ┃　　　┃  
//     ┃　　　┗━━━━━━━━━┓
//     ┃　　　　　　　    ┣┓
//     ┃　　　　         ┏┛
//     ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
//       ┃ ┫ ┫   ┃ ┫ ┫
//       ┗━┻━┛   ┗━┻━┛
//
//  作者：棒棒小糖
//  來源：简书
//
//  Creste by GongYunHao on 2018/3/5
//

public class FragmentMessage extends BaseFragment{

    private Context mContext;
    private List<MessageItem> messageItemList;
    private RecyclerView recyclerView;
    private MessageRecyclerViewAdapter messageRecyclerViewAdapter;
    private String Tag = "FragmentMessage";
    private String myName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageItemList = new ArrayList<>();
        JMessageClient.registerEventReceiver(this);
        UserInfo userInfo1 = JMessageClient.getMyInfo();
        myName = userInfo1.getUserName();

        List<Conversation> conversationList = JMessageClient.getConversationList();

        for (int i=0 ; i<conversationList.size() ; i++){

            if (!(conversationList.get(i).getLatestMessage().getContent() instanceof EventNotificationContent)){

                MessageContent messageContent =  conversationList.get(i).getLatestMessage().getContent();
                TextContent textContent = (TextContent)messageContent;
                Log.d(Tag,"---->"+messageContent.toJson());
                Log.d(Tag,"---->"+textContent.getText());
                UserInfo userInfo = conversationList.get(i).getLatestMessage().getFromUser();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                Log.d(Tag,"---->"+userInfo);
                MessageItem messageItem = new MessageItem();
                messageItem.setConversation(conversationList.get(i));
                messageItem.setImageId(R.drawable.head1);
                switch (conversationList.get(i).getType()){
                    case single:
                        messageItem.setMessageType(MessageItem.SINGLE);
                        break;
                    case group:
                        messageItem.setMessageType(MessageItem.GROUP);
                        break;
                    default:
                        break;
                }

                UserInfo userInfo2 = conversationList.get(i).getLatestMessage().getFromUser();
                String userName = userInfo2.getUserName();

                if (userName.equals(myName)){
                    messageItem.setMessage("我："+textContent.getText());
                }else {
                    messageItem.setMessage(userName+"："+textContent.getText());
                }

                messageItem.setDate(format.format(conversationList.get(i).getLatestMessage().getCreateTime()));
                messageItem.setUserName(conversationList.get(i).getTitle());
                messageItemList.add(messageItem);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        Log.d(Tag,"---->onCreateView");

        initViews(view);
        setListener();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    @Override
    protected void requestData() {

    }

    //避免了每次都要请求网络
    @Override
    protected void initViews(View view) {

        recyclerView = view.findViewById(R.id.message_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        messageRecyclerViewAdapter = new MessageRecyclerViewAdapter(mContext,messageItemList);
        recyclerView.setAdapter(messageRecyclerViewAdapter);
        messageRecyclerViewAdapter.setmOnItemClickListener(new MessageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String userName = messageItemList.get(position).getUserName();

                if (messageItemList.get(position).getMessageType()==MessageItem.SINGLE){
                    Intent intent = new Intent(getActivity(), ChattingActivity.class);
                    intent.putExtra("userName",userName);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), GroupChattingActivity.class);
                    intent.putExtra("groupNumber",messageItemList.get(position).getConversation().getTargetId());
                    Log.d(Tag,"---->"+messageItemList.get(position).getConversation().getTargetId());
                    startActivity(intent);
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void setListener(){

    }

    public void onEventMainThread(MessageEvent event){
        //do your own business
        Message msg = event.getMessage();

        switch (msg.getTargetType()) {

            case single:

                switch (msg.getContentType()) {

                    case text:

                        TextContent textContent = (TextContent) msg.getContent();
                        UserInfo userInfo = msg.getFromUser();
                        String fromName = userInfo.getUserName();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                        if (isExist(fromName)) {

                            MessageItem messageItem = new MessageItem();
                            messageItem.setUserName(userInfo.getUserName());
                            messageItem.setDate(format.format(msg.getCreateTime()));
                            messageItem.setMessage(textContent.getText());
                            messageItem.setImageId(R.drawable.head1);
                            messageItem.setMessageType(MessageItem.SINGLE);
                            Conversation conversation = Conversation.createSingleConversation(userInfo.getUserName());
                            messageItem.setConversation(conversation);
                            messageItemList.remove(getPlaceOfUser(fromName));
                            messageItemList.add(0, messageItem);

                        } else {

                            MessageItem messageItem = new MessageItem();
                            messageItem.setUserName(userInfo.getUserName());
                            messageItem.setDate(format.format(msg.getCreateTime()));
                            messageItem.setMessage(textContent.getText());
                            messageItem.setImageId(R.drawable.head1);
                            messageItem.setMessageType(MessageItem.SINGLE);
                            Conversation conversation = Conversation.createSingleConversation(userInfo.getUserName());
                            messageItem.setConversation(conversation);
                            messageItemList.add(0, messageItem);

                        }

                        messageRecyclerViewAdapter.notifyDataSetChanged();

                        break;

                }

                break;

            case group:

                switch (msg.getContentType()) {

                    case text:

                        Conversation conversation = Conversation.createGroupConversation(Long.parseLong(msg.getTargetID()));
                        TextContent textContent = (TextContent) msg.getContent();
                        UserInfo userInfo = msg.getFromUser();
                        String fromName = userInfo.getUserName();
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

                        if (isExist(conversation.getTitle())) {

                            MessageItem messageItem = new MessageItem();
                            messageItem.setUserName(conversation.getTitle());
                            messageItem.setDate(format.format(msg.getCreateTime()));
                            if (fromName.equals(myName)){
                                messageItem.setMessage("我："+textContent.getText());
                            }else {
                                messageItem.setMessage(fromName+"："+textContent.getText());
                            }
                            messageItem.setMessageType(MessageItem.GROUP);
                            messageItem.setImageId(R.drawable.head6);
                            messageItem.setConversation(conversation);
                            messageItemList.remove(getPlaceOfUser(conversation.getTitle()));
                            messageItemList.add(0, messageItem);

                        } else {

                            MessageItem messageItem = new MessageItem();
                            messageItem.setUserName(conversation.getTitle());
                            messageItem.setDate(format.format(msg.getCreateTime()));
                            if (fromName.equals(myName)){
                                messageItem.setMessage("我："+textContent.getText());
                            }else {
                                messageItem.setMessage(fromName+"："+textContent.getText());
                            }
                            messageItem.setMessageType(MessageItem.GROUP );
                            messageItem.setImageId(R.drawable.head6);
                            messageItem.setConversation(conversation);
                            messageItemList.add(0, messageItem);

                        }

                        messageRecyclerViewAdapter.notifyDataSetChanged();


                        break;

                    default:

                        break;

                }

                break;
                default:
                break;
        }

        }


    public boolean isExist(String name){

        if (messageItemList.isEmpty()){
            return false;
        }else{

            for (int i=0 ; i<messageItemList.size() ; i++){
                if (messageItemList.get(i).getUserName().equals(name)){
                    return true;
                }
            }

        }

        return false;

    }


    public int getPlaceOfUser(String name){

        for (int i=0 ; i<messageItemList.size() ; i++){
            if (messageItemList.get(i).getUserName().equals(name)){
                return i;
            }
        }
        return 0;
    }



}
