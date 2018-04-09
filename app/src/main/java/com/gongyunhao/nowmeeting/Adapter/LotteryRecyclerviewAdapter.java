package com.gongyunhao.nowmeeting.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.LotteryItem;

import java.util.List;

public class LotteryRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private Context context;

    private List<LotteryItem> lotteryItemList;

    private OnItemClickListener onItemClickListener = null;

    public LotteryRecyclerviewAdapter(Context context,List<LotteryItem> lotteryItemList){
        this.context = context;
        this.lotteryItemList = lotteryItemList;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setmOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v,(int)v.getTag());
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            View view = LayoutInflater.from(context).inflate(R.layout.item_lottery_more,parent,false);
            view.setOnClickListener(this);
            return new LotteryMoreViewHolder(view);
        }else if (viewType==2){
            View view = LayoutInflater.from(context).inflate(R.layout.item_lottery_picture,parent,false);
            view.setOnClickListener(this);
            return new LotteryViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LotteryViewHolder){
            LotteryViewHolder lotteryViewHolder = (LotteryViewHolder)holder;
            lotteryViewHolder.lotteryView.setTag(position);
        }else {
            LotteryMoreViewHolder lotteryMoreViewHolder = (LotteryMoreViewHolder)holder;
            lotteryMoreViewHolder.lotteryMoreView.setTag(position);
        }

    }



    @Override
    public int getItemViewType(int position) {
        if(position+1==lotteryItemList.size()){
            return 1;
        }else{
            return 2;
        }
    }

    class LotteryViewHolder extends RecyclerView.ViewHolder{


        View lotteryView;

        public LotteryViewHolder(View view){
            super(view);
            lotteryView = view;

        }

    }

    class LotteryMoreViewHolder extends RecyclerView.ViewHolder{

        View lotteryMoreView;
        public LotteryMoreViewHolder(View view){
            super(view);
            lotteryMoreView = view;
        }

    }

    @Override
    public int getItemCount() {
        return lotteryItemList.size();
    }
}
