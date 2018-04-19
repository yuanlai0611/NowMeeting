package com.gongyunhao.nowmeeting.Activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.gongyunhao.nowmeeting.view.FlowLayout;
import com.loopj.android.jpush.http.PreemtiveAuthorizationHttpRequestInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.security.auth.callback.Callback;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CreateVoteActivity extends BaseActivity {
    private Button btn_add_vote_item;
    private FlowLayout mFlowLayout;
    private LayoutInflater mInflater;
    private EditText et_add_vote_item,et_vote_name;
    private LinearLayout linear_vote_type,linear_vote_deadline;
    private List<String> vote_item_list;
    private TextView textView;
    private String getConferenceIdByNameUrl = "http://39.106.47.27:8080/conference/api/conference/dogetConferenceInfoByName";
    private String createVoteUrl = "http://39.106.47.27:8080/conference/api/vote/doCreateVote";
    private int conferenceId;
    private String userId;
    private Button buttonPublish;
    private JSONObject jsonObject;
    private String groupId;
    private Conversation conversation;
    private int voteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Intent intent = getIntent();
        final String conferenceName = intent.getStringExtra("conferenceName");
        SharedPreferences sharedPreferences = getSharedPreferences("isLogin",MODE_PRIVATE);
        userId = sharedPreferences.getString("userID","");
        groupId = intent.getStringExtra("groupId");
        conversation = JMessageClient.getGroupConversation(Long.parseLong(groupId));

        btn_add_vote_item.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                String itemmes=et_add_vote_item.getText().toString();
                if (TextUtils.isEmpty( itemmes )){
                    showToast( "没有内容哦~" );
                }else {
                    final TextView tv = (TextView) mInflater.inflate(
                            R.layout.item_edit_vote, mFlowLayout, false);
                    tv.setText(et_add_vote_item.getText().toString());
                    et_add_vote_item.setText("");
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mFlowLayout.removeView( view );
                            //数据列表移除刷新
                        }
                    });
                    vote_item_list.add( itemmes );
                    mFlowLayout.addView(tv);
                }
            }
        } );


        textView.setText( "设计投票" );

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String > subscriber) {
                try{


                    Intent intent = getIntent();
                    conferenceId = OkHttpUtil.getInstance().getConferenceIdByName(conferenceName,getConferenceIdByNameUrl);


                }catch (IOException e){



                }

            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>()
                {

                    @Override
                    public void onNext(String response) {



                    }

                    @Override
                    public void onCompleted() {



                    }

                    @Override
                    public void onError(Throwable e) {



                    }
                });

    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_create_vote );

    }

    @Override
    public void initViews() {

        textView =findViewById( R.id.chatting_title_name );
        btn_add_vote_item=findViewById( R.id.btn_add_vote_item );
        mFlowLayout=findViewById( R.id.flowlayout );
        et_add_vote_item=findViewById( R.id.et_add_vote_item );
        mInflater=LayoutInflater.from( this );
        linear_vote_type=findViewById( R.id.linear_add_vote_type );
        linear_vote_deadline=findViewById( R.id.linear_add_vote_deadline );
        et_vote_name=findViewById( R.id.et_crate_vote_name );
        buttonPublish = findViewById(R.id.btn_create_vote_publish);
    }

    @Override
    public void onClick(View v) {
        super.onClick( v );
        switch (v.getId()){
            case R.id.linear_add_vote_type:
                
                break;
            case R.id.linear_add_vote_deadline:
                final Calendar cale2 = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                };
                TimePickerDialog my = new TimePickerDialog(CreateVoteActivity.this,mTimeSetListener,cale2.get(Calendar.HOUR_OF_DAY), cale2.get(Calendar.MINUTE),true);
                my.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                    }
                });
                my.show();
                break;
            case R.id.btn_create_vote_publish:
                Log.d(Tag,"---->点击了发布按钮");
                final StringBuilder stringBuilder = new StringBuilder();
                for (int i=0 ; i<vote_item_list.size()-1 ; i++){

                    stringBuilder.append(vote_item_list.get(i));
                    stringBuilder.append('\\');

                }
                stringBuilder.append(vote_item_list.get(vote_item_list.size()-1));
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String > subscriber) {

                        try {

                            OkHttpUtil.getInstance().createVote(String.valueOf(conferenceId), userId, et_vote_name.getText().toString(), stringBuilder.toString(), createVoteUrl, new okhttp3.Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    Log.d(Tag,"---->投票创建成功");
                                    Intent intent = new Intent();
                                    intent.putExtra("voteName",et_vote_name.getText().toString());
                                    Log.d(Tag,"---->"+et_vote_name.getText().toString());
                                    intent.putExtra("firstOption",vote_item_list.get(0));
                                    Log.d(Tag,"---->"+vote_item_list.get(0));
                                    intent.putExtra("secondOption",vote_item_list.get(1));
                                    Log.d(Tag,"---->"+vote_item_list.get(1));
                                    try{

                                        jsonObject = new JSONObject(response.body().string());
                                        intent.putExtra("voteId",jsonObject.getInt("data"));
                                        Log.d(Tag,"---->"+jsonObject.getInt("data"));

                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                    StringBuilder stringBuilder1 = new StringBuilder();
                                    stringBuilder1.append("vote");
                                    for (int i=0 ; i<vote_item_list.size() ; i++){
                                        stringBuilder1.append(vote_item_list.get(i));
                                        stringBuilder1.append("/");

                                    }
                                    Log.d(Tag,"---->"+stringBuilder1.toString());
                                    intent.putExtra("voteList",stringBuilder1.toString());
                                    setResult(RESULT_OK,intent);
                                    finish();
                                }
                            });

                        }catch (IOException e){
                            e.printStackTrace();
                        }


                    }
                })
                        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                        .subscribe(new Observer<String>()
                        {

                            @Override
                            public void onNext(String response) {



                            }

                            @Override
                            public void onCompleted() {



                            }

                            @Override
                            public void onError(Throwable e) {



                            }
                        });


                break;
            default:
                break;
        }
    }

    @Override
    public void initListeners() {
        buttonPublish.setOnClickListener(this);
        linear_vote_type.setOnClickListener( this );
        linear_vote_deadline.setOnClickListener( this );
    }

    @Override
    public void initData() {
        vote_item_list = new ArrayList<>(  );
    }
}
