package com.gongyunhao.nowmeeting.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.gongyunhao.nowmeeting.R;
import com.gongyunhao.nowmeeting.test.ChattingItem;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;
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
 * Created by yuanlai on 2018/3/13.
 */

public class ChattingRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

   private List<ChattingItem> chattingItems;

   private Context mContext;

   public ChattingRecyclerviewAdapter(Context context,List<ChattingItem> chattingItems){
       mContext = context;
       this.chattingItems = chattingItems;
   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view;
           view = LayoutInflater.from(mContext).inflate(R.layout.item_bubble,parent,false);
           return new ChattingItemViewholder(view);
    }


    class ChattingItemViewholder extends RecyclerView.ViewHolder{

       LinearLayout linearLayoutLeft;
       LinearLayout linearLayoutRight;
       LinearLayout linearLayoutLeftPhoto;
       LinearLayout linearLayoutRightPhoto;
       LinearLayout linearLayoutLeftVote;
       LinearLayout linearLayoutRightVote;
       LinearLayout linearLayoutLeftLottery;
       LinearLayout linearLayoutRightLottery;
       ImageView imageViewLeftUserPicture;
       ImageView imageViewRightUserPicture;
       ImageView imageViewLeftUser;
       ImageView imageViewRightUser;
       ImageView imageViewLeftVote;
       ImageView imageViewRightVote;
       ImageView imageViewLeftLottery;
       ImageView imageViewRightLottery;
       ImageView imageViewLeftPhoto;
       ImageView imageViewRightPhoto;
       TextView textViewLeftVoteName;
       TextView textViewRightVoteName;
       LinearLayout linearLayoutLeftVoteFirstLine;
       LinearLayout linearLayoutLeftVoteSecondLine;
       TextView textViewLeftVoteFirstChoice;
       TextView textViewLeftVoteSecondChoice;
       LinearLayout linearLayoutRightVoteFirstLine;
       LinearLayout linearLayoutRightVoteSecondLine;
       TextView textViewRightVoteFirstChoice;
       TextView textViewRightVoteSecondChoice;
       TextView textViewLeftLotteryName;
       TextView textViewRightLotteryName;
       TextView textViewLeftLotteryNumber;
       TextView textViewRightLotteryNumber;
       TextView textViewMessageLeft;
       TextView textViewMessageRight;
       ImageView imageViewUserLeft;
       ImageView imageViewUserRight;

       public ChattingItemViewholder(View view){
            super(view);

            textViewMessageLeft = (TextView)view.findViewById(R.id.left_msg);
            linearLayoutLeft = (LinearLayout)view.findViewById(R.id.left_layout);
            imageViewUserLeft = (ImageView)view.findViewById(R.id.left_user_image);
            linearLayoutRight = (LinearLayout)view.findViewById(R.id.right_layout);
            textViewMessageRight = (TextView)view.findViewById(R.id.right_msg);
            imageViewUserRight = (ImageView)view.findViewById(R.id.right_user_image);
            linearLayoutLeftPhoto = (LinearLayout)view.findViewById(R.id.left_layout_picture);
            linearLayoutRightPhoto = (LinearLayout)view.findViewById(R.id.right_layout_picture);
            linearLayoutLeftVote = (LinearLayout)view.findViewById(R.id.left_layout_vote);
            linearLayoutRightVote = (LinearLayout)view.findViewById(R.id.right_layout_vote);
            linearLayoutLeftLottery = (LinearLayout)view.findViewById(R.id.left_layout_lottery);
            linearLayoutRightLottery = (LinearLayout)view.findViewById(R.id.right_layout_lottery);
            imageViewLeftPhoto = (ImageView)view.findViewById(R.id.left_picture);
            imageViewRightPhoto = (ImageView)view.findViewById(R.id.right_picture);
            imageViewLeftUserPicture = (ImageView)view.findViewById(R.id.left_user_image_picture);
            imageViewRightUserPicture = (ImageView)view.findViewById(R.id.right_user_image_picture);
            imageViewLeftUser = (ImageView)view.findViewById(R.id.left_user_image);
            imageViewRightUser = (ImageView)view.findViewById(R.id.right_user_image);
            imageViewLeftVote = (ImageView)view.findViewById(R.id.left_user_image_vote);
            imageViewRightVote = (ImageView)view.findViewById(R.id.right_user_image_vote);
            imageViewLeftLottery = (ImageView)view.findViewById(R.id.left_user_image_lottery);
            imageViewRightLottery = (ImageView)view.findViewById(R.id.right_user_image_lottery);
            textViewLeftVoteName = (TextView)view.findViewById(R.id.left_vote_name);
            textViewRightVoteName = (TextView)view.findViewById(R.id.right_vote_name);
            linearLayoutLeftVoteFirstLine = (LinearLayout)view.findViewById(R.id.left_vote_first_line);
            linearLayoutLeftVoteSecondLine = (LinearLayout)view.findViewById(R.id.left_vote_second_line);
            linearLayoutRightVoteFirstLine = (LinearLayout)view.findViewById(R.id.right_vote_first_line);
            linearLayoutRightVoteSecondLine = (LinearLayout)view.findViewById(R.id.right_vote_second_line);
            textViewLeftVoteFirstChoice =(TextView)view.findViewById(R.id.left_vote_first_choice);
            textViewLeftVoteSecondChoice = (TextView)view.findViewById(R.id.left_vote_second_choice);
            textViewRightVoteFirstChoice = (TextView)view.findViewById(R.id.right_vote_first_choice);
            textViewRightVoteSecondChoice = (TextView)view.findViewById(R.id.right_vote_second_choice);
            textViewLeftLotteryName = (TextView)view.findViewById(R.id.left_lottery_name);
            textViewLeftLotteryNumber = (TextView)view.findViewById(R.id.left_lottery_number);
            textViewRightLotteryName = (TextView)view.findViewById(R.id.right_lottery_name);
            textViewRightLotteryNumber = (TextView)view.findViewById(R.id.right_lottery_number);

       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

      final ChattingItemViewholder chattingItemViewholder = (ChattingItemViewholder)holder;

        if (chattingItems.get(position).getViewType()==ChattingItem.LEFT){

            chattingItemViewholder.linearLayoutLeft.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(chattingItems.get(position).getPictureId()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(chattingItemViewholder.imageViewUserLeft);
            chattingItemViewholder.textViewMessageLeft.setText(chattingItems.get(position).getChattingMessage());

        }else if(chattingItems.get(position).getViewType()==ChattingItem.RIGHT){

            chattingItemViewholder.linearLayoutRight.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(chattingItems.get(position).getPictureId()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(chattingItemViewholder.imageViewUserRight);
            chattingItemViewholder.textViewMessageRight.setText(chattingItems.get(position).getChattingMessage());

        }else if(chattingItems.get(position).getViewType()==ChattingItem.LEFT_PHOTO){

            chattingItemViewholder.linearLayoutLeftPhoto.setVisibility(View.VISIBLE);

            Tiny.FileCompressOptions fileCompressOptions = new Tiny.FileCompressOptions();
            fileCompressOptions.isKeepSampling = true;
            Tiny.getInstance().source(chattingItems.get(position).getPhotoPath()).asFile().withOptions(fileCompressOptions).compress(new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    if (isSuccess){

                    loadPhoto(outfile,chattingItemViewholder.imageViewLeftPhoto);

                    }else {

                    loadPhoto(chattingItems.get(position).getPhotoPath(),chattingItemViewholder.imageViewLeftPhoto);

                    }
                }
            });

            Glide.with(mContext).load(chattingItems.get(position).getPictureId()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(chattingItemViewholder.imageViewLeftUserPicture);

        }else if(chattingItems.get(position).getViewType()==ChattingItem.RIGHT_PHOTO){

            chattingItemViewholder.linearLayoutRightPhoto.setVisibility(View.VISIBLE);
            Tiny.FileCompressOptions fileCompressOptions = new Tiny.FileCompressOptions();
            fileCompressOptions.isKeepSampling = true;
            Tiny.getInstance().source(chattingItems.get(position).getPhotoPath()).asFile().withOptions(fileCompressOptions).compress(new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    if (isSuccess){

                        loadPhoto(outfile,chattingItemViewholder.imageViewRightPhoto);

                    }else {

                        loadPhoto(chattingItems.get(position).getPhotoPath(),chattingItemViewholder.imageViewRightPhoto);

                    }
                }
            });
            Glide.with(mContext).load(chattingItems.get(position).getPictureId()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(chattingItemViewholder.imageViewRightUserPicture);

        }
    }

    @Override
    public int getItemCount() {
        return chattingItems.size();
    }

    public void loadPhoto(String photopath, final ImageView imageViewPhoto){

        Glide.with(mContext).load(photopath).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                if (imageViewPhoto == null) {
                    return false;
                }
                if (imageViewPhoto.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    imageViewPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                ViewGroup.LayoutParams params = imageViewPhoto.getLayoutParams();
                int vw = imageViewPhoto.getWidth() - imageViewPhoto.getPaddingLeft() - imageViewPhoto.getPaddingRight();
                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                params.height = vh + imageViewPhoto.getPaddingTop() + imageViewPhoto.getPaddingBottom();
                imageViewPhoto.setLayoutParams(params);

                return false;
            }
        }).into(imageViewPhoto);



    }

}
