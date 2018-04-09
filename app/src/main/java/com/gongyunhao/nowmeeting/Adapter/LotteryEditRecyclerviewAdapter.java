package com.gongyunhao.nowmeeting.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.LotteryEditItem;

import java.util.List;

public class LotteryEditRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<LotteryEditItem> lotteryEditItemList;
    private Context context;

    private LotteryEditRecyclerviewAdapter(Context context,List<LotteryEditItem> lotteryEditItemList){

        this.context = context;
        LotteryEditItem lotteryEditItem = new LotteryEditItem();
        lotteryEditItem.setLotteryItemType(LotteryEditItem.Header);
        this.lotteryEditItemList.add(lotteryEditItem);
        this.lotteryEditItemList.addAll(lotteryEditItemList);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType==LotteryEditItem.Header){
            View view = LayoutInflater.from(context).inflate(R.layout.item_lottery_header,parent,false);
            return new LotteryHeaderItemViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_lottery_edit,parent,false);
            return new LotteryEditItemViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

          if (holder instanceof LotteryEditItemViewHolder){

          }else{

          }

    }

    public class LotteryEditItemViewHolder extends RecyclerView.ViewHolder{

        private View lotteryEditItemView;
        private EditText editTextLotteryType;
        private EditText editTextPrize;
        private EditText editTextPeopleNumber;
        public LotteryEditItemViewHolder(View view){
            super(view);
            lotteryEditItemView = view;
            editTextLotteryType = (EditText)view.findViewById(R.id.lottery_type);
            editTextPrize = (EditText)view.findViewById(R.id.prize);
            editTextPeopleNumber = (EditText)view.findViewById(R.id.people_number);
        }

    }

    public class LotteryHeaderItemViewHolder extends RecyclerView.ViewHolder{

        private View viewLotteryHeader;
        private EditText editTextLotteryTheme;
        public LotteryHeaderItemViewHolder(View view){
            super(view);
            viewLotteryHeader = view;
            editTextLotteryTheme = (EditText)view.findViewById(R.id.lottery_theme);
        }

    }

    @Override
    public int getItemCount() {
        return lotteryEditItemList.size();
    }

}
