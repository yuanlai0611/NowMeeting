package com.gongyunhao.nowmeeting.Activity;

import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.gongyunhao.nowmeeting.Base.BaseActivity;
import com.gongyunhao.nowmeeting.R;

public class SearchActivity extends BaseActivity {
    private ImageButton ib_back;
    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setEnterTransition(new Explode(  ));
        setContentView( );
        initViews();

        ib_back.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

    }

    @Override
    public void setContentView() {
        setContentView( R.layout.activity_search );
    }

    @Override
    public void initViews() {
        ib_back=findViewById( R.id.search_imageButton_back );
        et_search=findViewById( R.id.title_edittext_search );
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {

    }
}
