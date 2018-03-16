package com.gongyunhao.nowmeeting.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gongyunhao.nowmeeting.Adapter.ChattingRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.test.ChattingItem;

import java.util.ArrayList;
import java.util.List;


public class ChattingActivity extends AppCompatActivity {

    private List<ChattingItem> chattingItems;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        chattingItems = new ArrayList<>();
        mContext = this;
        testInit();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.chatting_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ChattingRecyclerviewAdapter chattingRecyclerviewAdapter = new ChattingRecyclerviewAdapter(mContext,chattingItems);
        recyclerView.setAdapter(chattingRecyclerviewAdapter);

    }

    public void testInit(){
        for (int i=0;i<6;i++){
            ChattingItem chattingItem = new ChattingItem();
            chattingItem.setViewType(ChattingItem.LEFT);
            chattingItem.setChattingMessage("你是傻逼吗？");
            chattingItem.setPictureId(R.drawable.head3);
            chattingItems.add(chattingItem);
            ChattingItem chattingItem1 = new ChattingItem();
            chattingItem1.setViewType(ChattingItem.RIGHT);
            chattingItem1.setChattingMessage("不是，你才是傻逼");
            chattingItem1.setPictureId(R.drawable.head4);
            chattingItems.add(chattingItem1);
        }
    }
}
