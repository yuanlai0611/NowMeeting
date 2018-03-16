package com.gongyunhao.nowmeeting.Activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.Adapter.MyPageAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.Fragment.FragmentMeeting;
import com.gongyunhao.nowmeeting.Fragment.FragmentMessage;
import com.gongyunhao.nowmeeting.Fragment.FragmentMy;
import com.gongyunhao.nowmeeting.R;

import com.gongyunhao.nowmeeting.util.TypeFaceUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private Boolean isSearchChecked=false;
    private TextView textView;
//    private RelativeLayout relativeLayout_search;
//    private EditText et_search;
    private List<Fragment> datas;
    private List<String> titles;
    private TabLayout tabLayout;
    private FragmentManager fm;
    private Fragment fragment;
    private int[] tabIcons = {
            R.drawable.selector_tab_message,
            R.drawable.selector_tab_meeting,
            R.drawable.selector_tab_my
    };

    private ViewPager viewPager;
    private MyPageAdapter myPageAdapter;
    private ImageButton imageButtonSearch;
    private Context mContext;

    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initViews();
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        initViews();

        imageButtonSearch.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

//                Intent intent=new Intent( MainActivity.this,SearchActivity.class );
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

                startIntent( MeetingDetailActivity.class );

//                fm=getSupportFragmentManager();
//                fragment=fm.findFragmentById( R.id.fragment_search_container );
//                if (fragment==null){
//                    fragment=new SearchFragment();
//                    fm.beginTransaction()
//                            .add( R.id.fragment_search_container,fragment )
//                            .commit();
//                }

            }
        } );

        //设置字体
        TypeFaceUtil.setTypeFace(textView,TypeFaceUtil.HARD_POINT,mContext);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float temp_positionOffset;
                if (positionOffset>0.5){
                    temp_positionOffset = positionOffset;
                }else {
                    temp_positionOffset = 1-positionOffset;
                }
                textView.setAlpha(temp_positionOffset);
                if ((position+positionOffset)<0.5){
                    textView.setText(R.string.title_message);
                }else if ((position+positionOffset)>=0.5&&(position+positionOffset)<1.5){
                    textView.setText(R.string.title_meeting);
                }else {
                    textView.setText(R.string.title_about_me);
                }
                if ((position+positionOffset)>=1&&(position+positionOffset)<2){
                    Log.d("MainActivity","---->开始设置imageButton透明");
                    imageButtonSearch.setVisibility(View.VISIBLE);
                    imageButtonSearch.setAlpha(2-(position+positionOffset));
                    imageButtonSearch.setEnabled( true );
                }else if ((position+positionOffset)==2){
                    imageButtonSearch.setVisibility(View.GONE);
                    imageButtonSearch.setEnabled( false );
                }else if ((position+positionOffset)==0){
                    imageButtonSearch.setVisibility(View.VISIBLE);
                    imageButtonSearch.setEnabled( true );
                }
                //positionOffset 是百分比
                   Log.d("MainActivity","---->position:"+position+"  positionOffset:"+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                //Log.d(Tag,"---->pagerState:"+state);
               //起始状态为0 正在翻页状态为1 翻页完成状态为2

            }
        });

    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {
//        et_search=findViewById( R.id.edittext_search );
//        relativeLayout_search=findViewById( R.id.relative_search );
        textView = findViewById(R.id.title_name);
        imageButtonSearch = findViewById(R.id.search_button);
        datas = new ArrayList<>();
        datas.add(new FragmentMessage());
        datas.add(new FragmentMeeting());
        datas.add(new FragmentMy());

        titles = new ArrayList<>();
        titles.add("消息");
        titles.add("会议");
        titles.add("我的");

        myPageAdapter = new MyPageAdapter(getSupportFragmentManager(),datas,titles);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        // 将适配器设置进ViewPager
        viewPager.setAdapter(myPageAdapter);
        viewPager.setOffscreenPageLimit(1);

        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager(),datas,titles);

//        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        // 将适配器设置进ViewPager
        viewPager.setAdapter(myPageAdapter);

        viewPager.setCurrentItem(0);
        // 将ViewPager与TabLayout相关联
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
    }
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_tab);
        txt_title.setText(titles.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_tab);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed( );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - lastClickTime) > 2000) {
            showToast( "再按一次退出"+this.getResources().getString(R.string.app_name) );
            lastClickTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
