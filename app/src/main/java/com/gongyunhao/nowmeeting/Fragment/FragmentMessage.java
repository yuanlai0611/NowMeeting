package com.gongyunhao.nowmeeting.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhao.nowmeeting.Adapter.MessageRecyclerViewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseFragment;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.MessageItem;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageItemList = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        Log.d(Tag,"---->onCreateView");
        initViews(view);
        setListener();
        return view;
    }


    @Override
    protected void requestData() {
        for(int i=0;i<10;i++){
            MessageItem messageItem = new MessageItem();
            messageItem.setUserName("贝贝");
            messageItem.setDate("上午10：30");
            messageItem.setMessage("软件app能够分别遥控几十个不同地方的各个接收终端，每个地方有若干个接收终端");
            messageItem.setImageId(R.drawable.head1);
            messageItemList.add(messageItem);
        }
    }


    //避免了每次都要请求网络
    @Override
    protected void initViews(View view) {
        recyclerView = view.findViewById(R.id.message_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        messageRecyclerViewAdapter = new MessageRecyclerViewAdapter(mContext,messageItemList);
        recyclerView.setAdapter(messageRecyclerViewAdapter);
    }




    @Override
    protected void setListener(){

    }

}
