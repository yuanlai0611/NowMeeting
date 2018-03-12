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

import com.gongyunhao.nowmeeting.Adapter.MeetingRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseFragment;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.MeetingItem;

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
public class FragmentMeeting extends BaseFragment{

    private Context mContext;
    String Tag = "FragmentMeeting";
    private List<MeetingItem> meetingItemList;
    private RecyclerView recyclerView;
    private MeetingRecyclerviewAdapter meetingRecyclerviewAdapter;
    private final int TITLE = 1;
    private final int MEETING = 2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetingItemList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting,container,false);
        Log.d(Tag,"---->onCre1ateView");
        initViews(view);
        setListener();
        return view;
    }



    @Override
    protected void initViews(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.meeting_recyclerview);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void requestData() {
        MeetingItem meetingItem = new MeetingItem();
        meetingItem.setViewType(TITLE);
        meetingItem.setTitleName("参加的会议");
        meetingItemList.add(meetingItem);
        for (int i=0 ; i<3 ; i++){
            MeetingItem meetingItem1 = new MeetingItem();
            meetingItem1.setViewType(MEETING);
            meetingItem1.setMeetingName("GDG Wuhan");
            meetingItemList.add(meetingItem1);
        }
        MeetingItem meetingItem2 = new MeetingItem();
        meetingItem2.setViewType(TITLE);
        meetingItem2.setTitleName("好友参加的会议");
        meetingItemList.add(meetingItem2);
        for (int i=0 ; i<2 ; i++){
            MeetingItem meetingItem3 = new MeetingItem();
            meetingItem3.setViewType(MEETING);
            meetingItem3.setMeetingName("安卓巴士-线下沙龙");
            meetingItemList.add(meetingItem3);
        }
        meetingRecyclerviewAdapter = new MeetingRecyclerviewAdapter(mContext,meetingItemList);
        recyclerView.setAdapter(meetingRecyclerviewAdapter);
    }



    @Override
    protected void setListener(){

    }
}
