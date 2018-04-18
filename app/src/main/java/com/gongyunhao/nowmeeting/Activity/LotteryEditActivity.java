package com.gongyunhao.nowmeeting.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.DataBaseBean.DataBaseUserInfo;
import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LotteryEditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTheme;
    private EditText editTextNumber;
    private ImageButton imageButtonBack;
    private TextView textViewPublish;
    private String getConferenceIdByNameUrl = "http://39.106.47.27:8080/conference/api/conference/dogetConferenceInfoByName";
    private String getLotteryPeopleUrl = "http://39.106.47.27:8080/conference/api/lottery/docreateLottery";
    private int conferenceId;
    private String Tag = "LotteryEditActivity";
    private String lotteryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_edit);
        editTextTheme = (EditText)findViewById(R.id.lottery_theme);
        editTextNumber = (EditText)findViewById(R.id.first_number);
        imageButtonBack = (ImageButton)findViewById(R.id.chatting_back);
        textViewPublish = (TextView)findViewById(R.id.lottery_publish);
        imageButtonBack.setOnClickListener(this);
        textViewPublish.setOnClickListener(this);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String > subscriber) {
                try{


                    Intent intent = getIntent();
                    conferenceId = OkHttpUtil.getInstance().getConferenceIdByName(intent.getStringExtra("conferenceName"),getConferenceIdByNameUrl);


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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lottery_publish:
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String > subscriber) {
                        try{


                            OkHttpUtil.getInstance().getLotteryPeople(String.valueOf(conferenceId), editTextTheme.getText().toString(), editTextNumber.getText().toString(), getLotteryPeopleUrl, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                    Log.d(Tag,"---->获得抽奖结果失败");

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    Intent intent = new Intent();

                                    try{

                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        lotteryResult = jsonObject.getString("data");

                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                    Log.d("lotteryResult","---->"+lotteryResult);
                                    intent.putExtra("lotteryResult",lotteryResult);
                                    intent.putExtra("lotteryName",editTextTheme.getText().toString());
                                    intent.putExtra("lotteryNumber",editTextNumber.getText().toString());
                                    setResult(RESULT_OK,intent);
                                    finish();


                                }
                            });


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

                break;
            case R.id.chatting_back:
                finish();
                break;
            default:
                break;
        }
    }
}
