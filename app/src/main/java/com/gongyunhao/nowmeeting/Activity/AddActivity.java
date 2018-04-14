package com.gongyunhao.nowmeeting.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.gongyunhao.nowmeeting.R;

public class AddActivity extends AppCompatActivity {

    RecyclerView recyclerViewAddFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        recyclerViewAddFriend = (RecyclerView)findViewById(R.id.recyclerview_add_friend);

    }
}
