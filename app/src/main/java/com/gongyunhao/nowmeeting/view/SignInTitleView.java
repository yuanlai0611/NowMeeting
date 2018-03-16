package com.gongyunhao.nowmeeting.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gongyunhao.nowmeeting.util.ScreenUtil;

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
//  Creste by GongYunHao on 2018/3/13
// 
public class SignInTitleView extends View {
    int screenWidth,screenHight;

    public SignInTitleView(Context context) {
        super( context );
        this.screenWidth= ScreenUtil.getScreenWidth( context );
        this.screenHight= ScreenUtil.getScreenHeight(context );
    }

    public SignInTitleView(Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
        this.screenWidth= ScreenUtil.getScreenWidth( context );
        this.screenHight= ScreenUtil.getScreenHeight(context );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(screenWidth, widthMeasureSpec);
        int height = getMySize(screenHight, heightMeasureSpec);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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

    public SignInTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
        this.screenWidth= ScreenUtil.getScreenWidth( context );
        this.screenHight= ScreenUtil.getScreenHeight(context );
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

}
