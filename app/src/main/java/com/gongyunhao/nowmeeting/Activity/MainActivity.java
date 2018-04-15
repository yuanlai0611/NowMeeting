package com.gongyunhao.nowmeeting.Activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.Adapter.MyPageAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.Fragment.FragmentMeeting;
import com.gongyunhao.nowmeeting.Fragment.FragmentMessage;
import com.gongyunhao.nowmeeting.Fragment.FragmentMy;
import com.gongyunhao.nowmeeting.R;

import com.gongyunhao.nowmeeting.util.AnimUtil;
import com.gongyunhao.nowmeeting.util.TypeFaceUtil;
import com.gongyunhao.nowmeeting.view.NoScrollViewpager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;


public class MainActivity extends BaseActivity {
    private TextView textView;
    private List<Fragment> datas;
    private List<String> titles;
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.selector_tab_message,
            R.drawable.selector_tab_meeting,
            R.drawable.selector_tab_my
    };
    private NoScrollViewpager viewPager;
    private MyPageAdapter myPageAdapter;
    private Context mContext;
    //添加好友信息
    private String mFromUserName;
    private String mReason;

    private View is_friend_add_dot;
    private Toolbar toolbar;
    private long lastClickTime = 0;
    private PopupWindow popupWindow;
    private AnimUtil animUtil;
    private ImageButton imageButtonAddMenu;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private static final long DURATION = 400;
    private static final float START_ALPHA = 0.9f;
    private static final float END_ALPHA = 1f;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LinearLayout linearLayoutRichScan,linearLayoutAddFriend,linearLayoutSearch,linearLayoutAddMeeting;
    private ImageButton imageButtonFriendAddList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        sharedPreferences=getSharedPreferences( "addfriend",MODE_PRIVATE );
        editor=sharedPreferences.edit();

        mContext = this;
        setSupportActionBar( toolbar );
            //设置字体
        TypeFaceUtil.setTypeFace(textView, TypeFaceUtil.HARD_POINT, mContext);

        JMessageClient.registerEventReceiver(this);

            viewPager.addOnPageChangeListener(new NoScrollViewpager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    float temp_positionOffset;

                    if (positionOffset > 0.5) {
                        temp_positionOffset = positionOffset;
                    } else {
                        temp_positionOffset = 1 - positionOffset;
                    }

                    textView.setAlpha(temp_positionOffset);

                    if ((position + positionOffset) < 0.5) {
                        textView.setText(R.string.title_message);
                    } else if ((position + positionOffset) >= 0.5 && (position + positionOffset) < 1.5) {
                        textView.setText(R.string.title_meeting);
                    } else {
                        textView.setText(R.string.title_about_me);
                    }

                    if ((position + positionOffset) >= 1) {
//                        Log.d("MainActivity", "---->开始设置imageButton透明");
                        imageButtonAddMenu.setAlpha(2 - (position + positionOffset));
                    } else if ((position + positionOffset) == 2) {
                        imageButtonAddMenu.setVisibility(View.GONE);
                    } else if ((position + positionOffset) == 0) {
//

                    if ((position+positionOffset)<=1&&(position+positionOffset)>=0){

                        imageButtonFriendAddList.setAlpha(1-(position+positionOffset));

                    }


                    //    Log.d("MainActivity","---->使imageButton可见");
                    }
                    //positionOffset 是百分比
                   // Log.d("MainActivity", "---->position:" + position + "  positionOffset:" + positionOffset);
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
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver( this );
        JMessageClient.logout();
    }

    public void onEvent(ContactNotifyEvent event) {
        String reason = event.getReason();
        String fromUsername = event.getFromUsername();
        String appkey = event.getfromUserAppKey();

        switch (event.getType()) {
            case invite_received://收到好友邀请
                mFromUserName=fromUsername;
                mReason=reason;
                is_friend_add_dot.setVisibility( View.VISIBLE );
                break;
            case invite_accepted://对方接收了你的好友邀请
                //...
                break;
            case invite_declined://对方拒绝了你的好友邀请
                //...
                break;
            case contact_deleted://对方将你从好友中删除
                //...
                break;
            default:
                break;
        }
    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_main );
    }

    @Override
    public void initViews() {

        imageButtonAddMenu = (ImageButton)findViewById(R.id.add_menu);
        imageButtonFriendAddList = (ImageButton)findViewById(R.id.friend_add_list);
        textView = findViewById(R.id.title_name);
        is_friend_add_dot=findViewById( R.id.is_friend_add_dot );
        toolbar = (Toolbar) findViewById( R.id.toolbar );
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (NoScrollViewpager) findViewById(R.id.view_pager);
        popupWindow = new PopupWindow(this);
        animUtil = new AnimUtil();
        datas = new ArrayList<>();
        datas.add(new FragmentMessage());
        datas.add(new FragmentMeeting());
        datas.add(new FragmentMy());
        titles = new ArrayList<>();
        titles.add("消息");
        titles.add("会议");
        titles.add("我的");
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager(), datas, titles);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        // 将适配器设置进ViewPager
        viewPager.setAdapter(myPageAdapter);
        viewPager.setOffscreenPageLimit(1);
        //viewPager.setNoScroll(true);
        viewPager.setCurrentItem(0);
        // 将ViewPager与TabLayout相关联
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    @Override
    public void initListeners() {

       imageButtonAddMenu.setOnClickListener(this);
       imageButtonFriendAddList.setOnClickListener(this);

    }

    @Override
    public void initData() {

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
          exit();
          return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - lastClickTime) > 2000) {
            showToast("再按一次退出" + this.getResources().getString(R.string.app_name));
            lastClickTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.add_menu:
                showPop();
                toggleBright();
                break;
            case R.id.rich_scan:
                startIntent( ScanActivity.class );
                popupWindow.dismiss();
                break;
            case R.id.add_friend:
                Intent intent=new Intent( MainActivity.this,SearchFriendActivity.class );
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                popupWindow.dismiss();
                break;
            case R.id.search:
                startIntent( SearchActivity.class );
                popupWindow.dismiss();
                break;
            case R.id.add_meeting:
                popupWindow.dismiss();
                Intent intent2 = new Intent(MainActivity.this,MeetingEditActivity.class);
                startActivity(intent2);
                break;
            case R.id.friend_add_list:
                Intent intent1=new Intent( MainActivity.this,AddActivity.class );
                intent1.putExtra( "qunimadejiahaoyou",mFromUserName );
                intent1.putExtra( "qunimadejiahaoyoureason",mReason );
                startActivity( intent1 );
                is_friend_add_dot.setVisibility( View.GONE );
                break;

            default:
                break;
        }
    }

    private void showPop(){

        // 设置布局文件
        popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.popup_window, null));
        // 设置pop透明效果
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置pop出入动画
        popupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        popupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        popupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        popupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        popupWindow.showAsDropDown(imageButtonAddMenu, -100, dp2px( 16 ));



        // 设置pop关闭监听，用于改变背景透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });

        linearLayoutRichScan = (LinearLayout)popupWindow.getContentView().findViewById(R.id.rich_scan);
        linearLayoutAddFriend = (LinearLayout)popupWindow.getContentView().findViewById(R.id.add_friend);
        linearLayoutSearch = (LinearLayout)popupWindow.getContentView().findViewById(R.id.search);
        linearLayoutAddMeeting = (LinearLayout)popupWindow.getContentView().findViewById(R.id.add_meeting);
        linearLayoutRichScan.setOnClickListener(this);
        linearLayoutAddFriend.setOnClickListener(this);
        linearLayoutSearch.setOnClickListener(this);
        linearLayoutAddMeeting.setOnClickListener(this);

    }


    private void toggleBright() {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        animUtil.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        animUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        animUtil.startAnimator();
    }

    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // 此方法用来设置浮动层，防止部分手机变暗无效
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}

