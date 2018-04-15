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
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.Activity.UserDetailActivity;
import com.gongyunhao.nowmeeting.JsonBean.Data;
import com.gongyunhao.nowmeeting.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Creste by GongYunHao on 2018/4/15
 */
public class SearchFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Data> userdatas=new ArrayList<>(  );
    private Context mContext;

    public SearchFriendAdapter(List<Data> userdatas,Context context) {
        this.userdatas = userdatas;
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_search_friend,parent,false);
        final SearchFriendViewHolder searchFriendViewHolder=new SearchFriendViewHolder( view );
        searchFriendViewHolder.btn_add.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( mContext, UserDetailActivity.class );
                intent.putExtra( "AddFriendActivity_AddFriend",userdatas.get( searchFriendViewHolder.getAdapterPosition() ).getUsername() );
                mContext.startActivity( intent );
            }
        } );
        return searchFriendViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Data mdata=userdatas.get( position );
        SearchFriendViewHolder searchFriendViewHolder=(SearchFriendViewHolder) holder;
        searchFriendViewHolder.username.setText( mdata.getUsername() );
        searchFriendViewHolder.signature.setText( mdata.getSignature() );
        searchFriendViewHolder.address.setText( mdata.getAddress() );
    }

    class SearchFriendViewHolder extends RecyclerView.ViewHolder {
        TextView username,signature,address;
        Button btn_add;
        public SearchFriendViewHolder(View itemView) {
            super( itemView );
            username=itemView.findViewById( R.id.item_search_name );
            signature=itemView.findViewById( R.id.item_search_signature );
            address=itemView.findViewById( R.id.item_search_address );
            btn_add=itemView.findViewById( R.id.btn_item_search_add_friend );
        }
    }

    @Override
    public int getItemCount() {
        return userdatas.size();
    }
}
