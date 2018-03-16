package com.gongyunhao.nowmeeting.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.bean.MeetingItem;

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

public class MeetingRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MeetingItem> meetingItemList;
    private Context mContext;
    private final int TITLE = 1;
    private final int MEETING = 2;

    public MeetingRecyclerviewAdapter(Context mContext,List<MeetingItem> meetingItemList){
        this.mContext = mContext;
        this.meetingItemList = meetingItemList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TITLE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_meeting_title, parent, false);
            return new TitleViewHolder(view);
        } else if (viewType == MEETING) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_meeting, parent, false);
            return new MeetingViewHolder(view);
        }
        return null;

    }

    class TitleViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewMeetingTitlePicture;
        public TitleViewHolder(View view){
            super(view);
            imageViewMeetingTitlePicture = (ImageView)view.findViewById(R.id.meeting_title_picture);
        }
    }

    class MeetingViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageViewMeetingPicture;
        TextView textViewMeetingName;
        public MeetingViewHolder(View view){
            super(view);
            imageViewMeetingPicture = (ImageView)view.findViewById(R.id.meeting_picture);
            textViewMeetingName = (TextView)view.findViewById(R.id.meeting_name);

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (meetingItemList.get(position).getViewType()==TITLE){
            return TITLE;
        }else if (meetingItemList.get(position).getViewType()==MEETING){
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

        if (holder instanceof TitleViewHolder){
            TitleViewHolder titleViewHolder = (TitleViewHolder)holder;
//            TypeFaceUtil.setTypeFace(titleViewHolder.textViewTitle,TypeFaceUtil.NEW_SONG,mContext);
            Glide.with(mContext).load(meetingItemList.get(position).getMeetingPictureId()).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(titleViewHolder.imageViewMeetingTitlePicture);
        }else if (holder instanceof MeetingViewHolder){
            MeetingViewHolder meetingViewHolder = (MeetingViewHolder) holder;
            meetingViewHolder.textViewMeetingName.setText(meetingItemList.get(position).getMeetingName());
            Glide.with(mContext).load(R.drawable.meeting_test).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(meetingViewHolder.imageViewMeetingPicture);

        }

    }
}
