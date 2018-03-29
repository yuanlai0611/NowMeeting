package com.gongyunhao.nowmeeting.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gongyunhao.nowmeeting.Adapter.UserRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.UserItem;

import java.util.ArrayList;
import java.util.List;

public class MeetingDetailActivity extends BaseActivity {
    private ImageView imageView_meeting_detail,imageView_qr_code;
    private int position;
    private String name,date,city;
    private int pictureID;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView recycler_rough;
    private UserRecyclerviewAdapter userRecyclerviewAdapter;
    private List<UserItem> userItems=new ArrayList<>(  );
    private TextView tv_detail_meeting_place,tv_detail_meeting_date;
    private String QR_CODE_CONTENT="Extra_Qr_Content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
//        getWindow().requestFeature( Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView();
        initData();
        initViews();
        initListeners();

        for (int i=0 ; i<2 ;i++){
            UserItem userItem = new UserItem();
            userItem.setUserName("袁大来");
            userItem.setUserPictureId(R.drawable.head4);
            userItems.add(userItem);
            UserItem userItem1 = new UserItem();
            userItem1.setUserName("程文喆");
            userItem1.setUserPictureId(R.drawable.head5);
            userItems.add(userItem1);
            UserItem userItem2 = new UserItem();
            userItem2.setUserName("袁程程");
            userItem2.setUserPictureId(R.drawable.head6);
            userItems.add(userItem2);
        }

        imageView_qr_code.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                /**
                 * 在这里放会议的识别信息进去
                 */
                Intent intent=new Intent( MeetingDetailActivity.this,QrCodeActivity.class );
                intent.putExtra( QR_CODE_CONTENT,"棒棒小糖测试,中文测试试用\nQR Code Content" );
                startActivity( intent );
            }
        } );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( MeetingDetailActivity.this,LinearLayoutManager.HORIZONTAL,false );
        recycler_rough.setLayoutManager(linearLayoutManager);
        userRecyclerviewAdapter = new UserRecyclerviewAdapter(this,userItems);
        recycler_rough.setAdapter(userRecyclerviewAdapter);

        showToast( "我是第 "+position+"个会议"+name );
        //图是在adapter中设置的!!!!!

    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_meeting_detail );
    }

    @Override
    public void initViews() {
        imageView_meeting_detail=findViewById( R.id.imageView_collapsing );
        collapsingToolbarLayout=findViewById( R.id.collapsing_toolbar_meeting_detail );
        recycler_rough = findViewById(R.id.detail_user_recycler_rough);
        Glide.with(this).load(pictureID).apply( RequestOptions.bitmapTransform(new CenterCrop())).into(imageView_meeting_detail);
        collapsingToolbarLayout.setTitle( name );
        tv_detail_meeting_place=findViewById( R.id.detail_meeting_place );
        tv_detail_meeting_date=findViewById( R.id.detail_meeting_date );
        tv_detail_meeting_place.setText( city );
        imageView_qr_code=findViewById( R.id.iv_meeting_detail_qr_code );
        tv_detail_meeting_date.setText( date );
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        position=getIntent(  ).getIntExtra( "Extra_meeting_posithon",0 );
        name=getIntent(  ).getStringExtra( "Extra_meeting_name" );
        pictureID=getIntent(  ).getIntExtra( "Extra_meeting_picture", 0 );
        city=getIntent(  ).getStringExtra( "Extra_meeting_city" );
        date=getIntent(  ).getStringExtra( "Extra_meeting_date" );
    }
}
