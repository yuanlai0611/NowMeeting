package com.gongyunhao.nowmeeting.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gongyunhao.nowmeeting.Adapter.PieChartAdapter;
import com.gongyunhao.nowmeeting.R;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private PieChart mPieChart;
    private RecyclerView recycler_vote;
    private int mCurrentItemOffset;
    //所有的pieEntry总和的List。
    private List<List<PieEntry>> mpie=new ArrayList<>(  );
    private List<String> piename=new ArrayList<>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pie_chart );
        recycler_vote=findViewById( R.id.recycler_vote );
        TextView textView=findViewById( R.id.chatting_title_name );
        textView.setText( "投票记录" );
        initData();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( PieChartActivity.this );
        linearLayoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
        recycler_vote.setLayoutManager( linearLayoutManager );
        //居中显示
        new LinearSnapHelper().attachToRecyclerView( recycler_vote );
        PieChartAdapter pieChartAdapter=new PieChartAdapter( mpie,piename );
        recycler_vote.setAdapter( pieChartAdapter );

//        PieChartAdapter pieChartAdapter=new PieChartAdapter(mpie);
//        //饼状图
//        mPieChart = (PieChart) findViewById(R.id.pieChart);
//        mPieChart.setUsePercentValues(true);
//        mPieChart.getDescription().setEnabled(false);
//        //        mPieChart.setExtraOffsets(5, 10, 5, 5);
//
//        mPieChart.setDragDecelerationFrictionCoef(0.95f);
//        //设置中间文件
//        mPieChart.setCenterText("哈哈哈哈");
//
//        mPieChart.setDrawHoleEnabled(true);
//        mPieChart.setHoleColor( Color.WHITE);
//
//        mPieChart.setTransparentCircleColor(Color.WHITE);
//        mPieChart.setTransparentCircleAlpha(110);
//
//        mPieChart.setHoleRadius(58f);
//        mPieChart.setTransparentCircleRadius(61f);
//
//        mPieChart.setDrawCenterText(true);
//
//        mPieChart.setRotationAngle(0);
//        // 触摸旋转
//        mPieChart.setRotationEnabled(true);
//        mPieChart.setHighlightPerTapEnabled(true);
//
//        //变化监听
//        mPieChart.setOnChartValueSelectedListener( PieChartActivity.this );
//
//        //模拟数据
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//        entries.add(new PieEntry(40, "优秀"));
//        entries.add(new PieEntry(20, "满分"));
//        entries.add(new PieEntry(30, "及格"));
//        entries.add(new PieEntry(10, "不及格"));
//
//        //设置数据
//        setData(entries);
//
//        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//
//        Legend l = mPieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation( Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//
//        // 输入标签样式
//        mPieChart.setEntryLabelColor(Color.WHITE);
//        mPieChart.setEntryLabelTextSize(12f);

    }

    private void initData() {
        //调用接口init饼状图。

        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(40, "优秀"));
        entries.add(new PieEntry(20, "满分"));
        entries.add(new PieEntry(30, "及格"));
        entries.add(new PieEntry(10, "不及格"));

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();
        entries1.add(new PieEntry(17, "河北"));
        entries1.add(new PieEntry(38, "海南"));
        entries1.add(new PieEntry(25, "湖北"));
        entries1.add(new PieEntry(8, "福建"));
        entries1.add(new PieEntry(12, "内蒙古"));

        ArrayList<PieEntry> entries2 = new ArrayList<PieEntry>();
        entries2.add(new PieEntry(17, "足球"));
        entries2.add(new PieEntry(38, "篮球"));
        entries2.add(new PieEntry(25, "网球"));

        ArrayList<PieEntry> entries3 = new ArrayList<PieEntry>();
        entries3.add(new PieEntry(40, "晴天"));
        entries3.add(new PieEntry(26, "雨"));
        entries3.add(new PieEntry(34, "阴"));
        //将所有数据填到大List中。

        //模拟数据
        mpie.add( entries );
        mpie.add( entries1 );
        mpie.add( entries2 );
        mpie.add( entries3 );
        piename.add( "班级考试情况" );
        piename.add( "学生分布情况" );
        piename.add( "兴趣分布" );
        piename.add( "最近天气情况" );
    }

    //设置数据
//    private void setData(ArrayList<PieEntry> entries) {
//        PieDataSet dataSet = new PieDataSet(entries, "计科1603班");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//
//        //数据和颜色
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//        colors.add( ColorTemplate.getHoloBlue());
//        dataSet.setColors(colors);
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor( Color.WHITE);
//        mPieChart.setData(data);
//        mPieChart.highlightValues(null);
//        //刷新
//        mPieChart.invalidate();
//    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

}
