package com.gongyunhao.nowmeeting.Activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Adapter.SearchFriendAdapter;
import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.JsonBean.Data;
import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchFriendActivity extends BaseActivity {
    private ImageButton ib_back;
    private RecyclerView recycler_search_friend;
    private EditText et_search;
    private String friendlistdata;
    private List<String> msearchfrienddatas=new ArrayList<>(  );
    private List<Data> dataList=new ArrayList<>(  );
    private boolean issuccess;
    private SearchFriendAdapter searchFriendAdapter;
    private String inforUrl = "http://39.106.47.27:8080/conference/api/user/dogetInfo";
    private String response;
    private static final int REQUEST_SUCCESS=0;
    private static final int REQUEST_FAILED=1;
    private static final int REQUEST_LOAD=2;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(  ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage( msg );
            switch (msg.what){
                case REQUEST_FAILED://没有查找到好友
                    break;
                case REQUEST_SUCCESS://查找成功
                    loadFriendDetail();
                    searchFriendAdapter.notifyDataSetChanged();
                    break;
                case REQUEST_LOAD://加载列表详细数据
                    searchFriendAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search_friend );
        ib_back=findViewById( R.id.search_imageButton_back );
        ib_back.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        recycler_search_friend=findViewById( R.id.recycler_search_friend );
        et_search=findViewById( R.id.title_edittext_search );
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( SearchFriendActivity.this );
        recycler_search_friend.setLayoutManager( linearLayoutManager );
        searchFriendAdapter=new SearchFriendAdapter( dataList,SearchFriendActivity.this );
        recycler_search_friend.setAdapter( searchFriendAdapter );
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //TODO:你自己的业务逻辑
                    //Toast.makeText(SearchActivity.this, "点击了搜索", Toast.LENGTH_SHORT).show();
                    new Thread( new Runnable( ) {
                        @Override
                        public void run() {
                            try {
                                friendlistdata= OkHttpUtil.getInstance().doSearchUserByName( et_search.getText().toString() );
                            } catch (IOException e) {
                                e.printStackTrace( );
                            }
                            Log.d( "Search++++++",friendlistdata );
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject( friendlistdata );
                                issuccess=jsonObject.getBoolean( "success" );
                            } catch (JSONException e) {
                                e.printStackTrace( );
                            }
                            if (!issuccess){//没有找到
                                Message message_fail=new Message();
                                message_fail.what=REQUEST_FAILED;
                                handler.sendMessage( message_fail );
                            }else {//找到了
                                msearchfrienddatas=parseDataWithJSON(friendlistdata);//username的数组
                                Message message_success=new Message();
                                message_success.what=REQUEST_SUCCESS;
                                handler.sendMessage( message_success );
                            }
                        }
                    } ).start();

                    return true;
                }
                return false;
            }

        });

    }

    private void loadFriendDetail(){
        dataList.clear();
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                for (String name:msearchfrienddatas){
                    try {
                        response=OkHttpUtil.getInstance().getInfo( name,inforUrl );
                    } catch (IOException e) {
                        e.printStackTrace( );
                    }
                    Gson gson=new Gson();
                    final Root root = gson.fromJson(response, Root.class);
                    dataList.add( root.getData() );
                    Message message=new Message();
                    message.what=REQUEST_LOAD;
                    handler.sendMessage( message );
                }
            }
        } ).start();
    }

    private List<String> parseDataWithJSON(String friendlistdata) {
        try {
            msearchfrienddatas.clear();
            JSONObject jsonObject=new JSONObject( friendlistdata );
            String data=jsonObject.getString( "data" );
            data=data.substring( 1,data.length()-1 );
            String[] mdatas=data.split( "," );
            for (int i=0;i<mdatas.length;i++){
                mdatas[i]=mdatas[i].substring( 1,mdatas[i].length()-1 );
                msearchfrienddatas.add( mdatas[i] );
                Log.d( "---Search---",mdatas[i] );
            }
            return msearchfrienddatas;
        } catch (JSONException e) {
            e.printStackTrace( );
        }
        return null;
    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
