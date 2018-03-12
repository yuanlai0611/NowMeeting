package com.gongyunhao.nowmeeting.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

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
 * Created by yuanlai on 2018/3/12.
 */

public class TypeFaceUtil {

    public static final int SLIM = 1;

    public static final int HARD_POINT = 2;

    public static final int NEW_SONG = 3;

    private static Typeface typeface;

    public static void setTypeFace(TextView textView, int Type, Context mContext){
        switch (Type){
            case SLIM:
                typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/slim.ttf");
                textView.setTypeface(typeface);
                break;
            case HARD_POINT:
                typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/upright_foursquare_hard_point.ttf");
                textView.setTypeface(typeface);
                break;
            case NEW_SONG:
                typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/new_song.ttf");
                break;
            default:
                break;
        }
    }

}
