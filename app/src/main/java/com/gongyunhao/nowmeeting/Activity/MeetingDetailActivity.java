package com.gongyunhao.nowmeeting.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gongyunhao.nowmeeting.Adapter.LotteryRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Adapter.UserRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Adapter.VoteRecyclerAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.LotteryItem;
import com.gongyunhao.nowmeeting.bean.UserItem;
import com.gongyunhao.nowmeeting.bean.Voteitem;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupMembersCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MeetingDetailActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imageView_meeting_detail,imageView_qr_code;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView recycler_rough,recyclerView_lottery,recycler_vote_small;
    private UserRecyclerviewAdapter userRecyclerviewAdapter;
    private LotteryRecyclerviewAdapter lotteryRecyclerviewAdapter;
    private VoteRecyclerAdapter voteRecyclerAdapter;
    private RelativeLayout relativeLayout;
    private TextView tv_detail_meeting_place,tv_detail_meeting_date;
    private String QR_CODE_CONTENT="Extra_Qr_Content";
    private List<LotteryItem> lotteryItemList;
    private List<Voteitem> voteitemList;
    private List<UserInfo> userItems;
    private String getMeetingUrl = "http://39.106.47.27:8080/conference//api/conference/dogetConferenceInfo";
    private int groupId;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_meeting_detail );
    }

    @Override
    public void initViews() {

        voteitemList = new ArrayList<>();
        userItems = new ArrayList<>();
        lotteryItemList = new ArrayList<>();
        imageView_meeting_detail = findViewById( R.id.imageView_collapsing );
        collapsingToolbarLayout = findViewById( R.id.collapsing_toolbar_meeting_detail );
        recycler_rough = findViewById(R.id.detail_user_recycler_rough);
        tv_detail_meeting_place = findViewById( R.id.detail_meeting_place );
        tv_detail_meeting_date = findViewById( R.id.detail_meeting_date );
        imageView_qr_code = findViewById( R.id.iv_meeting_detail_qr_code );
        relativeLayout = findViewById( R.id.relativate_vote );
        recyclerView_lottery = findViewById(R.id.recyclerview_lottery);
        recycler_vote_small = findViewById( R.id.recycler_vote_small );
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(MeetingDetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView_lottery.setLayoutManager(linearLayoutManager1);
        lotteryRecyclerviewAdapter = new LotteryRecyclerviewAdapter(this,lotteryItemList);
        recyclerView_lottery.setAdapter(lotteryRecyclerviewAdapter);
        recyclerView_lottery.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( MeetingDetailActivity.this,LinearLayoutManager.HORIZONTAL,false );
        recycler_rough.setLayoutManager(linearLayoutManager);
        recycler_rough.setNestedScrollingEnabled(false);
        userRecyclerviewAdapter = new UserRecyclerviewAdapter(this,userItems);
        recycler_rough.setAdapter(userRecyclerviewAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager( MeetingDetailActivity.this,LinearLayoutManager.HORIZONTAL,false );
        recycler_vote_small.setLayoutManager(linearLayoutManager2);
        recycler_vote_small.setNestedScrollingEnabled(false);
        voteRecyclerAdapter = new VoteRecyclerAdapter(this,voteitemList);
        recycler_vote_small.setAdapter(voteRecyclerAdapter);

    }

    @Override
    public void initListeners() {

        imageView_qr_code.setOnClickListener( this );
        relativeLayout.setOnClickListener( this );

    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        int meetingId = intent.getIntExtra("meetingId",0);
        getMeetingInfo(String.valueOf(meetingId));

//        for (int i=0;i<3;i++){
//            voteitemList.add( new Voteitem( "天气","2018","龚云浩" ) );
//        }




//        for (int i=0 ; i<4 ; i++){
//            LotteryItem lotteryItem = new LotteryItem();
//            lotteryItem.setLotteryName(" ");
//            lotteryItemList.add(lotteryItem);
//        }


       // Glide.with(this).load(R.drawable.).apply( RequestOptions.bitmapTransform(new BlurTransformation())).into(imageView_meeting_detail);

        userRecyclerviewAdapter.notifyDataSetChanged();
        lotteryRecyclerviewAdapter.notifyDataSetChanged();
        voteRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        super.onClick( v );
        switch (v.getId()){
            case R.id.iv_meeting_detail_qr_code:
//                /**
//                 * 在这里放会议的识别信息进去
//                 */
//                Intent intent=new Intent( MeetingDetailActivity.this,QrCodeActivity.class );
//                intent.putExtra( QR_CODE_CONTENT,"棒棒小糖测试,中文测试试用\nQR Code Content" );
//                startActivity( intent );
                startIntent( CreateVoteActivity.class );
                break;
            case R.id.relativate_vote:
                startIntent( PieChartActivity.class );
                break;
        }
    }

    public void getMeetingInfo(final String meetingId){

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String > subscriber) {
                try {

                    Response response = OkHttpUtil.getInstance().getMeetingInfoResponse(meetingId,getMeetingUrl);

                    if (response.code()==200){

                        subscriber.onNext(response.body().string());
                        subscriber.onCompleted();

                    }else{

                        throw new IOException();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>()
                {

                    @Override
                    public void onNext(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            groupId = jsonObject1.getInt("chatroomId");

                            String meetingDate = jsonObject1.getString("time");
                            String meetingPlace = jsonObject1.getString("location");
                            String meetingTitle = jsonObject1.getString("name");
                            tv_detail_meeting_date.setText(meetingDate);
                            tv_detail_meeting_place.setText(meetingPlace);
                            collapsingToolbarLayout.setTitle(meetingTitle);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCompleted() {

                       // Toast.makeText(MeetingDetailActivity.this,"会议界面加载成功",Toast.LENGTH_SHORT).show();
                        JMessageClient.getGroupMembers(groupId,
                                new GetGroupMembersCallback() {
                                    @Override
                                    public void gotResult(int i, String s, List<UserInfo> list) {

                                        if (i==0){
                                            Log.d(Tag,"---->获取用户信息成功");

                                            userItems.clear();
                                            userItems.addAll( list );
                                            userRecyclerviewAdapter.notifyDataSetChanged();

                                        }else{
                                            Log.d(Tag,"---->获取用户信息失败");
                                        }

                                    }
                                });

                    }

                    @Override
                    public void onError(Throwable e) {

                       // Toast.makeText(MeetingDetailActivity.this,"会议界面加载失败...",Toast.LENGTH_SHORT).show();


                    }
                });


    }

}
