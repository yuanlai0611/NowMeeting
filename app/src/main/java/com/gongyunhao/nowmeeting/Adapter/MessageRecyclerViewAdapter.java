package com.gongyunhao.nowmeeting.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.MessageItem;

import java.util.List;

/**
 * Created by yuanyuanlai on 2018/3/9.
 */

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener,View.OnLongClickListener{

    private List<MessageItem> messageItemList;

    private Context mContext;

    private OnItemClickListener onItemClickListener = null;

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (onItemClickListener != null){
            onItemClickListener.onItemLongClick(view,(int)view.getTag());
        }
        return false;
    }

    public void setmOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }


    public MessageRecyclerViewAdapter(Context mContext,List<MessageItem> messageItemList){
        this.mContext = mContext;
        this.messageItemList = messageItemList;
    }

    public void removeData(int position){
        messageItemList.remove(position);
        notifyItemRemoved(position);
    }

    public void addData(MessageItem messageItem){
        messageItemList.add(messageItem);
        notifyDataSetChanged();
    }

    public void addData(List<MessageItem> messageItemList){
        messageItemList.addAll(messageItemList);
        notifyDataSetChanged();
    }

    public void addData(MessageItem messageItem,int position){
        messageItemList.add(position,messageItem);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        View messageView;
        TextView textViewDate;
        TextView textViewMessage;
        TextView textViewUserName;
        ImageView imageViewUser;

        public MyViewHolder(View view){
            super(view);
            messageView = view;
            textViewMessage = (TextView) view.findViewById(R.id.user_message);
            textViewUserName = (TextView) view.findViewById(R.id.user_name);
            textViewDate = (TextView) view.findViewById(R.id.user_date);
            imageViewUser = (ImageView) view.findViewById(R.id.user_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_message,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       holder.textViewUserName.setText( messageItemList.get(position).getUserName());
       holder.textViewMessage.setText(messageItemList.get(position).getMessage());
       holder.textViewDate.setText( messageItemList.get(position).getDate());
       Glide.with(mContext).load(messageItemList.get(position).getImageId()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.imageViewUser);
       holder.messageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return messageItemList.size();
    }
}
