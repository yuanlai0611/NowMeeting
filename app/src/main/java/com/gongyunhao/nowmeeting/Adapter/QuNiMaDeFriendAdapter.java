package com.gongyunhao.nowmeeting.Adapter;
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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhao.nowmeeting.Activity.AddActivity;
import com.gongyunhao.nowmeeting.R;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

/**
 * Creste by GongYunHao on 2018/4/16
 */
public class QuNiMaDeFriendAdapter extends RecyclerView.Adapter<QuNiMaDeFriendAdapter.ViewHolder>{
    private String name,reason;
    private Context mContext;

    public QuNiMaDeFriendAdapter(String name, String reason, Context mContext) {
        this.name = name;
        this.reason = reason;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_add_friend,parent,false );
        ViewHolder viewHolder=new ViewHolder( view );
        viewHolder.accept.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                new Thread( new Runnable( ) {
                    @Override
                    public void run() {
                        AcceptInvitation( name );
                    }
                } ).start();
            }
        } );
        return viewHolder;
    }

    private void AcceptInvitation(String username){
        ContactManager.acceptInvitation(username, "", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (0 == responseCode) {
                    //接收好友请求成功
                    Toast.makeText( mContext,"接受好友成功！",Toast.LENGTH_SHORT ).show();
                } else {
                    //接收好友请求失败
                    Toast.makeText( mContext,"你们已经是好友,不能重复添加！",Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_reason.setText( reason );
        holder.tv_name.setText( name );
    }

    @Override
    public int getItemCount() {
        return TextUtils.isEmpty( name )?0:1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button accept;
        TextView tv_name,tv_reason;

        public ViewHolder(View itemView) {
            super( itemView );
            accept=itemView.findViewById( R.id.item_accept_fuck );
            tv_name=itemView.findViewById( R.id.item_name_fuck );
            tv_reason=itemView.findViewById( R.id.item_reason_fuck );
        }
    }
}
