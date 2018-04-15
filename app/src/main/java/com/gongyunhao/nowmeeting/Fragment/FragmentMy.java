package com.gongyunhao.nowmeeting.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Activity.ChattingActivity;
import com.gongyunhao.nowmeeting.Activity.LoginActivity;
import com.gongyunhao.nowmeeting.Adapter.UserRecyclerviewAdapter;
import com.gongyunhao.nowmeeting.Base.BaseFragment;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.MessageItem;
import com.gongyunhao.nowmeeting.bean.UserItem;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;


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
    private List<UserInfo> userItems;
    private LinearLayout linearLayoutLogOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userItems = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        Log.d(Tag,"---->onCreateView");
        initViews(view);
        setListener();
        linearLayoutLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JMessageClient.logout();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit();
                editor.putBoolean("isLogin",false);
                editor.apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        ContactManager.getFriendList( new GetUserInfoListCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, List<UserInfo> userInfoList) {
                if (0 == responseCode) {
                    //获取好友列表成功
                    userItems.clear();
                    userItems.addAll( userInfoList );
                    userRecyclerviewAdapter.notifyDataSetChanged();
                } else {
                    //获取好友列表失败
                    Toast.makeText( getActivity(),"获取好友列表失败",Toast.LENGTH_SHORT ).show();
                }
            }
        });
        return view;
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void initViews(View view) {

        recyclerViewUser = (RecyclerView)view.findViewById(R.id.user_recyclerview);
        linearLayoutLogOut = (LinearLayout)view.findViewById(R.id.linearLayout_log_out);
        recyclerViewUser.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewUser.setLayoutManager(linearLayoutManager);
        userRecyclerviewAdapter = new UserRecyclerviewAdapter(mContext,userItems);
        recyclerViewUser.setAdapter(userRecyclerviewAdapter);

        userRecyclerviewAdapter.setmOnItemClickListener( new UserRecyclerviewAdapter.OnItemClickListener( ) {
            @Override
            public void onItemClick(View view, int position) {
                String userName = userItems.get(position).getUserName();
                Intent intent = new Intent(getActivity(), ChattingActivity.class);
                intent.putExtra("userName",userName);
                startActivityForResult(intent,1);
            }
        } );

    }


    @Override
    protected void setListener(){

    }
}
