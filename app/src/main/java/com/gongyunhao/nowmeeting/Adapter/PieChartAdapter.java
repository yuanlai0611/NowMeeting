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

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gongyunhao.nowmeeting.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Creste by GongYunHao on 2018/3/31
 */
public class PieChartAdapter extends RecyclerView.Adapter<PieChartAdapter.ViewHolder>{
    private List<List<PieEntry>> mpie=new ArrayList<>(  );
    private List<String> mVoteName=new ArrayList<>(  );

    public PieChartAdapter(List<List<PieEntry>> mpie, List<String> mVoteName) {
        this.mpie = mpie;
        this.mVoteName = mVoteName;
    }

    @Override
    public PieChartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_recycler_vote,parent,false );
        ViewHolder viewHolder=new ViewHolder( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PieChartAdapter.ViewHolder holder, int position) {
        List<PieEntry> mpiedata=mpie.get( position );
        //饼状图
        holder.pieChart.setUsePercentValues(true);
        holder.pieChart.getDescription().setEnabled(false);

        holder.pieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        holder.pieChart.setCenterText(mVoteName.get( position ));

        holder.pieChart.setDrawHoleEnabled(true);
        holder.pieChart.setHoleColor( Color.WHITE);

        holder.pieChart.setTransparentCircleColor(Color.WHITE);
        holder.pieChart.setTransparentCircleAlpha(110);

        holder.pieChart.setHoleRadius(58f);
        holder.pieChart.setTransparentCircleRadius(61f);

        holder.pieChart.setDrawCenterText(true);

        holder.pieChart.setRotationAngle(0);
        // 触摸旋转
        holder.pieChart.setRotationEnabled(true);
        holder.pieChart.setHighlightPerTapEnabled(true);

        //设置数据
        PieDataSet dataSet = new PieDataSet(mpiedata, mVoteName.get( position ));
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add( ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor( Color.WHITE);
        holder.pieChart.setData(data);
        holder.pieChart.highlightValues(null);
        //刷新
        holder.pieChart.invalidate();

        holder.pieChart.animateY(1200, Easing.EasingOption.EaseInOutQuad);

        Legend l = holder.pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation( Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        holder.pieChart.setEntryLabelColor(Color.WHITE);
        holder.pieChart.setEntryLabelTextSize(12f);

    }

    @Override
    public int getItemCount() {
        return mpie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PieChart pieChart;

        public ViewHolder(View itemView) {
            super( itemView );
            pieChart=itemView.findViewById( R.id.pieChart );
        }
    }
}
