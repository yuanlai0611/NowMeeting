package com.gongyunhao.nowmeeting.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import android.widget.ImageView;
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

    private TextView textView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<Fragment> datas;
    private List<String> titles;
    private TabLayout tabLayout;
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
                if ((position+positionOffset)>=1){
                    Log.d("MainActivity","---->开始设置imageButton透明");
                    imageButtonSearch.setVisibility(View.VISIBLE);
                    imageButtonSearch.setAlpha(2-(position+positionOffset));
                }else if ((position+positionOffset)==2){
                    imageButtonSearch.setVisibility(View.GONE);
                }else if ((position+positionOffset)==0){
                    imageButtonSearch.setVisibility(View.VISIBLE);
                }
                //positionOffset 是百分比
                   Log.d("MainActivity","---->position:"+position+"  positionOffset:"+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
//
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                //Log.d(Tag,"---->pagerState:"+state);
               //起始状态为0 正在翻页状态为1 翻页完成状态为2

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState( );

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {


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

//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

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

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed( );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater( ).inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId( );
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId( );
        switch (id){
            case R.id.nav_camera:
                // Handle the camera action
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
            if (drawer.isDrawerOpen( GravityCompat.START )) {
                drawer.closeDrawer( GravityCompat.START );
            }else {
                exit();
            }
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
