package com.gongyunhao.nowmeeting.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.DataBaseBean.DataBaseUserInfo;
import com.gongyunhao.nowmeeting.JsonBean.Root;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.util.OkHttpUtil;
import com.gongyunhao.nowmeeting.util.ScreenUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubmitVoteActivity extends AppCompatActivity {

    private String voteContent;
    private String voteName;
    private String conferenceName;
    private String voteId;
    private String userId;
    private String conferenceId;
    private List<String> voteList;
    private TextView textViewVoteTitle;
    private TextView textViewVoteNumber;
    private TextView textViewSponser;
    private LinearLayout linearLayoutContainer;
    private RelativeLayout relativeLayoutItem;
    private Button buttonSubmit;
    private String isVotedUrl = "http://39.106.47.27:8080/conference/api/vote/docheckUserVote";
    private String getConferenceByNameUrl = "http://39.106.47.27:8080/conference/api/conference/dogetConferenceInfoByName";
    private String getVoteInformationUrl = "http://39.106.47.27:8080/conference/api/vote/getVoteInformation";
    private String userVoteUrl = "http://39.106.47.27:8080/conference/api/vote/douserVote";
    private boolean isVoted;
    private HashMap<String,Integer> voteSituation = new HashMap<String, Integer>();
    private String Tag="SubmitVoteActivity";
    private int totalNumber = 0;
    private int i;
    private int choice;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_submit_vote );
        textViewVoteTitle = (TextView)findViewById(R.id.tv_submit_vote_title);
        textViewSponser = (TextView)findViewById(R.id.tv_submit_vote_initiator_name);
        textViewVoteNumber = (TextView)findViewById(R.id.vote_number);
        linearLayoutContainer = (LinearLayout)findViewById(R.id.container);
        buttonBack = (ImageButton) findViewById(R.id.chatting_back);
        buttonSubmit = (Button)findViewById(R.id.submit);
        Intent intent = getIntent();
        voteContent = intent.getStringExtra("voteContent");
        voteName = intent.getStringExtra("voteName");
        textViewVoteTitle.setText(voteName);
        voteId = intent.getStringExtra("voteId");
        voteList = getEveryVoteName(voteContent);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (int i=0 ; i<voteList.size() ; i++){

            relativeLayoutItem = (RelativeLayout) LayoutInflater.from(SubmitVoteActivity.this).inflate(R.layout.item_vote,linearLayoutContainer,false);
            TextView textView = (TextView) relativeLayoutItem.findViewById(R.id.vote_item_name);
            textView.setText(voteList.get(i));
            linearLayoutContainer.addView(relativeLayoutItem);

        }
        conferenceName = intent.getStringExtra("conferenceName");
        SharedPreferences sharedPreferences = getSharedPreferences("isLogin",MODE_PRIVATE);
        userId = sharedPreferences.getString("userID","");

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String > subscriber) {
                OkHttpUtil.getInstance().isVoted(userId, voteId, isVotedUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {


                        try {


                            JSONObject jsonObject = new JSONObject(response.body().string());
                            isVoted = jsonObject.getBoolean("data");
                            Log.d(Tag,"----->用户是否投过票： "+isVoted);


                        }catch (JSONException e){
                            e.printStackTrace();
                        }



                             OkHttpUtil.getInstance().getConferenceIdByName(conferenceName, getConferenceByNameUrl, new Callback() {
                                 @Override
                                 public void onFailure(Call call, IOException e) {

                                     Log.d(Tag,"---->获取conferenceId失败");

                                 }

                                 @Override
                                 public void onResponse(Call call, Response response) throws IOException {


                                     try{
                                         JSONObject jsonObject = new JSONObject(response.body().string());
                                         JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                         conferenceId = jsonObject1.getString("id");
                                         Log.d(Tag,"---->conferenceId: "+conferenceId);

                                     }catch (JSONException e){
                                         e.printStackTrace();
                                     }
                                     OkHttpUtil.getInstance().voteInformation(conferenceId, voteName, getVoteInformationUrl, new Callback() {
                                         @Override
                                         public void onFailure(Call call, IOException e) {

                                             Log.d(Tag,"---->获取之前的投票情况失败");

                                         }

                                         @Override
                                         public void onResponse(Call call, Response response) throws IOException {


                                             try{

                                                 JSONObject jsonObject = new JSONObject(response.body().string());
                                                 JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                                 Iterator<String> it = jsonObject1.keys();
                                                 while (it.hasNext())
                                                 {
                                                     String key = String.valueOf(it.next());
                                                     Integer value = (Integer) jsonObject1.get(key);
                                                     totalNumber = totalNumber+value;
                                                     voteSituation.put(key, value);
                                                 }
                                                 Log.d(Tag,"---->获取之前的投票情况成功");

                                                 if (isVoted){

                                                     for (int i=0 ; i<voteList.size() ; i++){

                                                         relativeLayoutItem = (RelativeLayout) LayoutInflater.from(SubmitVoteActivity.this).inflate(R.layout.item_vote,linearLayoutContainer,false);
                                                         TextView textView = (TextView) relativeLayoutItem.findViewById(R.id.vote_item_name);
                                                         textView.setText(voteList.get(i));
                                                         View view = (View) relativeLayoutItem.findViewById(R.id.view_test);
                                                         view.getLayoutParams().width = voteSituation.get(voteList.get(i))/totalNumber*ScreenUtil.getScreenWidth(SubmitVoteActivity.this);
                                                         TextView textView1 = (TextView) relativeLayoutItem.findViewById(R.id.vote_item_number);
                                                         textView1.setText(voteSituation.get(voteList.get(i)));
                                                         linearLayoutContainer.addView(relativeLayoutItem);
                                                         buttonSubmit.setVisibility(View.GONE);

                                                     }

                                                 }


                                             }catch (JSONException e){
                                                 e.printStackTrace();
                                             }


                                         }
                                     });

                                 }
                             });


                    }
                });

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


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for ( i=0 ; i<voteList.size() ; i++) {

                    RadioButton radioButton = linearLayoutContainer.getChildAt(i).findViewById(R.id.check);

                    if (radioButton.isChecked()) {
                        Log.d(Tag,"---->第"+i+"个被选择");
                        choice = i;
                        break;
                    }

                }

                        Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(final Subscriber<? super String > subscriber) {

                                OkHttpUtil.getInstance().userVote(voteId, userId, voteList.get(choice), userVoteUrl, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                        Toast.makeText(SubmitVoteActivity.this,"投票失败",Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                      Log.d(Tag,"---->投票成功");

                                        subscriber.onNext(String.valueOf(choice));

                                    }
                                });


                            }
                        })
                                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                                .subscribe(new Observer<String>()
                                {

                                    @Override
                                    public void onNext(String response) {

                                        int i = Integer.parseInt(response);
                                        for (int j=0 ; j<voteList.size() ; j++){
//
                                            if (i!=j){
                                                performAnimate(linearLayoutContainer.getChildAt(j).findViewById(R.id.view_test),linearLayoutContainer.getChildAt(i).findViewById(R.id.view_test).getLayoutParams().width,voteSituation.get(voteList.get(j))/(totalNumber+1)*ScreenUtil.getScreenWidth(SubmitVoteActivity.this),j,false );

                                            }else{
                                                performAnimate(linearLayoutContainer.getChildAt(j).findViewById(R.id.view_test),linearLayoutContainer.getChildAt(i).findViewById(R.id.view_test).getLayoutParams().width,(voteSituation.get(voteList.get(j))+1)/(totalNumber+1)*ScreenUtil.getScreenWidth(SubmitVoteActivity.this),j,true );

                                            }

                                        }

                                    }

                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {


                                    }
                                });







            }
        });


    }






    public List<String> getEveryVoteName(String name){

        List<String> nameList = new ArrayList<>();

        if (name.startsWith("vote")){

            name = name.replaceFirst("vote","");

            StringBuilder stringBuilder = new StringBuilder();

            for (int i=0 ; i<name.length() ; i++){

                if (name.charAt(i)=='/'){

                    nameList.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();

                }else{

                    stringBuilder.append(name.charAt(i));

                }

            }

        }else{

            Log.d("SubmitVoteActivity","发送的消息格式有问题");

        }
        return nameList;

    }


    private void performAnimate(final View target, final int start, final int end, final int i,final boolean add) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer)animator.getAnimatedValue();
                //Log.d(TAG, "current value: " + currentValue);

                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;

                //这里我偷懒了，不过有现成的干吗不用呢
                //直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                TextView textView =  (TextView)linearLayoutContainer.getChildAt(i).findViewById(R.id.vote_item_number);
                if (!add){
                    textView.setText(""+voteSituation.get(voteList.get(i)).intValue());
                }else {
                    textView.setText(""+(voteSituation.get(voteList.get(i)).intValue()+1));
                }

            }
        });

        valueAnimator.setDuration(500).start();


    }

}
