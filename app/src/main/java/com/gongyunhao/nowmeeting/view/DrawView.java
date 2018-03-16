package com.gongyunhao.nowmeeting.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.gongyunhao.nowmeeting.util.ScreenUtil;

/**
 * Created by acer on 2017/12/10.
 */

public class DrawView extends View {
    int screenWidth,screenHight;
    String whatActivity;

    public DrawView(Context context,String whatActivity) {
        super( context );
        this.screenWidth= ScreenUtil.getScreenWidth( context );
        this.screenHight= ScreenUtil.getScreenHeight(context );
        this.whatActivity=whatActivity;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (whatActivity.equals( "LoginActivity" )){
            drawLoginBg( canvas );
        }else if (whatActivity.equals( "SignInActivity" )){
            drawSignInBg( canvas );
        }


    }

    private void drawLoginBg(Canvas canvas){
        // 创建画笔
        @SuppressLint("DrawAllocation") Paint p = new Paint();

        //上篮梯形
        p.setColor( Color.rgb( 15,138,230 ) );
        p.setStyle( Paint.Style.FILL);//设置填满
        // 绘制任意多边形
        Path path1 = new Path();
        path1.moveTo(0, 0);// 此点为多边形的起点
        path1.lineTo( screenWidth, 0);
        path1.lineTo( screenWidth , (float) (screenHight*0.15) );
        path1.lineTo(0, (float) (screenHight*0.35) );
        path1.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path1, p);

        //深篮三角
        p.setColor( Color.rgb( 63,81,181 ) );
        p.setStyle( Paint.Style.FILL);//设置填满
        // 绘制任意多边形
        Path path2 = new Path();
        path2.moveTo(0, 0);// 此点为多边形的起点
        path2.lineTo( (float) (screenWidth*0.38), (float) (screenHight*0.274) );
        path2.lineTo(0, (float) (screenHight*0.35) );
        path2.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path2, p);
    }

    private void drawSignInBg(Canvas canvas){

        // 创建画笔
        @SuppressLint("DrawAllocation") Paint p = new Paint();

        //上篮梯形
        p.setColor( Color.rgb( 15,138,230 ) );
        p.setStyle( Paint.Style.FILL);//设置填满
        // 绘制任意多边形
        Path path1 = new Path();
        path1.moveTo(0, 0);// 此点为多边形的起点
        path1.lineTo( screenWidth, 0);
        path1.lineTo( screenWidth , (float) (screenHight*0.35) );
        path1.lineTo(0, (float) (screenHight*0.15) );
        path1.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path1, p);

        //深篮三角
        p.setColor( Color.rgb( 63,81,181 ) );
        p.setStyle( Paint.Style.FILL);//设置填满
        // 绘制任意多边形
        Path path2 = new Path();
        path2.moveTo(screenWidth, 0);// 此点为多边形的起点
        path2.lineTo( (float) (screenWidth*0.62), (float) (screenHight*0.274) );
        path2.lineTo(screenWidth, (float) (screenHight*0.35) );
        path2.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path2, p);

    }

}
