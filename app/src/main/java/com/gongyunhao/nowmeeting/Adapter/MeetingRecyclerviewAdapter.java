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
import com.gongyunhao.nowmeeting.JsonBean.MeetingInformation;
import com.gongyunhao.nowmeeting.R;

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
 * Created by yuanlai on 2018/3/11.
 */

public class MeetingRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private List<MeetingInformation> meetingItemList;
    private Context mContext;
    private final int MEETING = 2;
    private OnItemClickListener onItemClickListener;
    private int[] drawables={R.drawable.meeting_test_1,R.drawable.meeting_test_2,R.drawable.meeting_test_3};

    public MeetingRecyclerviewAdapter(Context mContext,List<MeetingInformation> meetingItemList){
        this.mContext = mContext;
        this.meetingItemList = meetingItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == MEETING) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_meeting, parent, false);
            view.setOnClickListener( this );
            return new MeetingViewHolder(view);
        }
        return null;

    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    //在activity中调用她
    public void setmOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }


    class MeetingViewHolder extends RecyclerView.ViewHolder
    {
        View meetingView;
        ImageView imageViewMeetingPicture;
        TextView textViewMeetingName,sponsorname,mdate,location,city;
        LinearLayout linearLayoutMeetingType;
        ImageView imageViewMeetingType;
        TextView textViewMeetingTypeName;

        public MeetingViewHolder(View view){

            super(view);
            meetingView=view;
            imageViewMeetingPicture = (ImageView)view.findViewById(R.id.meeting_picture);
            textViewMeetingName = (TextView)view.findViewById(R.id.meeting_name);
            linearLayoutMeetingType = (LinearLayout)view.findViewById(R.id.linearLayoutMeetingType);
            imageViewMeetingType = (ImageView)view.findViewById(R.id.imageViewMeetingType);
            textViewMeetingTypeName = (TextView)view.findViewById(R.id.textViewMeetingName);
            sponsorname=view.findViewById( R.id.text_speaker );
            city=view.findViewById( R.id.meeting_place );
            mdate=view.findViewById( R.id.meeting_date );
            location=view.findViewById( R.id.meeting_location );

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (meetingItemList.get(position).getViewType()==MEETING){
            return MEETING;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return meetingItemList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MeetingViewHolder){
            MeetingViewHolder meetingViewHolder = (MeetingViewHolder) holder;
            if (meetingItemList.get(position).getMeetingType()== MeetingInformation.FRIEND_PARTICIPATE){
                 meetingViewHolder.linearLayoutMeetingType.setBackground(mContext.getResources().getDrawable(R.drawable.meeting_type_friend_participate));
                Glide.with(mContext).load(R.drawable.friend_participate).into(meetingViewHolder.imageViewMeetingType);
                meetingViewHolder.textViewMeetingTypeName.setText("朋友参与的会议");
            }else if (meetingItemList.get(position).getMeetingType()==MeetingInformation.PARTICIPATE){
                meetingViewHolder.linearLayoutMeetingType.setBackground(mContext.getResources().getDrawable(R.drawable.meeting_type_participate));
                Glide.with(mContext).load(R.drawable.participate).into(meetingViewHolder.imageViewMeetingType);
                meetingViewHolder.textViewMeetingTypeName.setText("参与的会议");
//                Glide.with( mContext ).load( drawables[position%4] ).into( meetingViewHolder.imageViewMeetingPicture );
            }
            meetingViewHolder.sponsorname.setText( meetingItemList.get( position ).getSponsorName() );
            meetingViewHolder.location.setText( meetingItemList.get( position ).getLocation() );
            meetingViewHolder.mdate.setText( meetingItemList.get( position ).getTime() );
            meetingViewHolder.city.setText( meetingItemList.get( position ).getCity() );
            meetingViewHolder.textViewMeetingName.setText(meetingItemList.get(position).getName());
            Glide.with(mContext).load(meetingItemList.get(position).getMeetingPictureId()).into(meetingViewHolder.imageViewMeetingPicture);
            meetingViewHolder.meetingView.setTag(position);


        }

    }
}
