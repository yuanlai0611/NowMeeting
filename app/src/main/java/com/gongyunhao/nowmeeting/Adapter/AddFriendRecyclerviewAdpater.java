package com.gongyunhao.nowmeeting.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.NewFriendItem;

import java.util.List;

public class AddFriendRecyclerviewAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewFriendItem> newFriendItemList;

    private Context context;

    public AddFriendRecyclerviewAdpater(Context context,List<NewFriendItem> newFriendItemList) {

        this.context = context;
        this.newFriendItemList = newFriendItemList;

    }


    @Override
    public AddFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.item_add_friend,parent,false);

        return new AddFriendViewHolder(view);

    }

    class AddFriendViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;

        TextView textViewFromWhere;

        TextView textViewWords;

        ImageView imageViewPicture;

        View addFriendView;

        public AddFriendViewHolder(View view){

            super(view);

            addFriendView = view;

            textViewName = (TextView)view.findViewById(R.id.name);

            textViewWords = (TextView)view.findViewById(R.id.words);

            textViewFromWhere = (TextView)view.findViewById(R.id.fromWhere);

            imageViewPicture = (ImageView)view.findViewById(R.id.user_picture);


        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AddFriendViewHolder addFriendViewHolder = (AddFriendViewHolder) holder;

        Glide.with(context).load(R.drawable.head7).into(addFriendViewHolder.imageViewPicture);

        addFriendViewHolder.textViewName.setText(newFriendItemList.get(position).getFriendName());

        addFriendViewHolder.textViewFromWhere.setText(newFriendItemList.get(position).getFromWhere());

        addFriendViewHolder.textViewWords.setText(newFriendItemList.get(position).getWords());

    }

    @Override
    public int getItemCount() {
        return newFriendItemList.size();
    }

}
