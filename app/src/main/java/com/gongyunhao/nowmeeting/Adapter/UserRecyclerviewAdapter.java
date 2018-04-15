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
import com.gongyunhao.nowmeeting.bean.UserItem;

import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

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
 * Created by yuanlai on 2018/3/15.
 */

public class UserRecyclerviewAdapter extends RecyclerView.Adapter<UserRecyclerviewAdapter.UserViewHolder> implements View.OnClickListener{
    private OnItemClickListener onItemClickListener = null;
    private List<UserInfo> userItems;
    private Context mContext;

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    public void setmOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public UserRecyclerviewAdapter(Context context,List<UserInfo> userItems){
        mContext = context;
        this.userItems = userItems;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        Glide.with(mContext).load(R.drawable.head3).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.imageViewUserPicture);
        holder.textViewUserName.setText(userItems.get(position).getUserName());
        holder.userView.setTag( position );
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        View userView;
        ImageView imageViewUserPicture;
        TextView textViewUserName;

        public UserViewHolder(View view){
            super(view);
            userView=view;
            imageViewUserPicture = (ImageView)view.findViewById(R.id.user_picture_about_me);
            textViewUserName = (TextView)view.findViewById(R.id.user_name_about_me);
        }

    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_user,parent,false);
        view.setOnClickListener( this );
        return new UserViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return userItems.size();
    }
}
