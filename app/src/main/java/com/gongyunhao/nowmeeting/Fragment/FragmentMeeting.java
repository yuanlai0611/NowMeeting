package com.gongyunhao.nowmeeting.Fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gongyunhao.nowmeeting.Activity.MeetingDetailActivity;
import com.gongyunhao.nowmeeting.Activity.MeetingDetailNoJoinActivity;
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
    private RecyclerView recyclerViewMeeting;
    private MeetingRecyclerviewAdapter meetingRecyclerviewAdapter;
    private ImageView imageView_meeting;

    private final int TITLE = 1;
    private final int MEETING = 2;
   // private MeetingItem meetingItem;

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
        recyclerViewMeeting = (RecyclerView)view.findViewById(R.id.recyclerview_meeting);
        recyclerViewMeeting.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewMeeting.setItemAnimator(new DefaultItemAnimator());
        meetingRecyclerviewAdapter = new MeetingRecyclerviewAdapter(mContext,meetingItemList);
        recyclerViewMeeting.setAdapter(meetingRecyclerviewAdapter);
        imageView_meeting=view.findViewById( R.id.meeting_picture );


        meetingRecyclerviewAdapter.setmOnItemClickListener( new MeetingRecyclerviewAdapter.OnItemClickListener( ) {
            @Override
            public void onItemClick(View view, int position) {
                MeetingItem meetingItem=meetingItemList.get( position );

                Intent intent=new Intent( getActivity(), MeetingDetailActivity.class );
                Intent intent_no_join=new Intent( getActivity(), MeetingDetailNoJoinActivity.class );
                intent.putExtra( "Extra_meeting_posithon",position );
                intent.putExtra( "Extra_meeting_name" ,meetingItem.getMeetingName());
                intent.putExtra( "Extra_meeting_picture",meetingItem.getMeetingPictureId() );
                intent.putExtra( "Extra_meeting_city",meetingItem.getMeetingCity() );
                intent.putExtra( "Extra_meeting_date",meetingItem.getMeetingDate() );
                int firstVisiblePosition = ((LinearLayoutManager)recyclerViewMeeting.getLayoutManager()).findFirstVisibleItemPosition();

                View itemView = recyclerViewMeeting.getChildAt(position - firstVisiblePosition);
                View meeting_p = itemView.findViewById(R.id.meeting_picture);

                //模拟判断用户是否参加了此会议

                if (position%2==0){//参加了在这里

                    //实现了share动画在recyclerview中传递的效果
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                            android.util.Pair.create(meeting_p, "iv_meeting_share"))
                            .toBundle());
                }else {//未参加在这里

                    //实现了share动画在recyclerview中传递的效果
                    startActivity(intent_no_join, ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                            android.util.Pair.create(meeting_p, "iv_meeting_share"))
                            .toBundle());
                }

            }
        } );

    }

    @Override
    protected void requestData() {
        initMeetingParticipate();
//        initMeetingFriendParticipate();
//        initMeetingHot();
        meetingRecyclerviewAdapter.notifyDataSetChanged();
    }


    private void initMeetingParticipate(){


//        MeetingItem meetingItem = new MeetingItem();
//        meetingItem.setViewType(TITLE);
//        meetingItem.setMeetingPictureId(R.drawable.meeting_participate);
//        meetingItemList.add(meetingItem);



            MeetingItem meetingItem1 = new MeetingItem();
            meetingItem1.setViewType(MEETING);
            meetingItem1.setMeetingCity( "武汉" );
            meetingItem1.setMeetingDate( "2017-10-21" );
            meetingItem1.setMeetingPictureId(R.drawable.meeting_test);
            meetingItem1.setMeetingName("GDG Wuhan");
            meetingItem1.setMeetingPictureId(R.drawable.meeting_test_1);
            meetingItem1.setMeetingType(MeetingItem.HOT);
            meetingItemList.add(meetingItem1);

            MeetingItem meetingItem2 = new MeetingItem();
            meetingItem2.setViewType(MEETING);
            meetingItem2.setMeetingName("GDG Wuhan");
            meetingItem2.setMeetingPictureId(R.drawable.meeting_test_2);
            meetingItem2.setMeetingType(MeetingItem.FRIEND_PARTICIPATE);
            meetingItemList.add(meetingItem2);

            MeetingItem meetingItem3 = new MeetingItem();
            meetingItem3.setViewType(MEETING);
            meetingItem3.setMeetingName("GDG Wuhan");
            meetingItem3.setMeetingPictureId(R.drawable.meeting_test_3);
            meetingItem3.setMeetingType(MeetingItem.PARTICIPATE);
            meetingItemList.add(meetingItem3);

            MeetingItem meetingItem4 = new MeetingItem();
            meetingItem4.setViewType(MEETING);
            meetingItem4.setMeetingName("GDG Wuhan");
            meetingItem4.setMeetingPictureId(R.drawable.meeting_test_1);
            meetingItem4.setMeetingType(MeetingItem.PARTICIPATE);
            meetingItemList.add(meetingItem4);

            MeetingItem meetingItem5 = new MeetingItem();
            meetingItem5.setViewType(MEETING);
            meetingItem5.setMeetingName("GDG Wuhan");
            meetingItem5.setMeetingPictureId(R.drawable.meeting_test_2);
            meetingItem5.setMeetingType(MeetingItem.FRIEND_PARTICIPATE);
            meetingItemList.add(meetingItem5);

            MeetingItem meetingItem6 = new MeetingItem();
            meetingItem6.setViewType(MEETING);
            meetingItem6.setMeetingName("GDG Wuhan");
            meetingItem6.setMeetingPictureId(R.drawable.meeting_test_3);
            meetingItem6.setMeetingType(MeetingItem.HOT);
            meetingItemList.add(meetingItem6);



        //设置参加的会议的具体会议
    }

//    private void initMeetingFriendParticipate(){
//        MeetingItem meetingItem = new MeetingItem();
//        meetingItem.setViewType(TITLE);
//        meetingItem.setMeetingPictureId(R.drawable.meeting_friend_participate);
//        meetingItemList.add(meetingItem);
//        for (int i=0;i<3;i++){
//            MeetingItem meetingItem1 = new MeetingItem();
//            meetingItem1.setViewType(MEETING);
//            meetingItem1.setMeetingName("GDG Wuhan");
//            meetingItemList.add(meetingItem1);
//        }
//        //设置朋友参加的具体会议
//    }
//
//    private void initMeetingHot(){
//        MeetingItem meetingItem = new MeetingItem();
//        meetingItem.setViewType(TITLE);
//        meetingItem.setMeetingPictureId(R.drawable.meeting_hot);
//        meetingItemList.add(meetingItem);
//        for (int i=0;i<3;i++){
//            MeetingItem meetingItem1 = new MeetingItem();
//            meetingItem1.setViewType(MEETING);
//            meetingItem1.setMeetingName("GDG Wuhan");
//            meetingItemList.add(meetingItem1);
//        }
//        //设置热门会议
//    }


    @Override
    protected void setListener(){

    }
}
