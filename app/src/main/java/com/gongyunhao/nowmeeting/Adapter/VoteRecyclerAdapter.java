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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.Voteitem;

import java.util.List;

/**
 * Creste by GongYunHao on 2018/4/8
 */
public class VoteRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;

    private List<Voteitem> VoteItemList;

    public VoteRecyclerAdapter(Context context,List<Voteitem> VoteItemList){
        this.context = context;
        this.VoteItemList = VoteItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(viewType==1){
//            View view = LayoutInflater.from(context).inflate( R.layout.item_lottery_more,parent,false);
//            return new VoteMoreViewHolder(view);
//        }else if (viewType==2){
            View view = LayoutInflater.from(context).inflate(R.layout.item_small_vote,parent,false);
            return new VoteViewHolder(view);
//        }
//        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof VoteViewHolder){
            VoteViewHolder voteViewHolder = (VoteViewHolder)holder;

//        }else if (holder instanceof VoteMoreViewHolder){
//            VoteMoreViewHolder voteMoreViewHolder=(VoteMoreViewHolder) holder;
//        }

    }

//    @Override
//    public int getItemViewType(int position) {
//        if(position+1==VoteItemList.size()){
//            return 1;//末尾的显示更多type
//        }else{
//            return 2;//前面的正常item项type
//        }
//    }

    class VoteViewHolder extends RecyclerView.ViewHolder{

        View VoteView;
        TextView VoteName,VoteTime,OrganizerName;
        public VoteViewHolder(View view){
            super(view);
            VoteView = view;
            VoteName=view.findViewById( R.id.tv_item_vote_small_name );
            VoteTime=view.findViewById( R.id.tv_item_vote_small_date );
            OrganizerName=view.findViewById( R.id.tv_item_vote_small_organizer );
        }

    }

//    class VoteMoreViewHolder extends RecyclerView.ViewHolder{
//
//        View VoteMoreView;
//        public VoteMoreViewHolder(View view){
//            super(view);
//            VoteMoreView = view;
//        }
//
//    }

    @Override
    public int getItemCount() {
        return VoteItemList.size();
    }

}
