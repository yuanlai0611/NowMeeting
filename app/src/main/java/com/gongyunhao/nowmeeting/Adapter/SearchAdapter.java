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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gongyunhao.nowmeeting.JsonBean.Data;
import com.gongyunhao.nowmeeting.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Creste by GongYunHao on 2018/4/15
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Data> dataList=new ArrayList<>(  );//用户信息列表
//    private List<>//会议信息列表

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0){
            View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_search_friend,parent,false);
            return new SearchFriendViewHolder(view);
        }else {
            View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_search_friend,parent,false);
            return new SearchMeetingViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position<dataList.size()){//type 0显示好友列表
            return 0;
        }else {//type 1显示会议列表
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class SearchFriendViewHolder extends RecyclerView.ViewHolder {
        public SearchFriendViewHolder(View itemView) {
            super( itemView );

        }
    }

    class SearchMeetingViewHolder extends RecyclerView.ViewHolder {
        public SearchMeetingViewHolder(View itemView) {
            super( itemView );
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
