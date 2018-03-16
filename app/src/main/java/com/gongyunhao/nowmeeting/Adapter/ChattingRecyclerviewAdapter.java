package com.gongyunhao.nowmeeting.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.test.ChattingItem;

import java.util.List;

/**
 * _oo0oo_
 * 08888888o
 * 88" . "88
 * (| -_- |)
 * 0\  =  /0
 * <p>
 * 佛祖保佑代码无bug
 * <p>
 * <p>
 * Created by yuanlai on 2018/3/13.
 */

public class ChattingRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

   private List<ChattingItem> chattingItems;

   private Context mContext;

   public ChattingRecyclerviewAdapter(Context context,List<ChattingItem> chattingItems){
       mContext = context;
       this.chattingItems = chattingItems;
   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view;
           view = LayoutInflater.from(mContext).inflate(R.layout.item_bubble,parent,false);
           return new ChattingItemViewholder(view);
    }


    class ChattingItemViewholder extends RecyclerView.ViewHolder{
       LinearLayout linearLayoutLeft;
       LinearLayout linearLayoutRight;
       TextView textViewMessageLeft;
       TextView textViewMessageRight;
       ImageView imageViewUserLeft;
       ImageView imageViewUserRight;
       public ChattingItemViewholder(View view){
            super(view);
            textViewMessageLeft = (TextView)view.findViewById(R.id.left_msg);
            linearLayoutLeft = (LinearLayout)view.findViewById(R.id.left_layout);
            imageViewUserLeft = (ImageView)view.findViewById(R.id.left_user_image);
            linearLayoutRight = (LinearLayout)view.findViewById(R.id.right_layout);
            textViewMessageRight = (TextView)view.findViewById(R.id.right_msg);
            imageViewUserRight = (ImageView)view.findViewById(R.id.right_user_image);
       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       ChattingItemViewholder chattingItemViewholder = (ChattingItemViewholder)holder;
        if (chattingItems.get(position).getViewType()==ChattingItem.LEFT){
            chattingItemViewholder.linearLayoutRight.setVisibility(View.GONE);
            Glide.with(mContext).load(chattingItems.get(position).getPictureId()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(chattingItemViewholder.imageViewUserLeft);
            chattingItemViewholder.textViewMessageLeft.setText(chattingItems.get(position).getChattingMessage());
        }else if (chattingItems.get(position).getViewType()==ChattingItem.RIGHT){
            chattingItemViewholder.linearLayoutLeft.setVisibility(View.GONE);
            Glide.with(mContext).load(chattingItems.get(position).getPictureId()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(chattingItemViewholder.imageViewUserRight);
            chattingItemViewholder.textViewMessageRight.setText(chattingItems.get(position).getChattingMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chattingItems.size();
    }
}
