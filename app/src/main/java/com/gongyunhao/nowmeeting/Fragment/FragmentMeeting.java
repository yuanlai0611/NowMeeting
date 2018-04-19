package com.gongyunhao.nowmeeting.Fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gongyunhao.nowmeeting.Activity.MeetingDetailActivity;
import com.gongyunhao.nowmeeting.Adapter.MeetingRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseFragment;
import com.gongyunhao.nowmeeting.JsonBean.MeetingInformation;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

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
    private RecyclerView recyclerViewMeeting;
    private MeetingRecyclerviewAdapter meetingRecyclerviewAdapter;
    private ImageView imageView_meeting;
    private SharedPreferences sharedPreferences;
    private String userID;
    private String mUrl="http://39.106.47.27:8080/conference/api/userEntry/dogetAllConference";
    private JSONArray jsonArray;
    private List<MeetingInformation> usermeetingList;

    private final int TITLE = 1;
    private final int MEETING = 2;
   // private MeetingItem meetingItem;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usermeetingList = new ArrayList<>();
        sharedPreferences=mContext.getSharedPreferences( "isLogin",Context.MODE_PRIVATE );
        userID=sharedPreferences.getString( "userID","" );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
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
        meetingRecyclerviewAdapter = new MeetingRecyclerviewAdapter(mContext,usermeetingList);
        recyclerViewMeeting.setAdapter(meetingRecyclerviewAdapter);
        imageView_meeting=view.findViewById( R.id.meeting_picture );

        meetingRecyclerviewAdapter.setmOnItemClickListener( new MeetingRecyclerviewAdapter.OnItemClickListener( ) {
            @Override
            public void onItemClick(View view, int position) {
                MeetingInformation meetingItem=usermeetingList.get( position );

                Intent intent=new Intent( getActivity(), MeetingDetailActivity.class );
                intent.putExtra( "Extra_meeting_posithon",position );
                intent.putExtra( "meetingId",meetingItem.getId() );
                int firstVisiblePosition = ((LinearLayoutManager)recyclerViewMeeting.getLayoutManager()).findFirstVisibleItemPosition();

                View itemView = recyclerViewMeeting.getChildAt(position - firstVisiblePosition);
                View meeting_p = itemView.findViewById(R.id.meeting_picture);

                //实现了share动画在recyclerview中传递的效果
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        android.util.Pair.create(meeting_p, "iv_meeting_share"))
                        .toBundle());

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

        OkHttpUtil.getInstance().dogetAllConference( userID,mUrl, new okhttp3.Callback( ) {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsedata=response.body().string();
                usermeetingList.clear();
                try {
                    JSONObject jsonObject=new JSONObject( responsedata );
                    jsonArray=jsonObject.getJSONArray( "data" );
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject( i );
                        MeetingInformation meeting=new MeetingInformation();
                        meeting.setId( jsonObject1.getInt( "id" ) );
                        meeting.setCategoryId( jsonObject1.getInt( "categoryId" ) );
                        meeting.setSponsorName( jsonObject1.getString( "sponsorName" ) );
                        meeting.setName( jsonObject1.getString( "name" ) );
                        meeting.setChatroomId( jsonObject1.getInt( "chatroomId" ) );
                        meeting.setSponsorId( jsonObject1.getInt( "sponsorId" ) );
                        meeting.setStatus( jsonObject1.getInt( "status" ) );
                        meeting.setIntroduction( jsonObject1.getString( "introduction" ) );
                        meeting.setCity( jsonObject1.getString( "city" ) );
                        meeting.setLocation( jsonObject1.getString( "location" ) );
                        meeting.setTime( jsonObject1.getString( "time" ) );
                        meeting.setSignedNumber( jsonObject1.getInt( "signedNumber" ) );
                        meeting.setParticipatedNumber( jsonObject1.getInt( "participatedNumber" ) );
                        meeting.setPhoto( jsonObject1.getString( "photo" ) );
                        meeting.setMeetingPictureId(R.drawable.meeting_test_2);
                        meeting.setMeetingType(MeetingInformation.PARTICIPATE);
                        meeting.setViewType(MEETING);
                        usermeetingList.add( meeting );
                    }
                    MeetingInformation meetingItem5 = new MeetingInformation();
                    meetingItem5.setViewType(MEETING);
                    meetingItem5.setName("电影讨论大会");
                    meetingItem5.setMeetingPictureId(R.drawable.meeting_test_2);
                    meetingItem5.setMeetingType(MeetingInformation.FRIEND_PARTICIPATE);
                    usermeetingList.add(meetingItem5);

                    MeetingInformation meetingItem6 = new MeetingInformation();
                    meetingItem6.setViewType(MEETING);
                    meetingItem6.setName("穿衣搭配");
                    meetingItem6.setSponsorName("龚云浩");
                    meetingItem6.setIntroduction( "还在为怎么穿衣搭配而烦扰吗？还在为自己的身材，身高，体型怎么搭配衣服而烦扰吗？你是衣服搭配选择困难户吗？\n" +
                            "\n" +
                            "不怕，来参加此会议吧，教您各种穿衣搭配哦。只要您每日来访问我都会有新的发现。哈哈，废话不多说，请猛戳开始自己的穿衣搭配风格吧。\n" );
                    meetingItem6.setCity( "杭州" );
                    meetingItem6.setLocation( "杭州西湖旁" );
                    meetingItem6.setTime( "2018-6-2" );
                    meetingItem6.setMeetingPictureId(R.drawable.meeting_test_3);
                    meetingItem6.setMeetingType(MeetingInformation.HOT);
                    usermeetingList.add(meetingItem6);
                    getActivity().runOnUiThread( new Runnable( ) {
                        @Override
                        public void run() {
                            meetingRecyclerviewAdapter.notifyDataSetChanged();
                        }
                    } );
                } catch (JSONException e) {
                    e.printStackTrace( );
                }
            }
        } );



        //设置参加的会议的具体会议
    }


    @Override
    protected void setListener(){

    }
}
