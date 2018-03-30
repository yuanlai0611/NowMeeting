package com.gongyunhao.nowmeeting.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.gongyunhao.nowmeeting.Adapter.ChattingRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.test.ChattingItem;

import java.util.ArrayList;
import java.util.List;


public class ChattingActivity extends BaseActivity {
    private ImageButton ib_back;
    private List<ChattingItem> chattingItems;
    private Context mContext;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        initData();

        initViews();

        ib_back.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_chatting);
    }

    @Override
    public void initViews() {
        ib_back=findViewById( R.id.chatting_back );
        recyclerView = findViewById(R.id.chatting_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ChattingRecyclerviewAdapter chattingRecyclerviewAdapter = new ChattingRecyclerviewAdapter(mContext,chattingItems);
        recyclerView.setAdapter(chattingRecyclerviewAdapter);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        chattingItems = new ArrayList<>();
        mContext = this;
        testInit();
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
