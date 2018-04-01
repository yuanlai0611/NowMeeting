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

public class LotteryRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<LotteryItem> lotteryItemList;

    public LotteryRecyclerviewAdapter(Context context,List<LotteryItem> lotteryItemList){
        this.context = context;
        this.lotteryItemList = lotteryItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            View view = LayoutInflater.from(context).inflate(R.layout.item_lottery_more,parent,false);
            return new LotteryMoreViewHolder(view);
        }else if (viewType==2){
            View view = LayoutInflater.from(context).inflate(R.layout.item_lottery_picture,parent,false);
            return new LotteryViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LotteryViewHolder){
            LotteryViewHolder lotteryViewHolder = (LotteryViewHolder)holder;
            if (position%3==0){
               lotteryViewHolder.imageViewLottery.setImageResource(R.drawable.lottery_picture_pink);
            }else if (position%3==1){
               lotteryViewHolder.imageViewLottery.setImageResource(R.drawable.lottery_picture_yellow);
            }else {
               lotteryViewHolder.imageViewLottery.setImageResource(R.drawable.lottery_picture_blue);
            }
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

        ImageView imageViewLottery;
        View lotteryView;
        TextView textViewLotteryTime;
        public LotteryViewHolder(View view){
            super(view);
            lotteryView = view;
            textViewLotteryTime = (TextView)view.findViewById(R.id.lottery_time);
            imageViewLottery = (ImageView)view.findViewById(R.id.lottery_picture);
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
