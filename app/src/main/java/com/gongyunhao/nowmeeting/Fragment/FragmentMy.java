package com.gongyunhao.nowmeeting.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhao.nowmeeting.Adapter.UserRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseFragment;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.UserItem;

import java.util.ArrayList;
import java.util.List;

//    ┏┓　   ┏┓
// ┏━━┛┻━━━━━┛┻ ┓ 
// ┃　　　　　　 ┃  
// ┃　　　━　    ┃  
// ┃　＞　　＜　 ┃  
// ┃　　　　　　 ┃  
// ┃... ⌒ ...  ┃  
// ┃　　　　　 　┃  
// ┗━━━┓　　　┏━┛  
//     ┃　　　┃　  
//     ┃　　　┃  
//     ┃　　　┃  神兽保佑  
//     ┃　　　┃  代码无bug　　  
//     ┃　　　┃  
//     ┃　　　┗━━━━━━━━━┓
//     ┃　　　　　　　    ┣┓
//     ┃　　　　         ┏┛
//     ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
//       ┃ ┫ ┫   ┃ ┫ ┫
//       ┗━┻━┛   ┗━┻━┛
//
//  作者：棒棒小糖
//  來源：简书
//
//  Creste by GongYunHao on 2018/3/5
// 
public class FragmentMy extends BaseFragment{

    private Context mContext;
    String Tag = "FragmentMy";
    private RecyclerView recyclerViewUser;
    private UserRecyclerviewAdapter userRecyclerviewAdapter;
    private List<UserItem> userItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userItems = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        Log.d(Tag,"---->onCreateView");
        initViews(view);
        setListener();
        return view;
    }

    @Override
    protected void requestData() {
        for (int i=0 ; i<2 ;i++){
            UserItem userItem = new UserItem();
            userItem.setUserName("袁大来");
            userItem.setUserPictureId(R.drawable.head1);
            userItems.add(userItem);
            UserItem userItem1 = new UserItem();
            userItem1.setUserName("程文喆");
            userItem1.setUserPictureId(R.drawable.head2);
            userItems.add(userItem1);
            UserItem userItem2 = new UserItem();
            userItem2.setUserName("袁程程");
            userItem2.setUserPictureId(R.drawable.head3);
            userItems.add(userItem2);
        }
    }

    @Override
    protected void initViews(View view) {

        recyclerViewUser = (RecyclerView)view.findViewById(R.id.user_recyclerview);
        recyclerViewUser.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewUser.setLayoutManager(linearLayoutManager);
        userRecyclerviewAdapter = new UserRecyclerviewAdapter(mContext,userItems);
        recyclerViewUser.setAdapter(userRecyclerviewAdapter);

    }


    @Override
    protected void setListener(){

    }
}
