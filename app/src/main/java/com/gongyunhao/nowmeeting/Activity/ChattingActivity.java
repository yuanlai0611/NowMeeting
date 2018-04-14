package com.gongyunhao.nowmeeting.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gongyunhao.nowmeeting.Adapter.ChattingRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.deal.KeyBoardObserver;
import com.gongyunhao.nowmeeting.deal.MeasureLinearLayout;
import com.gongyunhao.nowmeeting.deal.SharePrefenceUtils;
import com.gongyunhao.nowmeeting.test.ChattingItem;
import com.gongyunhao.nowmeeting.util.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;


public class ChattingActivity extends BaseActivity implements KeyBoardObserver,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener{
    private ImageButton ib_back,ib_more_emoji,ib_more_function;
    private List<ChattingItem> chattingItems;
    private Context mContext;
    private RecyclerView recyclerView;

    private MeasureLinearLayout rootLayout;
    private SwipeRefreshLayout swipeLayout;
    private FrameLayout pannel,emojiframe;
    private EmojiconEditText mEditEmojicon;
    private LinearLayout linear_more_function;

    private boolean isEmoji=false;

    @SuppressLint("ClickableViewAccessibility")
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
        setEmojiconFragment(false);

        //初始化高度，和软键盘一致，初值为手机高度一半
        pannel.getLayoutParams().height = SharePrefenceUtils.getKeyBoardHeight(this);
        rootLayout.getKeyBoardObservable().register(this);

        ib_more_function.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (isEmoji) {
                    //想要显示面板
                    if (rootLayout.getKeyBoardObservable().isKeyBoardVisibile()) {
                        //当前软键盘为 挂起状态
                        //隐藏软键盘并显示面板
                        mEditEmojicon.clearFocus();
                        KeyBoardUtils.hideKeyboard(mEditEmojicon);
                    } else {
                        //显示面板
                        pannel.setVisibility(View.VISIBLE);
                        linear_more_function.setVisibility( View.VISIBLE );
                        emojiframe.setVisibility( View.GONE );
                    }
                    isEmoji=!isEmoji;
                } else {
                    //想要关闭面板
                    //挂起软键盘，并隐藏面板
                    mEditEmojicon.requestFocus();
                    KeyBoardUtils.showKeyboard(mEditEmojicon);
                    isEmoji=!isEmoji;

                }
            }
        } );

        ib_more_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEmoji=!isEmoji;
                if (isEmoji) {
                    //想要显示面板
                    if (rootLayout.getKeyBoardObservable().isKeyBoardVisibile()) {
                        //当前软键盘为 挂起状态
                        //隐藏软键盘并显示面板
                        mEditEmojicon.clearFocus();
                        KeyBoardUtils.hideKeyboard(mEditEmojicon);
                    } else {
                        //显示面板
                        pannel.setVisibility(View.VISIBLE);
                        linear_more_function.setVisibility( View.GONE );
                        emojiframe.setVisibility( View.VISIBLE );
                    }
                } else {
                    //想要关闭面板
                    //挂起软键盘，并隐藏面板
                    mEditEmojicon.requestFocus();
                    KeyBoardUtils.showKeyboard(mEditEmojicon);
                }
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //点击列表部分就清除焦点并初始化状态
                    if (pannel.getVisibility() == View.VISIBLE) {
                        pannel.setVisibility(View.GONE);
                        isEmoji=false;
                    } else {
                        mEditEmojicon.clearFocus();
                        KeyBoardUtils.hideKeyboard(mEditEmojicon);
                    }
                }
                return false;
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeLayout.setRefreshing(false);
            }
        });

    }

    //EmojiconsFragment表情显示的fragment
    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    //表情点击回调
    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }
    //删除表情点击回调
    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);
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

        rootLayout =findViewById(R.id.root_layout);
        swipeLayout =findViewById(R.id.swipe_layout);
        linear_more_function=findViewById( R.id.linear_more_function );
        ib_more_function=findViewById( R.id.ib_chatting_more_function );
        emojiframe=findViewById( R.id.emojicons );
        pannel =findViewById(R.id.pannel);
        ib_more_emoji=findViewById( R.id.ib_chatting_more_emoji );
        mEditEmojicon=findViewById( R.id.et_chatting );

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

    @Override
    public void update(boolean keyBoardVisibile, int keyBoardHeight) {
        if (keyBoardVisibile) {
            //软键盘挂起
            pannel.setVisibility(View.GONE);
            isEmoji=false;
        } else {
            //回复原样
            if (isEmoji) {
                if (pannel.getLayoutParams().height != keyBoardHeight) {
                    pannel.getLayoutParams().height = keyBoardHeight;
                }
                pannel.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rootLayout.getKeyBoardObservable().unRegister(this);
    }

}
