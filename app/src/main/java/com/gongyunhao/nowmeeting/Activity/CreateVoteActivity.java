package com.gongyunhao.nowmeeting.Activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.view.FlowLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateVoteActivity extends BaseActivity {
    private Button btn_add_vote_item,btn_publish_vote;
    private FlowLayout mFlowLayout;
    private LayoutInflater mInflater;
    private EditText et_add_vote_item,et_vote_name;
    private LinearLayout linear_vote_type,linear_vote_deadline;
    private List<String> vote_item_list=new ArrayList<>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_vote );
        initViews();

        btn_add_vote_item.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                String itemmes=et_add_vote_item.getText().toString();
                if (TextUtils.isEmpty( itemmes )){
                    showToast( "没有内容哦~" );
                }else {
                    final TextView tv = (TextView) mInflater.inflate(
                            R.layout.item_vote, mFlowLayout, false);
                    tv.setText(itemmes);
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

        linear_vote_type.setOnClickListener( this );
        linear_vote_deadline.setOnClickListener( this );

    }

    @Override
    public void setContentView() {

    }

    @Override
    public void initViews() {
        TextView textView=findViewById( R.id.chatting_title_name );
        textView.setText( "设计投票" );
        btn_add_vote_item=findViewById( R.id.btn_add_vote_item );
        mFlowLayout=findViewById( R.id.flowlayout );
        et_add_vote_item=findViewById( R.id.et_add_vote_item );
        mInflater=LayoutInflater.from( this );
        linear_vote_type=findViewById( R.id.linear_add_vote_type );
        linear_vote_deadline=findViewById( R.id.linear_add_vote_deadline );
        btn_publish_vote=findViewById( R.id.btn_create_vote_publish );
        et_vote_name=findViewById( R.id.et_crate_vote_name );
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
        }
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
