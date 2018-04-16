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

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gongyunhao.nowmeeting.Activity.MeetingDetailNoJoinActivity;
import com.gongyunhao.nowmeeting.JsonBean.MeetingInformation;
import com.gongyunhao.nowmeeting.R;

import org.raphets.roundimageview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Creste by GongYunHao on 2018/4/16
 */
public class SearchMeetingAdapter extends RecyclerView.Adapter<SearchMeetingAdapter.ViewHolder>{
    private List<MeetingInformation> meetingInformationList=new ArrayList<>(  );
    private Context mContext;

    public SearchMeetingAdapter(List<MeetingInformation> meetingInformationList, Context mContext) {
        this.meetingInformationList = meetingInformationList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_search_meeting,parent,false  );
        final ViewHolder viewHolder=new ViewHolder( view );
        viewHolder.mview.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent_no_join=new Intent( mContext, MeetingDetailNoJoinActivity.class );
                intent_no_join.putExtra( "nojoin_meeting_id",meetingInformationList.get( viewHolder.getAdapterPosition() ).getId() );
                //实现了share动画在recyclerview中传递的效果
                mContext.startActivity(intent_no_join);
            }
        } );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MeetingInformation meetingInformation=meetingInformationList.get( position );
        holder.location.setText( meetingInformation.getLocation() );
        holder.meeting_date.setText( meetingInformation.getTime() );
        holder.meeting_name.setText( meetingInformation.getName() );
        holder.meeting_place.setText( meetingInformation.getCity() );
        holder.text_speaker.setText( meetingInformation.getSponsorName() );
        Glide.with(mContext).load(R.drawable.meeting_test_1).into(holder.meeting_picture);
    }

    @Override
    public int getItemCount() {
        return meetingInformationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mview;
        RoundImageView meeting_picture;
        TextView meeting_name,meeting_place,meeting_date,text_speaker,location;
        public ViewHolder(View itemView) {
            super( itemView );
            mview=itemView;
            meeting_picture=itemView.findViewById( R.id.meeting_picture_search );
            meeting_name=itemView.findViewById( R.id.meeting_name_search );
            meeting_place=itemView.findViewById( R.id.meeting_place_search );
            meeting_date=itemView.findViewById( R.id.meeting_date_search );
            text_speaker=itemView.findViewById( R.id.text_speaker_search );
            location=itemView.findViewById( R.id.location_search );
        }
    }
}
